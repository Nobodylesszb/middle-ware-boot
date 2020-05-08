package com.bo.springboot.quartz.model;

import lombok.Data;

import java.util.Map;

/**
 * @author snluomeng
 * @date 2019/12/19 18:25
 */
@Data
public class TaskDTO {
    /**
     * 任务名称
     */
    private String taskName;
    /**
     * 触发器名称
     */
    private String triggerName;
    /**
     * 任务组
     */
    private String taskGroupName;
    /**
     * 触发器组
     */
    private String triggerGroupName;
    /**
     * 1 启用 2 禁用
     */
    private String taskStatus;
    /**
     * taskCron 表达式
     */
    private String taskCron;
    /**
     * 上次执行时间
     */
    private String lastExecuteTime;

    private String nextExecuteTime;
    /**
     * 参数
     */
    private Map<String, Object> parames;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 修改时间
     */
    private String updateTime;
}
