package com.eussi;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author wangxueming
 * @create 2019-07-01 23:32
 * @description
 */
public class RedisManager {
    private static JedisPool jedisPool;
    static {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(20);
        jedisPoolConfig.setMaxIdle(10);
        jedisPool = new JedisPool(jedisPoolConfig, "192.168.198.201", 6379);
    }

    public static Jedis getJedis() throws Exception {
        if(jedisPool!=null) {
            return jedisPool.getResource();
        }
        throw new Exception("JedisPool is not init");
    }
}
