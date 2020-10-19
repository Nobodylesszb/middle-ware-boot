package com.bo.security.service;

import com.bo.security.entity.SysRole;

/**
 * @auther: bo
 * @Date: 2020/10/13 14:15
 * @version:
 * @description:
 */
public interface SysRoleService {
    SysRole selectById(Integer roleId);
}
