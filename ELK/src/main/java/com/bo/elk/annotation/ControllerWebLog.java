package com.bo.elk.annotation;

import java.lang.annotation.*;

/**
 * @Auther: bo
 * @Date: 2020/7/13 15:14
 * @Version:
 * @Description:
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ControllerWebLog {

    /**
     * 接口名称
     */
    String name();

    /**
     * 日志是否入库
     */
    boolean intoDb() default  false;
}
