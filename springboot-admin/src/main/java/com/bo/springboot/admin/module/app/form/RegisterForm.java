package com.bo.springboot.admin.module.app.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;

/**
 * @auther: bo
 * @Date: 2020/10/30 16:38
 * @version:
 * @description:
 */
@Data
@ApiModel
public class RegisterForm {

    @ApiModelProperty(value = "手机号")
    @NotBlank(message = "手机号不能为空")
    private String mobile;

    @ApiModelProperty(value = "密码")
    @NotBlank(message = "密码不能为空")
    private String password;
}
