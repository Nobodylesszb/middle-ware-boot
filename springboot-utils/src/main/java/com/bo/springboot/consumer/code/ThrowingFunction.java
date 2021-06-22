package com.bo.springboot.consumer.code;

import cn.hutool.core.lang.func.Func;

/**
 * @auther bo
 * @Date 2021/6/22 17:46
 * @description
 */
@FunctionalInterface
public interface ThrowingFunction<T> {
    T apply() throws Exception;


    static <T> T wrap(ThrowingFunction<T> fun) throws Exception {
        try {
            return fun.apply();
        } catch (Throwable e) {
            throw new Exception(e.getMessage());
        }
    }

    static <T> T wrapWithoutException(ThrowingFunction<T> fun) {
        try {
            return fun.apply();
        } catch (Throwable ignored) {
        }
        return null;
    }
}
