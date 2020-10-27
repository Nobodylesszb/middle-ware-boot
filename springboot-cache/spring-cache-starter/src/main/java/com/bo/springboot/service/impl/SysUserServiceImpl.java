package com.bo.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bo.springboot.dao.SysUserDAO;
import com.bo.springboot.entity.SysUser;
import com.bo.springboot.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @auther: bo
 * @Date: 2020/10/13 16:05
 * @version:
 * @description:
 */
@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserDAO sysUserDAO;

    @Override
    public SysUser selectByName(String username) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysUser::getName, username);
        return sysUserDAO.getOne(queryWrapper);
    }
}
