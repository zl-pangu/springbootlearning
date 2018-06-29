package com.sf.quickstart.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sf.dto.CustTableMatchDto;
import com.sf.scheduler.core.timer.IJob;
import com.sf.scheduler.core.timer.Task;
import com.sf.service.CustTableMatchService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * @Author: zhouliang
 * @Date: 2018/6/26 18:25
 */
@Slf4j
@DisallowConcurrentExecution //该注解表示禁止并行同一个任务
public class CustTableMatchTask extends Task {

    @Autowired
    private CustTableMatchService custTableMatchService;

    @Override
    public Map<String, String> execute(IJob iJob, Map<String, Object> params) throws Exception {
        ObjectMapper mapper=new ObjectMapper();
        String str = mapper.writeValueAsString(params);
        CustTableMatchDto param = mapper.readValue(str.getBytes(), CustTableMatchDto.class);
        try{
            custTableMatchService.parseExcles(param);

            return makeResult("0","任务执行成功");
        }catch (Exception e){
            String msg="任务失败:"+e.getMessage();

            log.error("任务失败：{},传入参数：{}",msg,params);
            e.printStackTrace();
            return makeResult("-1",msg);
        }
    }
}
