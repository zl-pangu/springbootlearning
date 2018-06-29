package com.sf.service;

import com.sf.domain.TlSysTimerDO;

import java.util.List;
import java.util.Map;

/**
 * 定时任务执行历史表
 * 
 * @author wangxiaoshuai
 * @email happyggq@163.com
 * @date 2018-06-26 11:25:33
 */
public interface TlSysTimerService {
	
	TlSysTimerDO get(String jobId);

	List<TlSysTimerDO> list(Map<String, Object> map);

	int count(Map<String, Object> map);

	int save(TlSysTimerDO sysTimer);

	int update(TlSysTimerDO sysTimer);

	int remove(String jobId);

	int batchRemove(String[] jobIds);
}
