package com.sf.service;

import com.sf.domain.TlSysJobDO;

import java.util.List;
import java.util.Map;

/**
 * 一次性任务执行日志表
 * 
 * @author wangxiaoshuai
 * @email happyggq@163.com
 * @date 2018-06-26 11:25:33
 */
public interface TlSysJobService {
	
	TlSysJobDO get(String jobId);

	List<TlSysJobDO> list(Map<String, Object> map);

	int count(Map<String, Object> map);

	int save(TlSysJobDO sysJob);

	int update(TlSysJobDO sysJob);

	int remove(String jobId);

	int batchRemove(String[] jobIds);
}
