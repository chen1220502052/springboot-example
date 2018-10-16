package com.example.sevice;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.dao.UserTestDao;
import com.example.model.User;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service("userService")
public class UserServiceImpl {
    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserTestDao userTestDao;
    
    @Autowired
    private JedisPool jedisPool;
    
    private static final String KEY_PREFIX = "test_user_";
    
    public boolean add(User user){
        if(userTestDao.add(user)){
            String key = KEY_PREFIX + user.getId();
            Jedis jedis = null;
            try{
                jedis = jedisPool.getResource();
                jedis.set(key, user.getName());
            }catch(Exception e){
                logger.error(e.getMessage());
            }finally{
                if(jedis != null){
                    jedis.close();
                }
            }
            return true;
        }
        return false;
    }
    
    @Cacheable(value = "user" , key = "'user'.concat(#id.toString())")
    public User get(int id){
        System.out.println("query form mysql... id=" + id);
        return userTestDao.get(id);
    }
}
