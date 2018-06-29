package com.zl.common.redis;

import com.zl.common.utils.SerializeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;


/**
 * @Author: zhouliang
 * @Date: 2018/6/28 10:58
 */
@Service
@Slf4j
public class RedisService {

    @Autowired
    JedisPool jedisPool;


    /**
     * EXPIRE 将key的生存时间设置为ttl秒
     * PEXPIRE 将key的生成时间设置为ttl毫秒
     * EXPIREAT 将key的过期时间设置为timestamp所代表的的秒数的时间戳
     * PEXPIREAT 将key的过期时间设置为timestamp所代表的的毫秒数的时间戳
     */

    /**
     * 获取缓存
     * @param key
     * @return
     */
    public String get(String key) {
        String value = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            if (jedis.exists(key)) {
                value = jedis.get(key);
                value = StringUtils.isNotBlank(value) && !"nil".equalsIgnoreCase(value) ? value : null;
                log.debug("get {} = {}", key, value);
            }
        } catch (Exception e) {
            log.warn("get {} = {}", key, value, e);
        } finally {
            returnResource(jedis);
        }
        return value;
    }

    /**
     * 获取缓存
     * @param key
     * @return
     */
    public  Object getObject(String key) {
        Object value = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            if (jedis.exists(getBytesKey(key))) {
                value = toObject(jedis.get(getBytesKey(key)));
                log.debug("getObject {} = {}", key, value);
            }
        } catch (Exception e) {
            log.warn("getObject {} = {}", key, value, e);
        } finally {
            returnResource(jedis);
        }
        return value;
    }

    /**
     * 设置缓存
     * @param key 键
     * @param value 值
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    public String set(String key, String value, int cacheSeconds) {
        String result = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();;
            result = jedis.set(key, value);
            if (cacheSeconds != 0) {
                jedis.expire(key, cacheSeconds);
            }
            log.debug("set {} = {}", key, value);
        } catch (Exception e) {
            log.warn("set {} = {}", key, value, e);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 设置缓存
     * @param key 键
     * @param value 值
     * @param cacheSeconds 过期时间
     * @return
     */
    public  String setObject(String key, Object value, int cacheSeconds) {
        String result = null;
        Jedis jedis = null;
        try {
            jedis =jedisPool.getResource();
            result = jedis.set(getBytesKey(key), toBytes(value));
            if (cacheSeconds != 0) {
                jedis.expire(key, cacheSeconds);
            }
            log.debug("setObject {} = {}", key, value);
        } catch (Exception e) {
            log.warn("setObject {} = {}", key, value, e);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 获取list缓存
     * @param key
     * @return
     */
    public  List<String> getList(String key) {
        List<String> value = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            if (jedis.exists(key)) {
                value = jedis.lrange(key, 0, -1);
                log.debug("getList {} = {}", key, value);
            }
        } catch (Exception e) {
            log.warn("getList {} = {}", key, value, e);
        } finally {
            returnResource(jedis);
        }
        return value;
    }

    /**
     * 获取List缓存
     * @param key 键
     * @return 值
     */
    public  List<Object> getObjectList(String key) {
        List<Object> value = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            if (jedis.exists(getBytesKey(key))) {
                List<byte[]> list = jedis.lrange(getBytesKey(key), 0, -1);
                value = Lists.newArrayList();
                for (byte[] bs : list) {
                    value.add(toObject(bs));
                }
                log.debug("getObjectList {} = {}", key, value);
            }
        } catch (Exception e) {
            log.warn("getObjectList {} = {}", key, value, e);
        } finally {
            returnResource(jedis);
        }
        return value;
    }

    /**
     * 设置list缓存
     * @param key 键
     * @param value 值
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    public  long setList(String key, List<String> value, int cacheSeconds) {
        long result = 0;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            if (jedis.exists(key)) {
                jedis.del(key);
            }
            result = jedis.rpush(key, value.toArray(new String[value.size()]));
            if (cacheSeconds != 0) {
                jedis.expire(key, cacheSeconds);
            }
            log.debug("setList {} = {}", key, value);
        } catch (Exception e) {
            log.warn("setList {} = {}", key, value, e);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 设置list缓存
     * @param key
     * @param value
     * @param cacheSeconds
     * @return
     */
    public  long setObjectList(String key, List<Object> value, int cacheSeconds) {
        long result = 0;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            if (jedis.exists(getBytesKey(key))) {
                jedis.del(key);
            }
            List<byte[]> list = Lists.newArrayList();
            for (Object o : value){
                list.add(toBytes(o));
            }
            result = jedis.rpush(getBytesKey(key), (byte[][])list.toArray());
            if (cacheSeconds != 0) {
                jedis.expire(key, cacheSeconds);
            }
            log.debug("setObjectList {} = {}", key, value);
        } catch (Exception e) {
            log.warn("setObjectList {} = {}", key, value, e);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 向list缓存中添加值
     * @param key
     * @param value
     * @return
     */
    public  long listAdd(String key, String... value) {
        long result = 0;
        Jedis jedis = null;
        try {
            jedis =jedisPool.getResource();
            result = jedis.rpush(key, value);
            log.debug("listAdd {} = {}", key, value);
        } catch (Exception e) {
            log.warn("listAdd {} = {}", key, value, e);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 向list缓存添加值
     * @param key
     * @param value
     * @return
     */
    public  long listObjectAdd(String key, Object... value) {
        long result = 0;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            List<byte[]> list = Lists.newArrayList();
            for (Object o : value){
                list.add(toBytes(o));
            }
            result = jedis.rpush(getBytesKey(key), (byte[][])list.toArray());
            log.debug("listObjectAdd {} = {}", key, value);
        } catch (Exception e) {
            log.warn("listObjectAdd {} = {}", key, value, e);
        } finally {
            returnResource(jedis);
        }
        return result;
    }


    /**
     * 关闭redis
     * @param jedis
     */
    void returnResource(Jedis jedis){
        if (jedis!=null){
            jedis.close();
        }
    }

    /**
     * 获取bytes 类型的key
     * @param object
     * @return
     */
    public static byte[] getBytesKey(Object object){
        if (object instanceof  String){
            String str = (String) object;
            return str.getBytes();
        }else{
            return SerializeUtils.serialize(object);
        }
    }

    /**
     * bytes 转 obj
     * @param bytes
     * @return
     */
    public static Object toObject(byte[] bytes){
        return SerializeUtils.deserialize(bytes);
    }

    /**
     * obj 转 bytes
     * @param obj
     * @return
     */
    public static byte[] toBytes(Object obj){
        return SerializeUtils.serialize(obj);
    }
}
