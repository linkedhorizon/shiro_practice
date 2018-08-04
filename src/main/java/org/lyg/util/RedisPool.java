package org.lyg.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author :lyg
 * @time :2018/8/4 0004
 */
public class RedisPool {
    private static JedisPool jedisPool;//jedis连接池
    private static Integer maxTotal = 20;//最大连接数
    private static Integer maxIdle = 10;//在jedispool中最大的idle状态（空闲的）的jedis实例的个数
    private static Integer minIdle = 2;//在jedispool中最小的idle状态（空闲的）的jedis实例的个数
    private static Boolean testOnBorrow = true;//在borrow一个jedis实例的时候，是否要进行验证操作，如果赋值true，则得到的jedis实例是可以用的
    private static Boolean testOnReturn = true;//在return一个jedis实例的时候，是否要进行验证操作，如果赋值true，则得到的jedis实例是可以用的

    private static String ip = "127.0.0.1";
    private static Integer port = 6379;
    private static void initPool(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();

        jedisPoolConfig.setMaxTotal(maxTotal);
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMinIdle(minIdle);

        jedisPoolConfig.setTestOnBorrow(testOnBorrow);
        jedisPoolConfig.setTestOnReturn(testOnReturn);

        jedisPoolConfig.setBlockWhenExhausted(true);//连接耗尽的时候，是否阻塞，false会抛出异常，true阻塞直到超时

        jedisPool = new JedisPool(jedisPoolConfig,ip,port,1000*2);
    }
    static {
        initPool();
    }

    public static Jedis getJedis(){
        return jedisPool.getResource();
    }

    public static void returnBrokenResource(Jedis resource){
        jedisPool.returnBrokenResource(resource);
    }

    public static void returnResource(Jedis resource){
        jedisPool.returnResource(resource);
    }

}
