package com.sf.service;

import com.sf.domain.TfSysTimerDO;

import java.util.List;
import java.util.Map;

/**
 * 定时任务表
 * 
 * @author wangxiaoshuai
 * @email happyggq@163.com
 * @date 2018-06-26 11:11:37
 */
public interface TfSysTimerService {
	
	TfSysTimerDO get(String jobId);

	List<TfSysTimerDO> list(Map<String, Object> map);

	int count(Map<String, Object> map);

	int save(TfSysTimerDO sysTimer);

	int update(TfSysTimerDO sysTimer);

	int remove(String jobId);

	int batchRemove(String[] jobIds);
}
