package com.bo.springboot.service;


import com.bo.springboot.entity.SysRole;

/**
 * @auther: bo
 * @Date: 2020/10/13 14:15
 * @version:
 * @description:
 */
public interface SysRoleService {
    SysRole selectById(Integer roleId);
}
