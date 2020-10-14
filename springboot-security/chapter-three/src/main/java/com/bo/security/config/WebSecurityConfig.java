package com.bo.security.config;

import com.bo.security.security.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.firewall.StrictHttpFirewall;

/**
 * @auther: bo
 * @Date: 2020/10/13 16:28
 * @version:
 * @description:
 */
@Configuration
@SuppressWarnings("all")
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    @Autowired
    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
    @Autowired
    private CustomLogoutSuccessHandler logoutSuccessHandler;

    /**
     * 使用内置的加密方式，注意，储存在数据库的密码必须和此加密方式一致
     *
     * @param auth
     * @throws Exception
     */
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//    }
//
//    //密码编码器
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests() //授权配置
                // 如果有允许匿名的url，填在下面
                .antMatchers("/sms/**").permitAll()
                .anyRequest().authenticated()
                .and()
                // 设置登陆页
                .formLogin() //表单登录
                .loginPage("/login")
                .successHandler(customAuthenticationSuccessHandler) // 处理登录成功
                .failureHandler(customAuthenticationFailureHandler) // 处理登录失败
                // 设置登陆成功页
                .defaultSuccessUrl("/").permitAll()
                .and()
                .logout().permitAll()
                .and().apply(smsCodeAuthenticationSecurityConfig);

        // 关闭CSRF跨域
        http.csrf().disable();
    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        // 设置拦截忽略文件夹，可以对静态资源放行
//        StrictHttpFirewall firewall = new StrictHttpFirewall();
//        //去掉";"黑名单
//        firewall.setAllowSemicolon(true);
//        firewall.setAllowUrlEncodedSlash(true);
        //加入自定义的防火墙
//        web.httpFirewall(firewall);
        web.ignoring().antMatchers("/css/**", "/js/**");
//        super.configure(web);
    }
}
