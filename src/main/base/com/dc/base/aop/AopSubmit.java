package com.dc.base.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @title:<h3> 表单防重注解类 <h3>
 * @author: Enzo
 * @date: 2018-11-23 13:41
 * @params
 * @return
 **/
@Target(ElementType.METHOD)//标记注解用于方法
@Retention(RetentionPolicy.RUNTIME)//可以反射获得注解参数
public @interface AopSubmit {
}
