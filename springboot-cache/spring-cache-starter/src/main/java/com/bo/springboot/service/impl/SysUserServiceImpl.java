package com.bo.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bo.springboot.dao.SysUserDAO;
import com.bo.springboot.entity.SysUser;
import com.bo.springboot.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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
    @Cacheable(cacheNames = {"user"}, key = "#id")//如果缓存存在，直接读取缓存值；如果缓存不存在，则调用目标方法，并将结果放入缓存
    public SysUser getOne(Long id) {
        return sysUserDAO.getById(id);
    }

    @Override
    @CachePut(cacheNames = {"user"}, key = "#sysUser.id")//写入缓存，key为user.id,一般该注解标注在新增方法上
    public Boolean insertUser(SysUser sysUser) {
        return sysUserDAO.save(sysUser);
    }

    @Override
    @CacheEvict(cacheNames = {"user"}, key = "#sysUser.id")
    public Boolean updateUser(SysUser sysUser) {
        return sysUserDAO.updateById(sysUser);
    }

    @Override
    @CacheEvict(cacheNames = {"user"}, key = "#sysUser.id")//根据key清除缓存，一般该注解标注在修改和删除方法上
    public Boolean deleteUser(SysUser sysUser) {

        return sysUserDAO.removeById(sysUser);
    }


    @Override
    @CacheEvict(value = "userCache", allEntries = true)//方法调用后清空所有缓存
    public Boolean deleteAll1() {
        return true;
    }

    @Override
    @CacheEvict(value = "userCache", beforeInvocation = true)//方法调用前清空所有缓存
    public Boolean deleteAll2() {
        return true;
    }
}
