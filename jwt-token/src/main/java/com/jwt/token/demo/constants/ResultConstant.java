package com.jwt.token.demo.constants;

public enum ResultConstant implements BaseEnumType {
    ERROR("500", "系统异常"),
    SUCCESS("0", "ok");

    private String code;
    private String msg;

    private ResultConstant(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}