package com.bo.security.service.impl;

import com.bo.security.dao.SysRoleDAO;
import com.bo.security.entity.SysRole;
import com.bo.security.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @auther: bo
 * @Date: 2020/10/13 14:17
 * @version:
 * @description:
 */

@Service
public class SysRoleServiceImpl implements SysRoleService {
    @Autowired
    private SysRoleDAO sysRoleDAO;

    @Override
    public SysRole selectById(Integer roleId) {
        return sysRoleDAO.getById(roleId);
    }
}
