package com.zl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.zl.common.utils.JsonMapper;
import com.zl.thread.dto.CustTableMatchDto;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController()
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestDemo {

    private String json;

    @Before
    public void setUp() throws Exception {
        CustTableMatchDto dto1 = CustTableMatchDto.builder()
                .flag("123456")
                .userNo("01376493")
                .build();
        CustTableMatchDto dto2 = CustTableMatchDto.builder()
                .flag("654321")
                .userNo("01346161")
                .build();
        List<CustTableMatchDto> list=new ArrayList<>();
        list.add(dto1);
        list.add(dto2);
        json = JsonMapper.objectToJson(list);
    }

    @After
    public void tearDown() throws Exception {
    }

    /**
     * 将json转换成list对象（TypeReference）
     */
    @Test
    public void testJsonToListTypeReference() {
        List<CustTableMatchDto> list = JsonMapper.jsonToObject(json, new TypeReference<List<CustTableMatchDto>>() {
        });

        System.out.println(list.size());

    }

    @Test
    public void testJsonToListClass() {
        List<CustTableMatchDto> list= JsonMapper.jsonToObject(json, List.class, CustTableMatchDto.class);
        System.out.println(list.size());
    }
}