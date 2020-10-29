package com.bo.springboot.annotation;

import java.lang.annotation.*;

/**
 * @auther: bo
 * @Date: 2020/10/29 17:33
 * @version:
 * @description:
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface DataSource {

    String value() default "";
}
