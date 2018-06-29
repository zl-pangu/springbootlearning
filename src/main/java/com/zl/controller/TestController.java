package com.zl.controller;

import com.zl.dto.CustTableMatchDto;
import com.zl.service.CustTableMatchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("zl")
@Slf4j
public class TestController {

    @Autowired
    private CustTableMatchService custTableMatchService;

    @RequestMapping("/test")
    public String test(){
       log.info("进入一个测试页面。。。。");
        return "test";
    }

    @RequestMapping("test1")
    public void test1() throws Exception{
        CustTableMatchDto dto=new CustTableMatchDto();
        dto.setFlag("123456");
        dto.setUserNo("01376493");
        custTableMatchService.parseExcles(dto);
    }

}
