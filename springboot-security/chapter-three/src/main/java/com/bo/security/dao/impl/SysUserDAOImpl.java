package com.bo.security.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bo.security.dao.SysUserDAO;
import com.bo.security.entity.SysUser;
import com.bo.security.mapper.SysUserMapper;
import org.springframework.stereotype.Repository;

/**
 * @auther: bo
 * @Date: 2020/10/13 11:48
 * @version:
 * @description:
 */
@Repository
public class SysUserDAOImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserDAO {
}
