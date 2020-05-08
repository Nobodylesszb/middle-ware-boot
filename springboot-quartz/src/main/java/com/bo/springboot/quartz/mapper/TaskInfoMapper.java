package com.bo.springboot.quartz.mapper;


import com.bo.springboot.quartz.model.TaskInfo;
/**
 * @author snluomeng
 * @date 2019/12/20 14:28
 */
public interface TaskInfoMapper {
    /**
     * @date 2019-12-20
     */
    int deleteByPrimaryKey(String taskId);

    /**
     * @date 2019-12-20
     */
    int insert(TaskInfo record);

    /**
     * @date 2019-12-20
     */
    int insertSelective(TaskInfo record);

    /**
     * @date 2019-12-20
     */
    TaskInfo selectByPrimaryKey(String taskId);

    /**
     * @date 2019-12-20
     */
    int updateByPrimaryKeySelective(TaskInfo record);

    /**
     * @date 2019-12-20
     */
    int updateByPrimaryKey(TaskInfo record);
}