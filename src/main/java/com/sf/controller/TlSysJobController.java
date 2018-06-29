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

import com.sf.domain.TlSysJobDO;
import com.sf.service.TlSysJobService;
import com.sf.common.utils.PageUtils;
import com.sf.common.utils.Query;
import com.sf.common.utils.R;

/**
 * 一次性任务执行日志表
 * 
 * @author wangxiaoshuai
 * @email happyggq@163.com
 * @date 2018-06-26 11:25:33
 */
 
@Controller
@RequestMapping("/sf/tlSysJob")
public class TlSysJobController {
	@Autowired
	private TlSysJobService sysJobService;
	
	@GetMapping()
	String SysJob(){
	    return "sf/sysJob/sysJob";
	}

	@ResponseBody
	@GetMapping("/list")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<TlSysJobDO> sysJobList = sysJobService.list(query);
		int total = sysJobService.count(query);
		PageUtils pageUtils = new PageUtils(sysJobList, total);
		return pageUtils;
	}

	@GetMapping("/add")
	String add(){
	    return "sf/sysJob/add";
	}

	@GetMapping("/edit/{jobId}")
	String edit(@PathVariable("jobId") String jobId,Model model){
		TlSysJobDO sysJob = sysJobService.get(jobId);
		model.addAttribute("sysJob", sysJob);
	    return "sf/sysJob/edit";
	}

	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	public R save( TlSysJobDO sysJob){
		if(sysJobService.save(sysJob)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	public R update( TlSysJobDO sysJob){
		sysJobService.update(sysJob);
		return R.ok();
	}

	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	public R remove( String jobId){
		if(sysJobService.remove(jobId)>0){
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
		sysJobService.batchRemove(jobIds);
		return R.ok();
	}
	
}
