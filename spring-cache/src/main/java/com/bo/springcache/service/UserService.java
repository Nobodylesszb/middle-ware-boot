package com.bo.springcache.service;

import com.bo.springcache.model.UserEntity;

import java.util.List;

/**
 * @Auther: bo
 * @Date: 2020/5/6 21:20
 * @Version:
 * @Description:
 */

public interface UserService {
    /**
     * 查找所有
     * @return
     */
    List<UserEntity> getAll();
    /**
     * 根据id获取用户
     * @param id
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


    void deleteAll1();

    void deleteAll2();

}
