package com.bo.springboot.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @auther: bo
 * @Date: 2020/10/13 11:17
 * @version:
 * @description:
 */
@Data
@TableName("sys_role")
public class SysRole implements Serializable {
    static final long serialVersionUID = 1L;

    private Integer id;

    private String name;
}
