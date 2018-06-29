package com.sf.dao;

import com.sf.domain.TlSysTimerDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 定时任务执行历史表
 * @author wangxiaoshuai
 * @email happyggq@163.com
 * @date 2018-06-26 11:25:33
 */
@Mapper
public interface TlSysTimerDao {

	TlSysTimerDO get(String jobId);

	List<TlSysTimerDO> list(Map<String, Object> map);

	int count(Map<String, Object> map);

	int save(TlSysTimerDO sysTimer);

	int update(TlSysTimerDO sysTimer);

	int remove(String JOB_ID);

	int batchRemove(String[] jobIds);
}
