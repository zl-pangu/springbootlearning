package com.sf.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.sf.dao.TfSysTimerDao;
import com.sf.domain.TfSysTimerDO;
import com.sf.service.TfSysTimerService;



@Service
public class TfSysTimerServiceImpl implements TfSysTimerService {
	@Autowired
	private TfSysTimerDao sysTimerDao;
	
	@Override
	public TfSysTimerDO get(String jobId){
		return sysTimerDao.get(jobId);
	}

	@Override
	public List<TfSysTimerDO> list(Map<String, Object> map){
		return sysTimerDao.list(map);
	}

	@Override
	public int count(Map<String, Object> map){
		return sysTimerDao.count(map);
	}

	@Override
	public int save(TfSysTimerDO sysTimer){
		return sysTimerDao.save(sysTimer);
	}

	@Override
	public int update(TfSysTimerDO sysTimer){
		return sysTimerDao.update(sysTimer);
	}

	@Override
	public int remove(String jobId){
		return sysTimerDao.remove(jobId);
	}

	@Override
	public int batchRemove(String[] jobIds){
		return sysTimerDao.batchRemove(jobIds);
	}
	
}
