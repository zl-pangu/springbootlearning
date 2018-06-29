package com.zl.common.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @Author: zhouliang
 * @Date: 2018/6/28 10:55
 */
@Service
public class RedisPoolFactory {

    @Autowired
    RedisConfig redisConfig;

    @Bean JedisPool JedisPoolFactory(){
        JedisPool jedisPool=null;
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        String host = redisConfig.getHost();
        int port = redisConfig.getPort();
        String password = redisConfig.getPassword();
        int timeout = redisConfig.getTimeout();
        try{
            poolConfig.setMaxIdle(redisConfig.getPoolMaxIdle());
            poolConfig.setMaxTotal(redisConfig.getPoolMaxTotal());
            poolConfig.setMaxWaitMillis(redisConfig.getPoolMaxWait() * 1000);
            if (jedisPool==null){
                if (password != null && !"".equals(password)) {
                    jedisPool = new JedisPool(poolConfig, host, port, timeout, password);
                } else if (timeout != 0) {
                    jedisPool = new JedisPool(poolConfig, host, port, timeout);
                } else {
                    jedisPool = new JedisPool(poolConfig, host, port);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return jedisPool;
    }
}
