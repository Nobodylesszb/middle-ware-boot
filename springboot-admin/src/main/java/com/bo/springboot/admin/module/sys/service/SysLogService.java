package com.bo.springboot.admin.module.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bo.springboot.admin.common.utils.PageUtils;
import com.bo.springboot.admin.module.sys.entity.SysLogEntity;

import java.util.Map;

/**
 * @auther: bo
 * @Date: 2020/11/4 10:39
 * @version:
 * @description:
 */
public interface SysLogService extends IService<SysLogEntity> {
    PageUtils queryPage(Map<String, Object> params);
}
