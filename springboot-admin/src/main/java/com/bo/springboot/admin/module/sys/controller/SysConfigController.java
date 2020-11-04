package com.bo.springboot.admin.module.sys.controller;

import com.bo.springboot.admin.common.annotation.SysLog;
import com.bo.springboot.admin.common.utils.PageUtils;
import com.bo.springboot.admin.common.utils.R;
import com.bo.springboot.admin.common.validator.ValidatorUtils;
import com.bo.springboot.admin.module.sys.entity.SysConfigEntity;
import com.bo.springboot.admin.module.sys.service.SysConfigService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @auther: bo
 * @Date: 2020/11/2 18:48
 * @version:
 * @description:
 */
@RestController
@RequestMapping("/sys/config")
public class SysConfigController  extends AbstractController{
    @Autowired
    private SysConfigService sysConfigService;

    /**
     * 所有配置列表
     */
    @GetMapping("/list")
    @RequiresPermissions("sys:config:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = sysConfigService.queryPage(params);
        return R.ok().put("page", page);
    }

    /**
     * 配置信息
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("sys:config:info")
    public R info(@PathVariable("id") Long id){
        SysConfigEntity config = sysConfigService.getById(id);

        return R.ok().put("config", config);
    }

    /**
     * 保存配置
     */
    @SysLog("保存配置")
    @PostMapping("/save")
    @RequiresPermissions("sys:config:save")
    public R save(@RequestBody SysConfigEntity config){
        ValidatorUtils.validateEntity(config);

        sysConfigService.saveConfig(config);

        return R.ok();
    }

}
