package com.bo.springboot.admin.module.sys.service;

import com.bo.springboot.admin.module.sys.entity.SysUserEntity;
import com.bo.springboot.admin.module.sys.entity.SysUserTokenEntity;

import java.util.Set;

/**
 * @auther: bo
 * @Date: 2020/11/3 14:43
 * @version: shiro service配置
 * @description:
 */
public interface ShiroService {
    /**
     * 获取用户权限列表
     */
    Set<String> getUserPermissions(long userId);

    SysUserTokenEntity queryByToken(String token);

    /**
     * 根据用户ID，查询用户
     * @param userId
     */
    SysUserEntity queryUser(Long userId);

}
