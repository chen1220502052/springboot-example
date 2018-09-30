package teemo.store.test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.dao.RedisRepository;

@RunWith(SpringRunner.class)
@DataRedisTest
public class ExampleDataRedisTests {
    @Autowired
    private RedisRepository repository;

}
