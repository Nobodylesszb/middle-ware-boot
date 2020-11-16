package com.bo.springboot.admin.datasource.annotation;

import java.lang.annotation.*;

/**
 * @auther: bo
 * @Date: 2020/11/16 16:17
 * @version:
 * @description: 多数据源注解
 */

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface DataSource {
    String value() default "";
}
