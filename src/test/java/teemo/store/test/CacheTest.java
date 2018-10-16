package teemo.store.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.ApplicationStartUp;
import com.example.sevice.UserServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={ApplicationStartUp.class}) // classes 指定启动类
public class CacheTest {
    
    @Autowired
    private UserServiceImpl userService;
    
    @Test
    public void testCache(){
        System.out.println(userService.get(1));
        System.out.println(userService.get(1));
    }
}
