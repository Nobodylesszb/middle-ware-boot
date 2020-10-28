package com.bo.springboot.service;



import com.bo.springboot.entity.SysUserRole;

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
