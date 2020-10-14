package com.bo.security.service;

import com.bo.security.entity.SysUser;

/**
 * @auther: bo
 * @Date: 2020/10/13 16:05
 * @version:
 * @description:
 */
public interface SysUserService {
    SysUser selectByName(String username);
}
