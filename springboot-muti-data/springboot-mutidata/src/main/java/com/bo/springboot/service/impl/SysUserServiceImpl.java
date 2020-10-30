package com.bo.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bo.springboot.annotation.DataSource;
import com.bo.springboot.dao.SysUserDAO;
import com.bo.springboot.entity.SysUser;
import com.bo.springboot.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @auther: bo
 * @Date: 2020/10/13 16:05
 * @version:
 * @description:
 */
@Service
@CacheConfig(cacheNames = {"userCache"}) //兜底方法
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserDAO sysUserDAO;

    @Override
    public SysUser selectByName(String username) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysUser::getName, username);
        return sysUserDAO.getOne(queryWrapper);
    }

    @Override
    public List<SysUser> getAll() {
        return sysUserDAO.list();
    }


    @Override
    @DataSource(value = "slave1")
    public SysUser getOne(Long id) {
        return sysUserDAO.getById(id);
    }

    @Override
    public Boolean insertUser(SysUser sysUser) {
        return sysUserDAO.save(sysUser);
    }

    @Override
    public Boolean updateUser(SysUser sysUser) {
        return sysUserDAO.updateById(sysUser);
    }

    @Override
    public Boolean deleteUser(SysUser sysUser) {

        return sysUserDAO.removeById(sysUser);
    }


    @Override
    public Boolean deleteAll1() {
        return true;
    }

    @Override
    public Boolean deleteAll2() {
        return true;
    }
}
