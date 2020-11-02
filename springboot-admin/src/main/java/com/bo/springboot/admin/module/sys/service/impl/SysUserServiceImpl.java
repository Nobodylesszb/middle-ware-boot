package com.bo.springboot.admin.module.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bo.springboot.admin.common.utils.PageUtils;
import com.bo.springboot.admin.common.utils.Query;
import com.bo.springboot.admin.module.sys.dao.SysUserDao;
import com.bo.springboot.admin.module.sys.entity.SysUserEntity;
import com.bo.springboot.admin.module.sys.service.SysUserService;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * @auther: bo
 * @Date: 2020/11/2 17:24
 * @version:
 * @description:
 */
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUserEntity> implements SysUserService {
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

    }

    @Override
    public void update(SysUserEntity user) {

    }

    @Override
    public void deleteBatch(Long[] userIds) {

    }

    @Override
    public boolean updatePassword(Long userId, String password, String newPassword) {
        return false;
    }
}
