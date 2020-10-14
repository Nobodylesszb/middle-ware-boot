package com.bo.security.service;

import com.bo.security.entity.SysUser;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @auther: bo
 * @Date: 2020/10/13 18:24
 * @version:
 * @description:
 */
@Ignore
@SpringBootTest
public class SysUserServiceTest {
    @Autowired
    SysUserService sysUserService;

    @Test
    public void testFindUserByName() {
        SysUser admin = sysUserService.selectByName("admin");
        System.out.println(admin);
    }
}
