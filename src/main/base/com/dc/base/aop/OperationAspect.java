package com.dc.base.aop;

import com.alibaba.fastjson.JSON;
import com.dc.base.contants.BaseContants;
import com.dc.base.contants.GlobalVar;
import com.dc.base.em.ErrorMesgEnum;
import com.dc.base.exception.BusinessException;
import com.dc.base.pojo.BaseModel;
import com.dc.base.util.QueryUtil;
import com.dc.mapper.SysOperationLogDao;
import com.dc.pojo.SysOperationLog;
import com.dc.pojo.SysUser;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;


/**
 * @author Enzo
 * @Description TODO
 * @date 2018-11-19 15:46
 */
@Aspect
@Component
public class OperationAspect {

    Logger log = Logger.getLogger(OperationAspect.class);
    protected HttpServletRequest request;
    protected HttpSession session;
    protected BaseModel baseModel = new BaseModel();
    @Autowired
    private SysOperationLogDao operationLogDao;

    @Pointcut("execution(* com.dc.controller..*.*(..))")
    public void pointCut() {

    }

    /**
     * @return void
     * @title:<h3> 前置通知，用于获得请求参数，req,sesssion <h3>
     * @author: Enzo
     * @date: 2018-11-19 16:11
     * @params [joinPoint, operation]
     **/
    @Before("pointCut()&&@annotation(operation)")
    public void doBefore(JoinPoint joinPoint, AopOperation operation)throws Exception {
        request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        session = request.getSession();
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            if (args[i].getClass() == BaseModel.class) {
                baseModel = (BaseModel) args[i];
            }
            //log.error("参数[" + (i + 1) + "]：" + JSON.toJSONString(args[i]));
        }
        //判断是否有权限
        checkOperationPermission(operation);
        //转化查询参数
        QueryUtil.convertAdvancedQuery(baseModel);
    }

    /**
     * @title:<h3> 判断是否有权限 <h3>
     * @author: Enzo
     * @date: 2018-11-20 14:23
     * @params [operation]
     * @return void
     **/
    private void checkOperationPermission(AopOperation operation)throws Exception{
        String code = operation.menu().getCode() + "";//获得权限编号
        //判断当前操作类型在权限值中的序列
        int num = -1;//存储权限值序列
        String[] typeNames = operation.menu().getValue().split(",");
        for (int i = 0; i < typeNames.length; i++) {
            if (operation.type().equals(typeNames[i])) {
                num = i;
                break;
            }
        }
        if (num < 0) {//操作类型不存在
            throw new BusinessException(ErrorMesgEnum.NO_PERMISSIONS.getMesg()
                    .replace("{module}", operation.menu().getName())
                    .replace("{type}", operation.type()));

        }
        //从session获得当前登录用户的权限值
        Map<String, String> mapPermission = (Map<String, String>) session.getAttribute(BaseContants.LOGIN_PERMISSION);
        if (mapPermission == null) {//未登录
            throw new BusinessException(ErrorMesgEnum.NO_LOGIN);
        }
        String permissionValue=mapPermission.get(code);//获得当前操作模块的权限值
        if(permissionValue==null){//没有该模块的权限
            throw new BusinessException(ErrorMesgEnum.NO_PERMISSIONS.getMesg()
                    .replace("{module}", operation.menu().getName())
                    .replace("{type}", operation.type()));
        }else if(permissionValue.length()<num+1){//找不到操作的权限序列
            throw new BusinessException(ErrorMesgEnum.NO_PERMISSIONS.getMesg()
                    .replace("{module}", operation.menu().getName())
                    .replace("{type}", operation.type()));
        }else{
            //找到权限序列后，判断是否有该权限值
            if(permissionValue.charAt(num)!='1'){//说明没权限
                throw new BusinessException(ErrorMesgEnum.NO_PERMISSIONS.getMesg()
                        .replace("{module}", operation.menu().getName())
                        .replace("{type}", operation.type()));
            }
        }
        //返回无权限按钮的dom选择器
        //1111101；查询，修改，删除
        //.permission_search;.permission_add;.permission_update;.permission_delete;
        //获取全部由权限控制的dom选择器
        String ids=GlobalVar.mapCodeMenu.get(code).getIds();
        StringBuilder permissionBtn=new StringBuilder("");
        if(ids!=null){
            String[] arrId=ids.split(";");
            for(int i=0;i<arrId.length&&i<permissionValue.length();i++){
                if(permissionValue.charAt(i)=='1'){//说明有权限
                    permissionBtn.append(",");
                    permissionBtn.append(arrId[i]);
                    //,.permission_delete,.permission_update,.permission_search
                }
            }
        }
        if(permissionBtn.length()>0){//去掉第一个逗号
            permissionBtn.replace(0,1,"");
        }
        baseModel.setPermission_btns(permissionBtn.toString());
    }

    /**
     * @return void
     * @title:<h3> 后置通知，记录操作日志 <h3>
     * @author: Enzo
     * @date: 2018-11-20 14:03
     * @params [joinPoint, operation]
     **/
    @After("pointCut()&&@annotation(operation)")
    public void doAfter(JoinPoint joinPoint, AopOperation operation) {
        SysOperationLog operationLog = new SysOperationLog();
        operationLog.setRequest_ip(request.getRemoteAddr());
        SysUser user = (SysUser) session.getAttribute(BaseContants.LOGIN_USER);
        if (user == null) {
            throw new BusinessException(ErrorMesgEnum.NO_LOGIN);
        }
        operationLog.setUser_account(user.getAccount());
        operationLog.setUser_id(user.getUser_id());
        operationLog.setUser_name(user.getName());
        operationLog.setModule(operation.menu().getName());
        operationLog.setType(operation.type());
        operationLog.setContent(operation.desc() + "|" + baseModel.getAop_mesg());
        operationLog.setRequest_method(joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature());
        if (operation.saveLog()) {
            int count = operationLogDao.insert(operationLog);
            if (count == 0) {
                throw new BusinessException("新增操作日志失败");
            }
        }
    }
}
