package com.thread.controller;

import com.thread.dto.CustTableMatchDto;
import com.thread.service.CustTableMatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("zl")
public class TestController {

    @Autowired
    private CustTableMatchService custTableMatchService;

    @RequestMapping("/test")
    public String test(){
        System.out.println(1);
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
