package com.bo.springboot.admin.module.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bo.springboot.admin.common.utils.R;
import com.bo.springboot.admin.module.sys.entity.SysUserTokenEntity;

/**
 * @auther: bo
 * @Date: 2020/11/3 15:03
 * @version:
 * @description:
 */
public interface SysUserTokenService extends IService<SysUserTokenEntity> {

    /**
     * 生成token
     * @param userId  用户ID
     */
    R createToken(long userId);

    /**
     * 退出，修改token值
     * @param userId  用户ID
     */
    void logout(long userId);

}
