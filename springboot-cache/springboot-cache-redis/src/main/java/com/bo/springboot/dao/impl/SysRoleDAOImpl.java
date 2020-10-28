package com.bo.springboot.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bo.springboot.dao.SysRoleDAO;
import com.bo.springboot.entity.SysRole;
import com.bo.springboot.mapper.SysRoleMapper;
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
