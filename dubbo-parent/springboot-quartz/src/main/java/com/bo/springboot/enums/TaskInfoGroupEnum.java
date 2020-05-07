package com.bo.springboot.enums;

/**
 * @Auther: bo
 * @Date: 2020/5/7 21:52
 * @Version:
 * @Description:
 */
public enum  TaskInfoGroupEnum {
    REQUEST(1, "RequestTask");

    private Integer code;

    private String name;

    TaskInfoGroupEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
