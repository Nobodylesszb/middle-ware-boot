package com.bo.security.service;

import com.bo.security.entity.SysUserRole;

import java.util.List;

/**
 * @auther: bo
 * @Date: 2020/10/13 16:05
 * @version:
 * @description:
 */
public interface SysUserRoleService {
    List<SysUserRole> listByUserId(Integer id);
}
