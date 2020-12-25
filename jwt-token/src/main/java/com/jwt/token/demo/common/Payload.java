package com.jwt.token.demo.common;

import com.jwt.token.demo.constants.BaseEnumType;
import com.jwt.token.demo.constants.ResultConstant;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

public class Payload<T> implements Serializable {
    private static final long serialVersionUID = -1549643581827130116L;
    private T payload;
    private String code;
    private String msg;

    public Payload() {
        this.code = ResultConstant.SUCCESS.getCode();
        this.msg = ResultConstant.SUCCESS.getMsg();
    }

    public Payload(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Payload(T payload) {
        this.code = ResultConstant.SUCCESS.getCode();
        this.msg = ResultConstant.SUCCESS.getMsg();
        this.payload = payload;
    }

    public Payload(BaseEnumType be) {
        this.code = be.getCode();
        this.msg = be.getMsg();
    }

    public Payload(T payload, String code, String msg) {
        this.payload = payload;
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getPayload() throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, NoSuchFieldException {
        return this.payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }

    public boolean success() {
        return this.getCode().equals("0");
    }

    private Payload(Throwable e) {
        ResultConstant resultConstant = ResultConstant.ERROR;
        this.code = resultConstant.getCode();
        this.msg = resultConstant.getMsg();
        if (e != null && !StringUtils.isEmpty(e.toString())) {
            this.msg = "系统异常：" + e.toString();
        }

    }
}
