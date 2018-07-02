package com.zl.thread.dao;

import com.zl.thread.domain.CustGenerateFileDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2018-06-20 11:55:28
 */
@Mapper
public interface CustGenerateFileDao {

	CustGenerateFileDO get(Integer id);
	
	List<CustGenerateFileDO> list(Map<String, Object> map);

	int count(Map<String, Object> map);
	
	int save(CustGenerateFileDO custGenerateFile);
	
	int update(CustGenerateFileDO custGenerateFile);
	
	int remove(Integer ID);
	
	int batchRemove(Integer[] ids);

	int batchInsert(List<CustGenerateFileDO> list);
}
