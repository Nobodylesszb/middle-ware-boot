package com.bo.security.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @auther: bo
 * @Date: 2020/10/13 11:46
 * @version:
 * @description:
 */
@Data
@TableName("sys_user")
public class SysUser implements Serializable {
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

    public SysUser() {
    }

}
