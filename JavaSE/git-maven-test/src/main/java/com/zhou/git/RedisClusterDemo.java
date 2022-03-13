package com.zhou.git;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

public class RedisClusterDemo {
    public static void main(String[] args) {
        //创建对象
        HostAndPort hostAndPort = new HostAndPort("192.168.137.129", 6379);
        JedisCluster jedisCluster = new JedisCluster(hostAndPort,6000,1000,1,
                "123456",new GenericObjectPoolConfig());
        //进行操作
        jedisCluster.set("b1","value1");
        String value = jedisCluster.get("b1");
        System.out.println("value:"+value);
    }
}
