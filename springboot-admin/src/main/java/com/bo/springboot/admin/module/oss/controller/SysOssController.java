package com.bo.springboot.admin.module.oss.controller;

import com.bo.springboot.admin.common.utils.ConfigConstant;
import com.bo.springboot.admin.module.oss.service.SysOssService;
import com.bo.springboot.admin.module.sys.service.SysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auther: bo
 * @Date: 2020/11/2 16:03
 * @version:
 * @description:
 */
@RequestMapping("/sys/oss")
@RestController
public class SysOssController {
    @Autowired
    private SysOssService sysOssService;
    @Autowired
    private SysConfigService sysConfigService;

    private final static String KEY = ConfigConstant.CLOUD_STORAGE_CONFIG_KEY;
}
