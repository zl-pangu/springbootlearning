package com.sf.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sf.domain.TlSysTimerDO;
import com.sf.service.TlSysTimerService;
import com.sf.common.utils.PageUtils;
import com.sf.common.utils.Query;
import com.sf.common.utils.R;

/**
 * 定时任务执行历史表
 * 
 * @author wangxiaoshuai
 * @email happyggq@163.com
 * @date 2018-06-26 11:25:33
 */
 
@Controller
@RequestMapping("/sf/tlSysTimer")
public class TlSysTimerController {
	@Autowired
	private TlSysTimerService sysTimerService;
	
	@GetMapping()
	String SysTimer(){
	    return "sf/sysTimer/sysTimer";
	}

	@ResponseBody
	@GetMapping("/list")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<TlSysTimerDO> sysTimerList = sysTimerService.list(query);
		int total = sysTimerService.count(query);
		PageUtils pageUtils = new PageUtils(sysTimerList, total);
		return pageUtils;
	}

	@GetMapping("/add")
	String add(){
	    return "sf/sysTimer/add";
	}

	@GetMapping("/edit/{jobId}")
	String edit(@PathVariable("jobId") String jobId,Model model){
		TlSysTimerDO sysTimer = sysTimerService.get(jobId);
		model.addAttribute("sysTimer", sysTimer);
	    return "sf/sysTimer/edit";
	}

	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	public R save( TlSysTimerDO sysTimer){
		if(sysTimerService.save(sysTimer)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	public R update( TlSysTimerDO sysTimer){
		sysTimerService.update(sysTimer);
		return R.ok();
	}

	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	public R remove( String jobId){
		if(sysTimerService.remove(jobId)>0){
		return R.ok();
		}
		return R.error();
	}

	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	public R remove(@RequestParam("ids[]") String[] jobIds){
		sysTimerService.batchRemove(jobIds);
		return R.ok();
	}
	
}
