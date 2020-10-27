package com.bo.springboot.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bo.springboot.dao.SysUserRoleDAO;
import com.bo.springboot.entity.SysUserRole;
import com.bo.springboot.mapper.SysUserRoleMapper;
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
