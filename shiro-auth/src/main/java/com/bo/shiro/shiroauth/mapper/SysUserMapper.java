package com.bo.shiro.shiroauth.mapper;

import com.bo.shiro.shiroauth.entity.SysUserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface SysUserMapper {
    /**
     * 查询用户的所有权限
     * @param userId  用户ID
     */
    List<String> queryAllPerms(Long userId);

    /**
     * 根据用户名查询用户
     */
    SysUserEntity selectOne(@Param("userName") String userName) ;

    /**
     * 查询用户的所有菜单ID
     */
    List<Long> queryAllMenuId(Long userId);
}