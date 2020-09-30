package com.bo.springboot.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.bo.springboot.model.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


/**
 * @author bo
 */
@Mapper
public interface UserDao extends BaseMapper<UserEntity> {

}
