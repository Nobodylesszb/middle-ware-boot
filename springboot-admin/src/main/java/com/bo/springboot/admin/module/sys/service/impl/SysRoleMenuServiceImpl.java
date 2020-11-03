package com.bo.springboot.admin.module.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bo.springboot.admin.module.sys.dao.SysRoleMenuDao;
import com.bo.springboot.admin.module.sys.entity.SysRoleMenuEntity;
import com.bo.springboot.admin.module.sys.service.SysRoleMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @auther: bo
 * @Date: 2020/11/3 11:05
 * @version:
 * @description:
 */
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuDao, SysRoleMenuEntity> implements SysRoleMenuService {
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveOrUpdate(Long roleId, List<Long> menuIdList) {
        //先删除角色与菜单关系
        deleteBatch(new Long[]{roleId});
        if(menuIdList.size() == 0){
            return ;
        }
        List<SysRoleMenuEntity> sysRoleMenuEntities = menuIdList.stream().map(menuId -> {
            SysRoleMenuEntity sysRoleMenuEntity = new SysRoleMenuEntity();
            sysRoleMenuEntity.setMenuId(menuId);
            sysRoleMenuEntity.setId(roleId);
            return sysRoleMenuEntity;
        }).collect(Collectors.toList());
        this.saveBatch(sysRoleMenuEntities);
    }

    @Override
    public List<Long> queryMenuIdList(Long roleId) {
        return baseMapper.queryMenuIdList(roleId);
    }

    @Override
    public int deleteBatch(Long[] roleIds) {
        return baseMapper.deleteBatch(roleIds);
    }
}
