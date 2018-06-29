package com.sf.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.sf.dao.TlSysTimerDao;
import com.sf.domain.TlSysTimerDO;
import com.sf.service.TlSysTimerService;



@Service
public class TlSysTimerServiceImpl implements TlSysTimerService {
	@Autowired
	private TlSysTimerDao sysTimerDao;
	
	@Override
	public TlSysTimerDO get(String jobId){
		return sysTimerDao.get(jobId);
	}

	@Override
	public List<TlSysTimerDO> list(Map<String, Object> map){
		return sysTimerDao.list(map);
	}

	@Override
	public int count(Map<String, Object> map){
		return sysTimerDao.count(map);
	}

	@Override
	public int save(TlSysTimerDO sysTimer){
		return sysTimerDao.save(sysTimer);
	}

	@Override
	public int update(TlSysTimerDO sysTimer){
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
