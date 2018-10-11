package teemo.store.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.ApplicationStartUp;

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
}
