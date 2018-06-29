package com.sf.dao;

import com.sf.domain.TfSysTimerDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 定时任务表
 * @author wangxiaoshuai
 * @email happyggq@163.com
 * @date 2018-06-26 11:11:37
 */
@Mapper
public interface TfSysTimerDao {

	TfSysTimerDO get(String jobId);
	
	List<TfSysTimerDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(TfSysTimerDO sysTimer);
	
	int update(TfSysTimerDO sysTimer);
	
	int remove(String JOB_ID);
	
	int batchRemove(String[] jobIds);
}
