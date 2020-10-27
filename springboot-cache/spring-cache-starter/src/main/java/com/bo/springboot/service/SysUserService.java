package com.bo.springboot.service;


import com.bo.springboot.entity.SysUser;

/**
 * @auther: bo
 * @Date: 2020/10/13 16:05
 * @version:
 * @description:
 */
public interface SysUserService {
    SysUser selectByName(String username);
}
