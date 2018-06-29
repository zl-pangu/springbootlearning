package com.thread.dao;

import com.thread.domain.CustBchmakDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author: zhouliang
 * @Date: 2018/6/21 9:58
 */
@Mapper
public interface CustBchmakDao {

    CustBchmakDO get(Long id);

    CustBchmakDO queryByFlag(@Param("flag") String flag);

    List<CustBchmakDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(CustBchmakDO custBchmak);

    int update(CustBchmakDO custBchmak);

    int remove(Long ID);

    int batchRemove(Long[] ids);
}
