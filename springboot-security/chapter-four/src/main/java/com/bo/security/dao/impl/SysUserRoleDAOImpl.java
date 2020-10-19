package com.bo.security.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bo.security.dao.SysUserRoleDAO;
import com.bo.security.entity.SysUserRole;
import com.bo.security.mapper.SysUserRoleMapper;
import org.springframework.stereotype.Repository;

/**
 * @auther: bo
 * @Date: 2020/10/13 12:00
 * @version:
 * @description:
 */
@Repository
public class SysUserRoleDAOImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleDAO {
}
