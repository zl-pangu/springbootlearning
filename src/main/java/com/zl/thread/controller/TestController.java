package com.zl.thread.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zl.common.redis.RedisService;
import com.zl.common.utils.R;
import com.zl.thread.dao.CustUploadingFilesDao;
import com.zl.thread.domain.CustUploadingFilesDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;


@Controller
@RequestMapping("zl")
@Slf4j
public class TestController {

    @Autowired
    private CustUploadingFilesDao dao;

    @Resource
    RedisService redisService;

    @RequestMapping("/do")
    public String test(){
        log.info("进入一个测试页面。。。。");
        return "test";
    }

    @RequestMapping("/queryFile")
    @ResponseBody
    public String test1(){
        log.info("queryFile。。。。。");
        try {
            ObjectMapper mapper=new ObjectMapper();
            CustUploadingFilesDO files = dao.get(22405);
            String str = mapper.writeValueAsString(files);
            return str;
        } catch (Exception e) {
            log.error("查询失败：{}",e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("/redisTest")
    @ResponseBody
    public R redisTest(){
        log.info("redis test....");
        long l = System.currentTimeMillis();
        String msg = null;
        try {
            ObjectMapper mapper=new ObjectMapper();
            CustUploadingFilesDO files = dao.get(22405);
            msg = redisService.setObject(String.valueOf(l), files, 300);
            CustUploadingFilesDO obj = (CustUploadingFilesDO)redisService.getObject(String.valueOf(l));
            log.info("save objValue:{}",mapper.writeValueAsString(obj));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return R.ok(msg);
    }

    @RequestMapping("/bspRoutePush")
    @ResponseBody
    public String bspRoutePush(String content) throws UnsupportedEncodingException {
        String xml=URLDecoder.decode(content,"UTF-8");
        log.info("传入xml:{},content:{}",xml,content);
        return "<Response service='RoutePushService'>\n" +
                "\n" +
                "<Head>OK</Head>\n" +
                "\n" +
                "</Response>";
    }

    @RequestMapping("/pushOrderState")
    @ResponseBody
    public String pushOrderState(String content) throws UnsupportedEncodingException {
        String xml=URLDecoder.decode(content,"UTF-8");
        log.info("订单状态回调========》传入xml:{},content:{}",xml,content);
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
                "<Response>\n" +
                "<success>true</success>\n" +
                "</Response>";
    }
}
