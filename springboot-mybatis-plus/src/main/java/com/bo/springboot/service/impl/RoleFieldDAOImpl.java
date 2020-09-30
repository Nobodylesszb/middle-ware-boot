package com.bo.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bo.springboot.dao.UserDao;
import com.bo.springboot.model.UserEntity;
import com.bo.springboot.service.UserService;
import org.springframework.stereotype.Repository;

@Repository
public class RoleFieldDAOImpl extends ServiceImpl<UserDao, UserEntity>
        implements UserService {

}