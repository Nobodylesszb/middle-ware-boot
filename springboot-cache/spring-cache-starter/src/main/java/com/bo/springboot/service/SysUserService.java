package com.bo.springboot.service;


import com.bo.springboot.entity.SysUser;

import java.util.List;

/**
 * @auther: bo
 * @Date: 2020/10/13 16:05
 * @version:
 * @description:
 */
public interface SysUserService {
    SysUser selectByName(String username);

    List<SysUser> getAll();

    SysUser getOne(Long  id);

    boolean insertUser(SysUser sysUser);

    Boolean updateUser(SysUser  sysUser);

    Boolean deleteUser(SysUser  sysUser);


}
