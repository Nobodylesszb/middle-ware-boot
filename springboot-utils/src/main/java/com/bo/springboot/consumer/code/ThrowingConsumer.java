package com.bo.springboot.consumer.code;

/**
 * @auther bo
 * @Date 2021/6/22 18:09
 * @description
 */
@FunctionalInterface
public interface ThrowingConsumer {

    void accept() throws Exception;

    static void wrap(ThrowingConsumer throwingConsumer) {
        try {
            throwingConsumer.accept();
        } catch (Throwable ignored) {
        }
    }
}
