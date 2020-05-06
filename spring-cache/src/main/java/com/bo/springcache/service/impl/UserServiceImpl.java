package com.bo.springcache.service.impl;

import com.bo.springcache.dao.UserDao;
import com.bo.springcache.model.UserEntity;
import com.bo.springcache.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: bo
 * @Date: 2020/5/6 21:21
 * @Version:
 * @Description:
 */
@Service
@CacheConfig(cacheNames = {"userCache"})
@SuppressWarnings("all")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    @Cacheable("userList") // 标志读取缓存操作，如果缓存不存在，则调用目标方法，并将结果放入缓存
    public List<UserEntity> getAll() {
        System.out.println("缓存不存在，执行方法");
        return userDao.getAll();
    }

    @Override
    @Cacheable(cacheNames = { "user" }, key = "#id")//如果缓存存在，直接读取缓存值；如果缓存不存在，则调用目标方法，并将结果放入缓存
    public UserEntity getOne(Long id) {
        System.out.println("缓存不存在，执行方法");
        return userDao.getOne(id);
    }

    @Override
    @CachePut(cacheNames = { "user" }, key = "#user.id")//写入缓存，key为user.id,一般该注解标注在新增方法上
    public void insertUser(UserEntity user) {
        System.out.println("写入缓存");
        userDao.insertUser(user);
    }

    @Override
    @CacheEvict(cacheNames = { "user" }, key = "#user.id")//根据key清除缓存，一般该注解标注在修改和删除方法上
    public void updateUser(UserEntity user) {
        System.out.println("清除缓存");
        userDao.updateUser(user);
    }

    @Override
    @CacheEvict(value="userCache",allEntries=true)//方法调用后清空所有缓存
    public void deleteAll1() {

    }

    @Override
    @CacheEvict(value="userCache",beforeInvocation=true)//方法调用前清空所有缓存
    public void deleteAll2() {

    }
}
