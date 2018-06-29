package com.sf.quickstart.task;

import com.sf.scheduler.core.timer.IJob;
import com.sf.scheduler.core.timer.Task;
import com.sf.service.TfSysTimerService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;


/**
 * 一个任务demo
 * @author lijie.zh
 */
@Slf4j
@DisallowConcurrentExecution //该注解表示禁止并行同一个任务
public class DemoTask extends Task {

    @Autowired
    private TfSysTimerService tfSysTimerService;

    @Override
    public Map<String, String> execute(IJob currentJob, Map<String, Object> params) throws Exception {
        log.debug("这是一个测试任务！job：{} params：{} service: {}", currentJob.getJobId(), params,tfSysTimerService);
        return makeResult("0", "任务执行成功");
    }

}
