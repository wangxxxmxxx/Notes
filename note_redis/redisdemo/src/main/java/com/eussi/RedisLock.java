package com.eussi;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.List;
import java.util.UUID;

/**
 * @author wangxueming
 * @create 2019-07-01 23:37
 * @description
 */
public class RedisLock {

    public String getLock(String key, int timeout) {
        try {
            Jedis jedis = RedisManager.getJedis();
            String value = UUID.randomUUID().toString();//UUID
            long end = System.currentTimeMillis() + timeout*1000;
            while (System.currentTimeMillis() < end) {//在一段时间内尝试获取锁
                if (jedis.setnx(key, value) == 1) {//设置成功返回1
                    jedis.expire(key, timeout);
                    //锁设置成功, redis操作成功
                    return value;
                }
                if (jedis.exists(key) && jedis.ttl(key) == -1) {//edis.setnx(key, value) == 1执行完毕宕机，重新检测过期时间,并设置
                    jedis.expire(key, timeout);
                }
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean releaseLock(String key, String value) {
        try {
            Jedis jedis = RedisManager.getJedis();
            while(true) {
                jedis.watch(key);//一般是和事务一起使用，当对某个key进行watch后如果其他的客户端对这个key进行了更改，那么本次事务会被取消，事务的exec会返回null。jedis.watch(key)都会返回OK
                if(value.equals(jedis.get(key))) {
                    Transaction transaction = jedis.multi();
                    transaction.del(key);
                    List<Object> list = transaction.exec();
                    if(list==null) {
                        continue;
                    }
                    return true;
                }
                jedis.unwatch();
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        RedisLock redisLock = new RedisLock();
        String lockId = redisLock.getLock("lock:test2", 5);
        if(null!=lockId) {
            System.out.println("获得锁成功：" + lockId);
        } else {
            System.out.println("获得锁失败！");
        }

//        redisLock.releaseLock("lock:test2", lockId);//注释此句代码则获取失败,注释后调整下面锁的获取时间，大于超时时间也可以


        lockId = redisLock.getLock("lock:test2", 5);
        if(null!=lockId) {
            System.out.println("获得锁成功：" + lockId);
        } else {
            System.out.println("获得锁失败！");
        }
    }


}
