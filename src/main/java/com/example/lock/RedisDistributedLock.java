package com.example.lock;

import java.util.Collections;

import redis.clients.jedis.Jedis;

/**
 *  redis 分布式锁
 * @author chenqiankun
 *
 */
public class RedisDistributedLock {

    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX"; // if not exist set
    private static final String SET_WITH_EXPIRE_TIME_MS = "PX"; // expire time millisecond
//    private static final String SET_WITH_EXPIRE_TIME_S = "EX"; // expire time second
    private static final Long RELEASE_SUCCESS = 1L;
    private static final String RELEASE_LOCK_SCRIPT = "if redis.call('get', KEYS[1]) == ARGV[1] "
            + "then return redis.call('del', KEYS[1]) else return 0 end";
    
    public static boolean tryGetDistributeLock(Jedis jedis, String lockKey, 
            String lockValue, int expireTime){
        String result = jedis.set(lockKey, lockValue, SET_IF_NOT_EXIST, 
                SET_WITH_EXPIRE_TIME_MS, expireTime); // since redis version: 2.6.12
        if(LOCK_SUCCESS.equals(result)){
            return true;
        }
        return false;
    }
    
    public static boolean releaseDistributedLock(Jedis jedis, String lockKey, String lockValue){
        Object result = jedis.eval(RELEASE_LOCK_SCRIPT, 
                Collections.singletonList(lockKey), Collections.singletonList(lockValue)); // since 2.6.o
        if(RELEASE_SUCCESS.equals(result)){
            return true;
        }
        return false;
    }
}
