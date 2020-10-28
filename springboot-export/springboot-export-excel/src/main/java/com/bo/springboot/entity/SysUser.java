package com.bo.springboot.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @auther: bo
 * @Date: 2020/10/13 11:46
 * @version:
 * @description:
 */
@Data
public class SysUser {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Integer id;

    /**
     * name
     */
    private String name;

    /**
     * password
     */
    private String password;


}
