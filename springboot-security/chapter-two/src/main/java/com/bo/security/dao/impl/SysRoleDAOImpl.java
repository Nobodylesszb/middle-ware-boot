package com.bo.security.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bo.security.dao.SysRoleDAO;
import com.bo.security.entity.SysRole;
import com.bo.security.mapper.SysRoleMapper;
import org.springframework.stereotype.Repository;

/**
 * @auther: bo
 * @Date: 2020/10/13 11:38
 * @version:
 * @description:
 */
@Repository
public class SysRoleDAOImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleDAO {
}
