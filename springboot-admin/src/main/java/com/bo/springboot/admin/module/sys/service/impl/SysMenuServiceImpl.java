package com.bo.springboot.admin.module.sys.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bo.springboot.admin.common.utils.Constant;
import com.bo.springboot.admin.common.utils.MapUtils;
import com.bo.springboot.admin.module.sys.dao.SysMenuDao;
import com.bo.springboot.admin.module.sys.entity.SysMenuEntity;
import com.bo.springboot.admin.module.sys.service.SysMenuService;
import com.bo.springboot.admin.module.sys.service.SysRoleMenuService;
import com.bo.springboot.admin.module.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @auther: bo
 * @Date: 2020/11/3 09:45
 * @version:
 * @description:
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuDao, SysMenuEntity> implements SysMenuService {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysRoleMenuService sysRoleMenuService;
    /**
     * 筛选需要的用户菜单id
     * @param parentId 父菜单ID
     * @param menuIdList  用户菜单ID
     * @return
     */
    @Override
    public List<SysMenuEntity> queryListParentId(Long parentId, List<Long> menuIdList) {
        List<SysMenuEntity> menuList = queryListParentId(parentId);
        if(CollectionUtil.isEmpty(menuIdList)){
            return menuList;
        }
        List<SysMenuEntity> userMenuList = menuList.stream().filter(menuIdList::contains).collect(Collectors.toList());
        //判断
        return CollectionUtil.isEmpty(userMenuList)? menuList:menuList;
    }

    /**
     *
     * @param parentId 父菜单ID
     * @return
     */
    @Override
    public List<SysMenuEntity> queryListParentId(Long parentId) {
        return this.baseMapper.queryListParentId(parentId);
    }

    @Override
    public List<SysMenuEntity> queryNotButtonList() {
        return this.baseMapper.queryNotButtonList();
    }

    @Override
    public List<SysMenuEntity> getUserMenuList(Long userId) {
        //系统管理员，拥有最高权限
        if(userId == Constant.SUPER_ADMIN){
            return getAllMenuList(null);
        }

        //用户菜单列表
        List<Long> menuIdList = sysUserService.queryAllMenuId(userId);
        return getAllMenuList(menuIdList);
    }

    private List<SysMenuEntity> getAllMenuList(List<Long> menuIdList) {
        //查询根菜单列表 如果用户id存在查出用户id
        List<SysMenuEntity> menuList = queryListParentId(0L, menuIdList);
        //递归获取子菜单
        return getMenuTreeList(menuList, menuIdList);
    }

    /**
     * 递归
     */
    private List<SysMenuEntity> getMenuTreeList(List<SysMenuEntity> menuList, List<Long> menuIdList){
        List<SysMenuEntity> subMenuList = new ArrayList<SysMenuEntity>();

        for(SysMenuEntity entity : menuList){
            //目录
            //这里筛选是够需要
            if(entity.getType() == Constant.MenuType.CATALOG.getValue()){
                entity.setList(getMenuTreeList(queryListParentId(entity.getMenuId(), menuIdList), menuIdList));
            }
            subMenuList.add(entity);
        }

        return subMenuList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long menuId) {
        //删除菜单
        this.removeById(menuId);
        //删除菜单与角色关联
        sysRoleMenuService.removeByMap(new MapUtils().put("menu_id", menuId));
    }
}
