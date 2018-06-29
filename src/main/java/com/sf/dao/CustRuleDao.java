package com.sf.dao;

import com.sf.domain.CustRuleDO;
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
public interface CustRuleDao {

	CustRuleDO get(Integer id);
	
	List<CustRuleDO> list(Map<String, Object> map);

	int count(Map<String, Object> map);
	
	int save(CustRuleDO custRule);
	
	int update(CustRuleDO custRule);
	
	int remove(Integer ID);
	
	int batchRemove(Integer[] ids);
}
