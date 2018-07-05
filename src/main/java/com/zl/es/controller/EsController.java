package com.zl.es.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zl.common.utils.JsonMapper;
import com.zl.common.utils.R;
import com.zl.es.domail.EsUser;
import com.zl.es.util.ElasticsearchUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.Map;
import java.util.Random;

/**
 * @Author: zhouliang
 * @Date: 2018/7/5 10:51
 */
@Controller
@RequestMapping("/es")
public class EsController {
    private String indexName="test_index";
    private String esType="external";

    @RequestMapping("createIndex")
    @ResponseBody
    public R createIndex(){
        if (!ElasticsearchUtil.isIndexExist(indexName)){
            ElasticsearchUtil.createIndex(indexName);
            return R.ok("索引创建成功");
        }else{
            return R.error("索引已经创建");
        }
    }

    @RequestMapping("/insertJson")
    @ResponseBody
    public R insertJson() {
        EsUser user = EsUser.builder().id("1")
                .age(25)
                .date(new Date())
                .name("zl" + new Random(100).nextInt())
                .build();
        String msg = ElasticsearchUtil.addData(JsonMapper.objectToJson(user), indexName, esType, user.getId());
        return R.ok(msg);
    }

    @GetMapping("/deleteIndex")
    @ResponseBody
    public R deleteIndex(@RequestParam(name = "id")String id){
        ElasticsearchUtil.deleteDataById(indexName,esType,id);
        return R.ok();
    }

    @GetMapping("/update")
    @ResponseBody
    public R update(@RequestParam(name = "id")String id){
        EsUser user = EsUser.builder().name("张三").date(new Date()).age(22).id("123").build();
        //index：索引 type:   id:定位到一个json数据，相当于一个表
        ElasticsearchUtil.updateDataById(JsonMapper.objectToJson(user),indexName,esType,id);
        return R.ok();
    }

    @GetMapping("getData")
    @ResponseBody
    public R getDate(String id){
        Map<String, Object> map = ElasticsearchUtil.searchDataById(indexName, esType, id, null);
        return R.ok(map);
    }
}
