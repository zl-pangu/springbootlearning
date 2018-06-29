package com.sf.controller;

import com.sf.common.utils.R;
import com.sf.domain.TfSysTimerDO;
import com.sf.dto.CustTableMatchDto;
import com.sf.service.CustTableMatchService;
import com.sf.service.TfSysTimerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.Date;

/**
 * @author Wangxiaoshuai
 * @Date 2018/6/26 13:42
 */
@Controller
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TfSysTimerService tfSysTimerService;

    @Autowired
    private CustTableMatchService custTableMatchService;

    @RequestMapping("/do")
    @ResponseBody
    public R test(){
        Date sysDate = new Date();
        TfSysTimerDO tfSysTimerDO = new TfSysTimerDO();
        tfSysTimerDO.setJobId("10001");
        tfSysTimerDO.setJobName("一个周期性执行的任务");
        tfSysTimerDO.setJobType(1);
        tfSysTimerDO.setJobClass("com.sf.quickstart.task.DemoTask");
        tfSysTimerDO.setJobParams("{\"userName\":\"顺丰科技\",\"age\":\"20\"}");
        tfSysTimerDO.setCronExpression("0 0/1 * * * ?");
        tfSysTimerDO.setExpiredTag(0);
        tfSysTimerDO.setAlarmType(1);
        tfSysTimerDO.setAlarmReceiver("wangxiaoshuai@sf-express.com");
        tfSysTimerDO.setUseTag(1);
        tfSysTimerDO.setStartTime(20170809064138L);
        tfSysTimerDO.setEndTime(20501231235959L);
        tfSysTimerDO.setCreateTime(sysDate);
        tfSysTimerDO.setUpdateTime(sysDate);
        tfSysTimerDO.setServerNode("macth-task");

        tfSysTimerService.save(tfSysTimerDO);
        return R.ok("success");
    }

    @RequestMapping("test1")
    void test1() throws Exception{
        CustTableMatchDto dto=new CustTableMatchDto();
        dto.setFlag("123456");
        dto.setUserNo("01376493");
        custTableMatchService.parseExcles(dto);
    }

}
