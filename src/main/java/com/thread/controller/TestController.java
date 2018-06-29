package com.thread.controller;

import com.thread.dto.CustTableMatchDto;
import com.thread.service.CustTableMatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Wangxiaoshuai
 * @Date 2018/6/26 13:42
 */
@Controller
@RequestMapping("/test")
public class TestController {


    @Autowired
    private CustTableMatchService custTableMatchService;


    @RequestMapping("test1")
    void test1() throws Exception{
        CustTableMatchDto dto=new CustTableMatchDto();
        dto.setFlag("123456");
        dto.setUserNo("01376493");
        custTableMatchService.parseExcles(dto);
    }

}
