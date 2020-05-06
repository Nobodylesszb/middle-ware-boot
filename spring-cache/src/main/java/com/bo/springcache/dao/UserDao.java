package com.bo.springcache.dao;

import com.bo.springcache.model.UserEntity;

import java.util.List;

/**
 * @Auther: bo
 * @Date: 2020/5/6 21:16
 * @Version:
 * @Description:
 */

public interface UserDao {
    //mapper.xml方式
    /**
     * 获取所有用户
     * @return
     */
    List<UserEntity> getAll();
    /**
     * 根据id获取用户
     * @return
     */
    UserEntity getOne(Long id);
    /**
     * 新增用户
     * @param user
     */
    void insertUser(UserEntity user);
    /**
     * 修改用户
     * @param user
     */
    void updateUser(UserEntity user);
    /**
     * 删除用户
     * @param id
     */
    void deleteUser(Long id);
}
