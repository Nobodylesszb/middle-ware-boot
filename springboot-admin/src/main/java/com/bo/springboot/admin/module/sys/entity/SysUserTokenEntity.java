package com.bo.springboot.admin.module.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @auther: bo
 * @Date: 2020/11/3 14:45
 * @version:
 * @description:
 */
@Data
@TableName("sys_user_token")
@ApiModel
public class SysUserTokenEntity {
    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    @TableId(type = IdType.INPUT)
    private Long userId;
    @ApiModelProperty(value = "用户token")
    private String token;
    @ApiModelProperty(value = "过期时间")
    private Date expireTime;
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
}
