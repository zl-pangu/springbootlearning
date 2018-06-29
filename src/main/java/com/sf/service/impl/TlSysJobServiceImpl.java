package com.sf.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.sf.dao.TlSysJobDao;
import com.sf.domain.TlSysJobDO;
import com.sf.service.TlSysJobService;



@Service
public class TlSysJobServiceImpl implements TlSysJobService {
	@Autowired
	private TlSysJobDao sysJobDao;
	
	@Override
	public TlSysJobDO get(String jobId){
		return sysJobDao.get(jobId);
	}

	@Override
	public List<TlSysJobDO> list(Map<String, Object> map){
		return sysJobDao.list(map);
	}

	@Override
	public int count(Map<String, Object> map){
		return sysJobDao.count(map);
	}

	@Override
	public int save(TlSysJobDO sysJob){
		return sysJobDao.save(sysJob);
	}

	@Override
	public int update(TlSysJobDO sysJob){
		return sysJobDao.update(sysJob);
	}

	@Override
	public int remove(String jobId){
		return sysJobDao.remove(jobId);
	}

	@Override
	public int batchRemove(String[] jobIds){
		return sysJobDao.batchRemove(jobIds);
	}
	
}
