package com.dc.base.aop;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @author Enzo
 * @Description TODO
 * @date 2018-11-19 14:52
 */
//@Aspect
//@Component
public class AopAspect {
    Logger log = Logger.getLogger(AopAspect.class);

    @Pointcut("execution(* com.dc.controller..*.*(..))")
    public void pointCut() {

    }

    @Before("pointCut()")
    public void before(JoinPoint point) throws Exception {
        Object[] arrs = point.getArgs();
        log.error("【前置通知】" + point.getTarget().getClass().getName() + "." + point.getSignature().getName());
    }

    @AfterReturning("pointCut()")
    public void afterReturning(JoinPoint point) throws Exception {
        Object[] arrs = point.getArgs();
        log.error("【最终通知】" + point.getTarget().getClass().getName() + "." + point.getSignature().getName());
    }

    @After("pointCut()")
    public void after(JoinPoint point) throws Exception {
        Object[] arrs = point.getArgs();
        log.error("【后置通知】" + point.getTarget().getClass().getName() + "." + point.getSignature().getName());
    }

    @AfterThrowing("pointCut()")
    public void afterThrowing(JoinPoint point) throws Exception {
        Object[] arrs = point.getArgs();
        log.error("【异常通知】" + point.getTarget().getClass().getName() + "." + point.getSignature().getName());
    }

    @Around("pointCut()")
    public Object doAround(ProceedingJoinPoint point) throws Exception {
        Object[] arrs = point.getArgs();
        log.error("【环绕通知】" + point.getTarget().getClass().getName() + "." + point.getSignature().getName());
        Object result = null;
        try {
            log.error("前置通知");
            result = point.proceed();//controller的方法
            log.error("后置通知");
        } catch (Throwable e) {
            log.error("异常通知");
        } finally {
            log.error("最终通知");
        }
        return result;
    }
}
