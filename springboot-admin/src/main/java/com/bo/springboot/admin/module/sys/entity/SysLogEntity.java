package com.bo.springboot.admin.module.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @auther: bo
 * @Date: 2020/11/4 10:35
 * @version:
 * @description:
 */
@Data
@TableName("sys_log")
public class SysLogEntity implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;
    //用户名
    private String username;
    //用户操作
    private String operation;
    //请求方法
    private String method;
    //请求参数
    private String params;
    //执行时长(毫秒)
    private Long time;
    //IP地址
    private String ip;
    //创建时间
    private Date createDate;
}
