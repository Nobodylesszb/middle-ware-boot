package com.bo.springboot.quartz.model;
/**
 * @author snluomeng
 * @date 2019/12/20 14:28
 */
public class TaskInfo {
    /**
     * 任务ID
     */
    private String taskId;

    /**
     *任务名称
     */
    private String taskName;

    /**
     *触发器名称
     */
    private String triggerName;

    /**
     *任务组
     */
    private String taskGroupname;

    /**
     *触发器组
     */
    private String triggerGroupname;

    /**
     *  任务状态 1 启用 2 禁用
     */
    private Integer taskStatus;

    /**
     * taskCron 表达式
     */
    private String taskCron;

    /**
     * 上次执行时间
     */
    private String lastExecutetime;

    /**
     *  参数
     */
    private String parames;

    /**
     * 下次执行时间
     */
    private String nextExecutetime;

    /**
     *  创建时间
     */
    private String createTime;

    /**
     * 修改时间
     */
    private String updateTime;

    /**
     * 
     * @return task_id 
     */
    public String getTaskId() {
        return taskId;
    }

    /**
     * 
     * @param taskId 
     */
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    /**
     * 
     * @return task_name 
     */
    public String getTaskName() {
        return taskName;
    }

    /**
     * 
     * @param taskName 
     */
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    /**
     * 
     * @return trigger_name 
     */
    public String getTriggerName() {
        return triggerName;
    }

    /**
     * 
     * @param triggerName 
     */
    public void setTriggerName(String triggerName) {
        this.triggerName = triggerName;
    }

    /**
     * 
     * @return task_groupname 
     */
    public String getTaskGroupname() {
        return taskGroupname;
    }

    /**
     * 
     * @param taskGroupname 
     */
    public void setTaskGroupname(String taskGroupname) {
        this.taskGroupname = taskGroupname;
    }

    /**
     * 
     * @return trigger_groupname 
     */
    public String getTriggerGroupname() {
        return triggerGroupname;
    }

    /**
     * 
     * @param triggerGroupname 
     */
    public void setTriggerGroupname(String triggerGroupname) {
        this.triggerGroupname = triggerGroupname;
    }

    /**
     * 
     * @return task_status 
     */
    public Integer getTaskStatus() {
        return taskStatus;
    }

    /**
     * 
     * @param taskStatus 
     */
    public void setTaskStatus(Integer taskStatus) {
        this.taskStatus = taskStatus;
    }

    /**
     * 
     * @return task_cron 
     */
    public String getTaskCron() {
        return taskCron;
    }

    /**
     * 
     * @param taskCron 
     */
    public void setTaskCron(String taskCron) {
        this.taskCron = taskCron;
    }

    /**
     * 
     * @return last_executetime 
     */
    public String getLastExecutetime() {
        return lastExecutetime;
    }

    /**
     * 
     * @param lastExecutetime 
     */
    public void setLastExecutetime(String lastExecutetime) {
        this.lastExecutetime = lastExecutetime;
    }

    /**
     * 
     * @return parames 
     */
    public String getParames() {
        return parames;
    }

    /**
     * 
     * @param parames 
     */
    public void setParames(String parames) {
        this.parames = parames;
    }

    /**
     * 
     * @return next_executetime 
     */
    public String getNextExecutetime() {
        return nextExecutetime;
    }

    /**
     * 
     * @param nextExecutetime 
     */
    public void setNextExecutetime(String nextExecutetime) {
        this.nextExecutetime = nextExecutetime;
    }

    /**
     * 
     * @return create_time 
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     * 
     * @param createTime 
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    /**
     * 
     * @return update_time 
     */
    public String getUpdateTime() {
        return updateTime;
    }

    /**
     * 
     * @param updateTime 
     */
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}