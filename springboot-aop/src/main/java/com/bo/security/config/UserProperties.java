package com.bo.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("application")
public class UserProperties {

    /**
     * 服务地址
     */
    private String host;
    /**
     * Jenkins连接信息,用于服务以ED-Ward模式启动时配置
     */
    private JenkinsConnectionInfo jenkinsConnectionInfo;
    /**
     * ED-Ward Web 地址
     */
    private String edwardWebHost;
    /**
     * 一键拉起服务地址
     */
    private String pullUpServiceHost;
    /** 自动化测试报告类型 */

    /**
     * 全局线程池配置
     */
    private ThreadPool threadPool = new ThreadPool();

    /**
     * 制品管理的url
     */
    private String artifactManageUrl;
    /**
     * 制品管理的用户名
     */
    private String artifactManageUsername;
    /**
     * 制品管理的密码
     */
    private String artifactManagePassword;
    /**
     * 制品管理的Host
     */
    private String artifactManageNexusHost;

    @Data
    public static class ThreadPool {
        private Integer coreSize = 5;
        private Integer maxSize = 10;
        private Long threadRemainMillisecond = 60 * 1000L;
        private Integer blockingQueueSize = 15;
    }

    @Data
    public static class JenkinsConnectionInfo {
        private String host;
        private String username;
        private String password;
        private String apiToken;
        private String tunnelHost;
    }
}
