package com.bo.springboot.admin.module.sys.form;

import lombok.Data;

/**
 * @auther: bo
 * @Date: 2020/11/5 10:36
 * @version:
 * @description:
 */
@Data
public class SysLoginForm {

    private String username;
    private String password;
    private String captcha;
    private String uuid;
}
