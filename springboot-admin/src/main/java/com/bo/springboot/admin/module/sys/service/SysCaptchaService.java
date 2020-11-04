package com.bo.springboot.admin.module.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bo.springboot.admin.module.sys.entity.SysCaptchaEntity;

import java.awt.image.BufferedImage;

/**
 * @auther: bo
 * @Date: 2020/11/4 18:50
 * @version:
 * @description:
 */
public interface SysCaptchaService extends IService<SysCaptchaEntity> {
    /**
     * 获取图片验证码
     */
    BufferedImage getCaptcha(String uuid);

    /**
     * 验证码效验
     * @param uuid  uuid
     * @param code  验证码
     * @return  true：成功  false：失败
     */
    boolean validate(String uuid, String code);
}
