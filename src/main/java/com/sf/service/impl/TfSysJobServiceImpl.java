package com.sf.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.sf.dao.TfSysJobDao;
import com.sf.domain.TfSysJobDO;
import com.sf.service.TfSysJobService;



@Service
public class TfSysJobServiceImpl implements TfSysJobService {
	@Autowired
	private TfSysJobDao sysJobDao;
	
	@Override
	public TfSysJobDO get(String jobId){
		return sysJobDao.get(jobId);
	}

	@Override
	public List<TfSysJobDO> list(Map<String, Object> map){
		return sysJobDao.list(map);
	}

	@Override
	public int count(Map<String, Object> map){
		return sysJobDao.count(map);
	}

	@Override
	public int save(TfSysJobDO sysJob){
		return sysJobDao.save(sysJob);
	}

	@Override
	public int update(TfSysJobDO sysJob){
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
