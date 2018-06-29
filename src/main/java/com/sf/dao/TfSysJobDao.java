package com.sf.dao;

import com.sf.domain.TfSysJobDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 一次性任务表：执行完立即删除
 * @author wangxiaoshuai
 * @email happyggq@163.com
 * @date 2018-06-26 11:11:37
 */
@Mapper
public interface TfSysJobDao {

	TfSysJobDO get(String jobId);

	List<TfSysJobDO> list(Map<String, Object> map);

	int count(Map<String, Object> map);

	int save(TfSysJobDO sysJob);

	int update(TfSysJobDO sysJob);

	int remove(String JOB_ID);

	int batchRemove(String[] jobIds);
}
