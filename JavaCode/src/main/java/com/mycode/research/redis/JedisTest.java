package com.mycode.research.redis;

import redis.clients.jedis.Jedis;

/**
 * @author jiangzhen
 * @date 2019/5/20 10:57
 */
public class JedisTest {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("192.168.58.130", 6379);
        jedis.connect();

        jedis.set("user.name", "jiangzhen");

        System.out.println(jedis.get("user.name"));
    }
}
