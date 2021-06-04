package com.bo.springboot.utils;

@FunctionalInterface
public interface CollBeanUtilsCallBack<S, T> {

    void callBack(S t, T s);
}