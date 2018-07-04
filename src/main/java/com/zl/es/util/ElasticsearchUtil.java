package com.zl.es.util;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * @Author: zhouliang
 * @Date: 2018/7/4 15:44
 */
@Slf4j
public class ElasticsearchUtil {
    @Autowired
    private TransportClient transportClient;

    private static TransportClient client;

    /**
     * 被@PostConstruct修饰的方法会在服务器加载Servlet的时候运行，并且只会被服务器调用一次，类似于Servlet的inti()方法。
     * 被@PostConstruct修饰的方法会在构造函数之后，init()方法之前运行。 @PreDestroy
     */
    @PostConstruct
    public void init() {
        client = this.transportClient;
    }

    /**
     * 创建索引
     * @param index
     * @return
     */
    public static boolean createIndex(String index){
        CreateIndexResponse indexResponse = client.admin().indices().prepareCreate(index).execute().actionGet();
        log.info("执行成功？{}",indexResponse.isAcknowledged());
        return indexResponse.isShardsAcknowledged();
    }

    /**
     * 删除索引
     * @param index
     * @return
     */
    public static boolean deleteIndex(String index){
        DeleteIndexResponse dResponse = client.admin().indices().prepareDelete(index).execute().actionGet();
        if (dResponse.isAcknowledged()){
            log.info("delete index :"+index+",successfully!");
        }else{
            log.info("Fail to delete index:"+index);
        }
        return  dResponse.isAcknowledged();
    }

    /**
     * 判断索引是否存在
     * @param index
     * @return
     */
    public static boolean isIndexExist(String index){
        IndicesExistsResponse inExistsResponse = client.admin().indices().exists(new IndicesExistsRequest(index)).actionGet();
        if (inExistsResponse.isExists()){
            log.info("Index ["+index+"] is exist!");
        }else{
            log.info("Index ["+index+"] is not exist");
        }
        return inExistsResponse.isExists();
    }

    public static String addData(String index, String type, String id){
        return null;
    }

}
