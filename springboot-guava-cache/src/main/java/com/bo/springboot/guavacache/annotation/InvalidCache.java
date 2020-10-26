package com.bo.springboot.guavacache.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author bo
 * @Date 2020/10/25 21:07
 * @description
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface InvalidCache {
    //缓存的名字
    String[] name() default {};

    //缓存要失效的参数名字
    String invalid() default "";

    //前端传入的对象名字
    String IncomingObjectName() default "";

    //前端传入的对象
    Class IncomingObject();
}
