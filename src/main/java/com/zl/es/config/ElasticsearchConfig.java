package com.zl.es.config;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;

/**
 * @Author: zhouliang
 * @Date: 2018/7/4 15:03
 */
@Configuration
@Slf4j
public class ElasticsearchConfig {
    @Value("${elasticsearch.host}")
    private String host;

    @Value("${elasticsearch.port}")
    private Integer port;

    @Value("${elasticsearch.cluster-name}")
    private String clusterName;

    @Value("${elasticsearch.pool}")
    private String poolSize;

    @Bean(name = "transportClient")
    public TransportClient transportClient(){
        log.info("es 初始化开始....");
        TransportClient transportClient = null;
        try{
            //配置信息
            Settings build = Settings.builder().put("cluster.name", clusterName)
                    .put("client.transport.sniff", true)
                    .put("thread_pool.search.size", Integer.valueOf(poolSize))
                    .build();
            transportClient=new PreBuiltTransportClient(build);
            TransportAddress transportAddress = new TransportAddress(InetAddress.getByName(host), port);
            transportClient.addTransportAddress(transportAddress);
        }catch (Exception e){
            log.error("es 初始化失败....",e.getMessage());
            e.printStackTrace();
        }
        return  transportClient;
    }
}
