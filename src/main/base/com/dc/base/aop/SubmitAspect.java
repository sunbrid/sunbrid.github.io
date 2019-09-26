package com.dc.base.aop;

import com.dc.base.contants.BaseContants;
import com.dc.base.em.ErrorMesgEnum;
import com.dc.base.exception.BusinessException;
import com.dc.base.pojo.BaseModel;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Enzo
 * @Description 表单防重切面类
 * @date 2018-11-23 13:41
 */
@Aspect
@Component
public class SubmitAspect {
    protected HttpServletRequest request;
    protected HttpSession session;
    protected BaseModel baseModel = new BaseModel();

    @Pointcut("execution(* com.dc.controller..*.*(..))")
    public void pointCut() {

    }

    @Before("pointCut()&&@annotation(aopSubmit)")
    public void doBefore(JoinPoint joinPoint, AopSubmit aopSubmit) throws Exception {
        System.out.println("进入表单防重切面");
        request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        session = request.getSession();
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            if (args[i].getClass() == BaseModel.class) {
                baseModel = (BaseModel) args[i];
            }
        }
        String sessionToken = (String) session.getAttribute(BaseContants.SESSION_TOKEN);
        if (baseModel.getToken() == null || "".equals(baseModel.getToken())) {//如果前台没有传入token
            throw new BusinessException(ErrorMesgEnum.NOT_TOKEN);
        } else if (sessionToken == null || "".equals(sessionToken)) {//如果不存在token，则保存前台传入的token
            session.setAttribute(BaseContants.SESSION_TOKEN, baseModel.getToken());
        } else if (sessionToken.equals(baseModel.getToken())) {//比较session中的token和前台传入的token是否一致
            throw new BusinessException(ErrorMesgEnum.RESUBMIT);
        }else{
            session.setAttribute(BaseContants.SESSION_TOKEN, baseModel.getToken());
        }
    }

}
