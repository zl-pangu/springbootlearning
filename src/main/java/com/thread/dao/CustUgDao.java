package com.thread.dao;

import com.thread.domain.CustMatchTable;
import com.thread.domain.CustUgDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2018-06-20 11:55:28
 */
@Mapper
public interface CustUgDao {

	CustUgDO get(Integer id);

	List<CustUgDO> list(Map<String, Object> map);

	int count(Map<String, Object> map);
	
	int save(CustUgDO custUg);
	
	int update(CustUgDO custUg);
	
	int remove(Integer ID);
	
	int batchRemove(Integer[] ids);

	List<CustMatchTable> queryMatchFile(@Param("flag") String flag);

}
