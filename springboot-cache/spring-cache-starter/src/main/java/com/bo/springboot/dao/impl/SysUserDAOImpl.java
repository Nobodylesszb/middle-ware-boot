package com.bo.springboot.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bo.springboot.dao.SysUserDAO;
import com.bo.springboot.entity.SysUser;
import com.bo.springboot.mapper.SysUserMapper;
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
