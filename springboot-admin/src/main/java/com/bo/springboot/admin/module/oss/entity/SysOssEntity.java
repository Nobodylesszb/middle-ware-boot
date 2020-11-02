package com.bo.springboot.admin.module.oss.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @auther: bo
 * @Date: 2020/11/2 15:33
 * @version:
 * @description: 文件上传配置实体类
 */
@Data
@TableName("sys_oss")
public class SysOssEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId
    private Long id;
    //URL地址
    private String url;
    //创建时间
    private Date createDate;
}
