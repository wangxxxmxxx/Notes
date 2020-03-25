package com.eussi;

import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangxueming
 * @create 2019-07-04 1:32
 * @description
 */
public class RedisLua {
    public static void main(String[] args) throws Exception {
        //执行Lua脚本
        evalLua();
        //通过摘要执行Lua脚本缓存
        evalshaLua();

        //根据传入参数，可能会失败
        evalshaLua();
        evalshaLua();
    }

    private static void evalLua() throws Exception {
        Jedis jedis = RedisManager.getJedis();

        String lua = "local num=redis.call('incr',KEYS[1])\n" +
                "if tonumber(num)==1 then\n" +
                "   redis.call('expire',KEYS[1],ARGV[1])\n" +
                "   return 1\n" +
                "elseif tonumber(num)>tonumber(ARGV[2]) then\n" +
                "   return 0\n" +
                "else\n" +
                "   return 1\n" +
                "end";

        List<String> keys = new ArrayList<String>();
        keys.add("phone:limit");
        List<String> argss = new ArrayList<String>();
        argss.add("10");
        argss.add("2");
        Object obj = jedis.eval(lua, keys, argss);
        System.out.println(obj);
    }

    private static void evalshaLua() throws Exception {
        Jedis jedis = RedisManager.getJedis();

        String lua = "local num=redis.call('incr',KEYS[1])\n" +
                "if tonumber(num)==1 then\n" +
                "   redis.call('expire',KEYS[1],ARGV[1])\n" +
                "   return 1\n" +
                "elseif tonumber(num)>tonumber(ARGV[2]) then\n" +
                "   return 0\n" +
                "else\n" +
                "   return 1\n" +
                "end";

//        String sha = jedis.scriptLoad(lua);//仅需在第一次执行时进行缓存或者摘要即可
//        System.out.println("脚本sha:" + sha);

        String sha = "8a8ee74e246c39d3ac49ddfc938fa2942c56e087";

        List<String> keys = new ArrayList<String>();
        keys.add("phone:limit");
        List<String> argss = new ArrayList<String>();
        argss.add("10");
        argss.add("2");
        Object obj = jedis.evalsha(sha, keys, argss);
        System.out.println(obj);
    }
}
