package com.dc.base.aop;

import com.dc.base.em.RoleMenuEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)//标记注解用于方法
@Retention(RetentionPolicy.RUNTIME)//可以反射获得注解参数
public @interface AopOperation {
    /**
     * @title:<h3> 描述信息 <h3>
     * @author: Enzo
     * @date: 2018-11-19 15:44
     * @params []
     * @return java.lang.String
     **/
    String desc() default "";
/**
 * @title:<h3> 操作类型 <h3>
 * @author: Enzo
 * @date: 2018-11-19 15:44
 * @params []
 * @return java.lang.String
 **/
    String type();
/**
 * @title:<h3> 权限菜单 <h3>
 * @author: Enzo
 * @date: 2018-11-19 15:44
 * @params []
 * @return com.dc.base.em.RoleMenuEnum
 **/
    RoleMenuEnum menu();
/**
 * @title:<h3> 是否记录操作日志 <h3>
 * @author: Enzo
 * @date: 2018-11-19 15:44
 * @params []
 * @return boolean
 **/
    boolean saveLog() default true;
}
