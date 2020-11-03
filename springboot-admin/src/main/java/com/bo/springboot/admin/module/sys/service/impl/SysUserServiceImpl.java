package com.bo.springboot.admin.module.sys.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bo.springboot.admin.common.exception.RRException;
import com.bo.springboot.admin.common.utils.Constant;
import com.bo.springboot.admin.common.utils.PageUtils;
import com.bo.springboot.admin.common.utils.Query;
import com.bo.springboot.admin.module.sys.dao.SysUserDao;
import com.bo.springboot.admin.module.sys.entity.SysUserEntity;
import com.bo.springboot.admin.module.sys.service.SysRoleService;
import com.bo.springboot.admin.module.sys.service.SysUserService;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @auther: bo
 * @Date: 2020/11/2 17:24
 * @version:
 * @description:
 */
@Service
@SuppressWarnings("all")
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUserEntity> implements SysUserService {
    @Autowired
    private SysRoleService sysRoleService;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String username = (String)params.get("username");
        Long createUserId = (Long)params.get("createUserId");

        IPage<SysUserEntity> page = this.page(
                new Query<SysUserEntity>().getPage(params),
                new QueryWrapper<SysUserEntity>()
                        .like(StringUtils.isNotBlank(username),"username", username)
                        .eq(createUserId != null,"create_user_id", createUserId)
        );

        return new PageUtils(page);
    }

    @Override
    public List<String> queryAllPerms(Long userId) {
        return baseMapper.queryAllPerms(userId);
    }

    @Override
    public List<Long> queryAllMenuId(Long userId) {
        return baseMapper.queryAllMenuId(userId);
    }

    @Override
    public SysUserEntity queryByUserName(String username) {
        return baseMapper.queryByUserName(username);
    }


    @Override
    public void saveUser(SysUserEntity user) {
        user.setCreateTime(new Date());
        String salt = RandomStringUtils.randomAlphanumeric(20);
        user.setPassword(new Sha256Hash(user.getPassword(),salt).toHex());
        user.setSalt(salt);
        this.save(user);

    }

    @Override
    public void update(SysUserEntity user) {
        if(StringUtils.isBlank(user.getPassword())){
            user.setPassword(null);
        }else {
            user.setPassword(new Sha256Hash(user.getPassword(),user.getSalt()).toHex());
        }
        this.updateById(user);
        //检查角色是否越权
        checkRole(user);
    }

    private void checkRole(SysUserEntity user) {
        if(CollectionUtil.isEmpty(user.getRoleIdList())){
            return;
        }
        //如果不是超级管理员，则需要判断用户的角色是否自己创建
        if(user.getCreateUserId() == Constant.SUPER_ADMIN){
            return ;
        }

        //查询用户创建的角色列表
        List<Long> roleIdList = sysRoleService.queryRoleIdList(user.getCreateUserId());

        //判断是否越权
        if(!roleIdList.containsAll(user.getRoleIdList())){
            throw new RRException("新增用户所选角色，不是本人创建");
        }

    }

    @Override
    public void deleteBatch(Long[] userIds) {
        this.removeByIds(Arrays.asList(userIds));
    }

    @Override
    public boolean updatePassword(Long userId, String password, String newPassword) {

        SysUserEntity sysUserEntity = new SysUserEntity();
        sysUserEntity.setPassword(newPassword);
        return this.update(sysUserEntity,new QueryWrapper<SysUserEntity>().eq("user_id", userId).eq("password", password));
    }
}
