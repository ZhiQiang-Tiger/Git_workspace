package com.zhou.git;

import redis.clients.jedis.Jedis;

public class HelloJedis {
    public static void main(String[] args) {
        //创建Jedis对象
        Jedis jedis = new Jedis("192.168.137.128",6379);
        //密码
        jedis.auth("123456");
        //测试
        String ping = jedis.ping();
        System.out.println(ping);

    }

    //操作key
}
