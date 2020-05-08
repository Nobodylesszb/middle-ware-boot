package com.bo.springboot.quartz;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author snluomeng
 * @date 2019/12/19 16:27
 */
@Slf4j
public class MyJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("==================开始执行任务==================");
        log.info("执行任务线程ID{}",Thread.currentThread().getId());
        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        log.info("参数为{}",jobDataMap.get("testKey"));
    }
}
