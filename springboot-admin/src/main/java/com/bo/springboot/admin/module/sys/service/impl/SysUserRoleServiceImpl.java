package com.bo.springboot.admin.module.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bo.springboot.admin.common.utils.MapUtils;
import com.bo.springboot.admin.module.sys.dao.SysUserRoleDao;
import com.bo.springboot.admin.module.sys.entity.SysUserRoleEntity;
import com.bo.springboot.admin.module.sys.service.SysUserRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @auther: bo
 * @Date: 2020/11/3 11:37
 * @version:
 * @description:
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleDao, SysUserRoleEntity> implements SysUserRoleService {
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveOrUpdate(Long userId, List<Long> roleIdList) {
        //先删除用户与角色关系
        this.removeByMap(new MapUtils().put("user_id", userId));

        if(roleIdList == null || roleIdList.size() == 0){
            return ;
        }

        List<SysUserRoleEntity> sysUserRoleEntities = roleIdList.stream().map(roleId -> {
            SysUserRoleEntity sysUserRoleEntity = new SysUserRoleEntity();
            sysUserRoleEntity.setUserId(userId);
            sysUserRoleEntity.setRoleId(roleId);
            return sysUserRoleEntity;
        }).collect(Collectors.toList());
        this.saveBatch(sysUserRoleEntities);
    }

    @Override
    public List<Long> queryRoleIdList(Long userId) {
        return baseMapper.queryRoleIdList(userId);
    }

    @Override
    public int deleteBatch(Long[] roleIds) {
        return baseMapper.deleteBatch(roleIds);
    }
}
