package com.bo.springboot.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("sys_user_role")
public class SysUserRole implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * user_id
     */
    private Integer userId;

    /**
     * role_id
     */
    private Integer roleId;

    public SysUserRole() {
    }

}
