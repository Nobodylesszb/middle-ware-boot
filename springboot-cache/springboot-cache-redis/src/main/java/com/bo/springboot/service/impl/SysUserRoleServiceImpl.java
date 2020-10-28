package com.bo.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bo.springboot.dao.SysUserRoleDAO;
import com.bo.springboot.entity.SysUserRole;
import com.bo.springboot.service.SysUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @auther: bo
 * @Date: 2020/10/13 16:07
 * @version:
 * @description:
 */
@Service
public class SysUserRoleServiceImpl implements SysUserRoleService {
    @Autowired
    private SysUserRoleDAO userRoleDAO;

    @Override
    public List<SysUserRole> listByUserId(Integer id) {
        QueryWrapper<SysUserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysUserRole::getUserId, id);
        return userRoleDAO.list(queryWrapper);
    }
}
