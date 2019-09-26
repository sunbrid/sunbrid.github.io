package com.dc.controller;

import com.dc.base.contants.BaseContants;
import com.dc.base.controller.BaseController;
import com.dc.base.em.ErrorMesgEnum;
import com.dc.base.exception.BusinessException;
import com.dc.base.pojo.BaseModel;
import com.dc.base.rsa.RSAJS;
import com.dc.pojo.SysLoginLog;
import com.dc.pojo.SysMenu;
import com.dc.service.LoginService;
import com.dc.service.SysMenuService;
import com.dc.service.SysRoleService;
import com.wordnik.swagger.annotations.ApiImplicitParam;
import com.wordnik.swagger.annotations.ApiImplicitParams;
import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @author Enzo
 * @Description TODO
 * @date 2018-11-12 15:39
 */
@Controller
@ResponseBody
@RequestMapping("index")
public class LoginController extends BaseController {
    @Autowired
    LoginService loginService;
    @Autowired
    SysMenuService menuService;
    @Autowired
    SysRoleService roleService;

    /**
     * @return com.dc.base.pojo.BaseModel
     * @title:<h3> 用户登录 <h3>
     * @author: Enzo
     * @date: 2018-11-12 15:56
     * @params [loginLog, baseModel]
     **/
    @ApiOperation(value = "登录", notes = "登录", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user_account", value = "帐号"
                    , dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "user_password", value = "密码"
                    , dataType = "String", paramType = "query")
    })
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public BaseModel login(SysLoginLog loginLog, BaseModel baseModel) throws Exception {

        if (loginLog == null) {
            throw new BusinessException(ErrorMesgEnum.NO_LOGIN.LOGIN_NULL_USER);
        } else if (loginLog.getUser_account() == null || "".equals(loginLog.getUser_account()) || loginLog.getUser_password() == null || "".equals(loginLog.getUser_password())) {
            throw new BusinessException(ErrorMesgEnum.NO_LOGIN.LOGIN_NULL_USER);
        } else {
            loginLog.setType(0);//标志pc登录
            loginLog.setRequest_ip(request.getRemoteAddr());
            loginLog.setModel_number(request.getHeader("User-Agent"));
            loginLog.setSession_id(session.getId());
            //解密
            try {
                String password = RSAJS.sessionDecryptHexStr(session, loginLog.getUser_password());
                loginLog.setUser_password(password);
            } catch (Exception e) {
                throw new BusinessException(ErrorMesgEnum.LOGIN_PASSWORD);
            }
            loginService.login(loginLog, baseModel);
            //if(baseModel.getResultCode()>0){
            //  throw  new BusinessException(baseModel.getResultCode(),baseModel.getMessage());
            //}
            session.setAttribute(BaseContants.LOGIN_USER, baseModel.getData());
            session.setAttribute(BaseContants.LOGIN_PERMISSION, roleService.selectMergeRolePermissionByUserId(loginLog.getUser_id()));
        }
        return baseModel;
    }

    /**
     * @return com.dc.base.pojo.BaseModel
     * @title:<h3> 查询权限菜单枚举类 <h3>
     * @author: Enzo
     * @date: 2018-11-14 11:02
     * @params [baseModel]
     **/
    @ApiOperation(value = "查询权限菜单枚举类", httpMethod = "GET")
    @RequestMapping("selectEnumRoleMenu")
    public BaseModel selectEnumRoleMenu(BaseModel baseModel) throws Exception {
        loginService.selectEnumRoleMenu(baseModel);
        baseModel.setMessage("查询权限菜单成功");
        return baseModel;
    }

    /**
     * @return com.dc.base.pojo.BaseModel
     * @title:<h3> 刷新权限菜单 <h3>
     * @author: Enzo
     * @date: 2018-11-14 14:23
     * @params [baseModel]
     **/
    @ApiOperation(value = "刷新权限菜单", httpMethod = "GET")
    @RequestMapping("refreshSysMenu")
    public BaseModel refreshSysMenu(BaseModel baseModel) throws Exception {
        menuService.refreshSysMenu(baseModel);
        baseModel.setMessage("刷新权限菜单成功");
        return baseModel;
    }

    /**
     * @return com.dc.base.pojo.BaseModel
     * @title:<h3> 查询当前用户权限菜单 <h3>
     * @author: Enzo
     * @date: 2018-11-20 11:37
     * @params [baseModel]
     **/
    @ApiOperation(value = "查询当前用户权限菜单", httpMethod = "GET", response = SysMenu.class)
    @RequestMapping("findMyMenu")
    public BaseModel findMyMenu(BaseModel baseModel) throws Exception {
        Map<String, String> map = (Map<String, String>) session.getAttribute(BaseContants.LOGIN_PERMISSION);
        if (map == null) {
            throw new BusinessException(ErrorMesgEnum.NO_LOGIN);
        }
        menuService.findMyMenu(map, baseModel);
        baseModel.setMessage("查询权限菜单成功");
        return baseModel;
    }

    /**
     * @return com.dc.base.pojo.BaseModel
     * @title:<h3> 注销 <h3>
     * @author: Enzo
     * @date: 2018-11-23 9:27
     * @params [baseModel]
     **/
    @ApiOperation(value = "注销", httpMethod = "GET")
    @RequestMapping("loginOut")
    public BaseModel loginOut(SysLoginLog loginLog,BaseModel baseModel) throws Exception {
        loginLog.setSession_id(session.getId());
        loginLog.setLoginout_type(1);//标志主动注销
        loginService.loginOut(loginLog);
        session.invalidate();
        baseModel.setMessage("注销成功");
        return baseModel;
    }
}
