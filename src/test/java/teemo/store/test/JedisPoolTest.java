package teemo.store.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.ApplicationStartUp;
import com.example.lock.RedisDistributedLock;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={ApplicationStartUp.class}) // classes 指定启动类
public class JedisPoolTest {

    @Autowired
    private JedisPool jedisPool;
    
    @Test
    public void testJedisPool(){
        String key = "test_jedis";
        Jedis jedis = jedisPool.getResource();
        System.out.println(jedis.set(key, "test"));
        System.out.println(jedis.get(key));
        System.out.println(jedis.del(key));
    }
    
    @Test
    public void testRedisDistributedLock(){
        Jedis jedis = jedisPool.getResource();
        while(RedisDistributedLock.tryGetDistributeLock(jedis, "lock_key_1", "test", 10000)){
            try{
                System.out.println("get lock success...");
            }finally{
                int count = 3;
                while(!RedisDistributedLock.releaseDistributedLock(jedis, "lock_key_1", "test") && count >= 0){
                    System.out.println("release lock failed...");
                    count--;
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                if(count >= 0){
                    System.out.println("relase lock success...");
                }

            }
            break;
        }
    }
}
