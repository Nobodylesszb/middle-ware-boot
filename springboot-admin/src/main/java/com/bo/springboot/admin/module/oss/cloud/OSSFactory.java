package com.bo.springboot.admin.module.oss.cloud;

import com.bo.springboot.admin.common.utils.ConfigConstant;
import com.bo.springboot.admin.common.utils.Constant;
import com.bo.springboot.admin.common.utils.SpringContextUtils;
import com.bo.springboot.admin.module.sys.service.SysConfigService;

/**
 * @auther: bo
 * @Date: 2020/11/2 15:12
 * @version:
 * @description: 根数数据库配置选择不同的上传平台
 */
public class OSSFactory {

    private static SysConfigService sysConfigService;

    static {
        OSSFactory.sysConfigService = (SysConfigService) SpringContextUtils.getBean("sysConfigService");
    }

    public static CloudStorageService build(){
        //获取云存储配置信息
        CloudStorageConfig config = sysConfigService.getConfigObject(ConfigConstant.CLOUD_STORAGE_CONFIG_KEY, CloudStorageConfig.class);

        if(config.getType() == Constant.CloudService.QINIU.getValue()){
            return new QiniuCloudStorageService(config);
        }else if(config.getType() == Constant.CloudService.ALIYUN.getValue()){
            return new AliyunCloudStorageService(config);
        }else if(config.getType() == Constant.CloudService.QCLOUD.getValue()){
            return new QcloudCloudStorageService(config);
        }
        return null;
    }
}
