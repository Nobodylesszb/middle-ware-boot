package com.bo.ioc.entity;

import lombok.Data;

/**
 * @auther: bo
 * @Date: 2020/9/30 11:00
 * @version:
 * @description:
 */
@Data
public class Car {
    private String name;
    private String length;
    private String width;
    private String height;
    private Wheel wheel;
}
