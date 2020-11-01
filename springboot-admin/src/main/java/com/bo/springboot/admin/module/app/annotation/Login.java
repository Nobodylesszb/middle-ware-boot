package com.bo.springboot.admin.module.app.annotation;

/**
 * @auther: bo
 * @Date: 2020/11/1 09:50
 * @version:
 * @description:
 */

import java.lang.annotation.*;

/**
 * app登录效验
 *
 * @author Mark sunlightcs@gmail.com
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Login {
}
