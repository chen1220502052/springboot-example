package teemo.store.test;
import org.junit.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.example.ApplicationStartUp;
import com.example.controller.UserVehicleController;
import com.example.model.VehicleDetails;
import com.example.sevice.UserServiceImpl;
import com.example.sevice.UserVehicleService;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=ApplicationStartUp.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // classes 指定启动类
public class MyControllerTests {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserServiceImpl userService;

    @Test
    public void testExample() throws Exception {

        this.mvc.perform(get("/say").accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk());
    }
}
