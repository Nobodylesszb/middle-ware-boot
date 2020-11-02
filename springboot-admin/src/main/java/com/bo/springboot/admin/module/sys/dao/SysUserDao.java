package com.bo.springboot.admin.module.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bo.springboot.admin.module.sys.entity.SysConfigEntity;
import com.bo.springboot.admin.module.sys.entity.SysUserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @auther: bo
 * @Date: 2020/11/2 17:13
 * @version:
 * @description:
 */
@Mapper
public interface SysUserDao extends BaseMapper<SysUserEntity> {
    /**
     * 查询用户的所有权限
     * @param userId  用户ID
     */
    List<String> queryAllPerms(@Param("userId") Long userId);

    /**
     * 查询用户的所有菜单ID
     */
    List<Long> queryAllMenuId(@Param("userId") Long userId);

    /**
     * 根据用户名，查询系统用户
     */
    SysUserEntity queryByUserName(@Param("username") String username);

}
