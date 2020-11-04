package com.bo.springboot.admin.common.annotation;

import java.lang.annotation.*;

/**
 * @auther: bo
 * @Date: 2020/11/4 10:49
 * @version:
 * @description:
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {
    String value() default "";
}
