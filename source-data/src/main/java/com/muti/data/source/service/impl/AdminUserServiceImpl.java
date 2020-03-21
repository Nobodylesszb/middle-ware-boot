package com.muti.data.source.service.impl;

import com.muti.data.source.entity.AdminUser;
import com.muti.data.source.mapper.one.AdminUserMapper;
import com.muti.data.source.service.AdminUserService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

@Service
public class AdminUserServiceImpl implements AdminUserService {

    @Resource
    private AdminUserMapper adminUserMapper ;

    @Override
    public AdminUser selectByPrimaryKey (Integer id) {
        return adminUserMapper.selectByPrimaryKey(id) ;
    }
}
