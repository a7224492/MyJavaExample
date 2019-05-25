package com.mycode.redis;

import redis.clients.jedis.Jedis;

/**
 * Author: jiangzhen
 * Date: 2019/5/19 10:23
 */
public class JedisTest {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("192.168.47.129", 6379);
        jedis.connect();

        jedis.set("name", "jiangzhen");
    }
}
