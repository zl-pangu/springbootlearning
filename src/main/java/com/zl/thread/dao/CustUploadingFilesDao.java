package com.zl.thread.dao;

import com.zl.thread.domain.CustUploadingFilesDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2018-06-20 11:55:29
 */
@Mapper
public interface CustUploadingFilesDao {

	CustUploadingFilesDO get(Integer id);

	int batchInsert(List<CustUploadingFilesDO> list);

	List<CustUploadingFilesDO> list(Map<String, Object> map);

	int count(Map<String, Object> map);
	
	int save(CustUploadingFilesDO custUploadingFiles);
	
	int update(CustUploadingFilesDO custUploadingFiles);
	
	int remove(Integer ID);
	
	int batchRemove(Integer[] ids);
}
