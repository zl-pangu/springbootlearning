package com.sf.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sf.dto.CustTableMatchDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.sf.domain.TfSysJobDO;
import com.sf.service.TfSysJobService;
import com.sf.common.utils.PageUtils;
import com.sf.common.utils.Query;
import com.sf.common.utils.R;

/**
 * 一次性任务表：执行完立即删除
 * 
 * @author wangxiaoshuai
 * @email happyggq@163.com
 * @date 2018-06-26 11:11:37
 */
 
@Controller
@RequestMapping("/sf/tfSysJob")
public class TfSysJobController {
	@Autowired
	private TfSysJobService sysJobService;
	
	@GetMapping()
	String SysJob(){
	    return "sf/sysJob/sysJob";
	}
	
	@ResponseBody
	@GetMapping("/list")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<TfSysJobDO> sysJobList = sysJobService.list(query);
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
		TfSysJobDO sysJob = sysJobService.get(jobId);
		model.addAttribute("sysJob", sysJob);
	    return "sf/sysJob/edit";
	}

	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	public R save( TfSysJobDO sysJob){
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
	public R update( TfSysJobDO sysJob){
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

	@PostMapping("/generateMatchTable")
	@ResponseBody
	public R generateMatchTable(@RequestBody CustTableMatchDto matchDto)  {
		ObjectMapper mapper=new ObjectMapper();
		try {
			long l = System.currentTimeMillis();
			TfSysJobDO tfSysJobDO=new TfSysJobDO();
			tfSysJobDO.setJobId(matchDto.getFlag());
			tfSysJobDO.setJobName("匹配表格的一次性任务");
			tfSysJobDO.setJobType(1);
			tfSysJobDO.setJobClass("com.sf.quickstart.task.CustTableMatchTask");
			tfSysJobDO.setJobParams(mapper.writeValueAsString(matchDto));
			tfSysJobDO.setAlarmType(1);
			tfSysJobDO.setAlarmReceiver("liangzhou@sf-express.com");
			tfSysJobDO.setUseTag(1);
			tfSysJobDO.setExecTime(l);
			tfSysJobDO.setCreateTime(new Date());
			tfSysJobDO.setCreateBy(matchDto.getUserNo());
			tfSysJobDO.setServerNode("macth-task");
			sysJobService.save(tfSysJobDO);
			return  R.ok();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return  R.error(500,e.getMessage());
		}
	}

}
