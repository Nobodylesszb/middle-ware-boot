package com.bo.springboot.admin.module.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bo.springboot.admin.module.sys.entity.SysLogEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysLogDao extends BaseMapper<SysLogEntity> {
	
}