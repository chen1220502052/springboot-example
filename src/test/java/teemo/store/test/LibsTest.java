package teemo.store.test;

import com.jd.open.api.sdk.DefaultJdClient;
import com.jd.open.api.sdk.JdClient;

public class LibsTest {
    static String accessToken = "";
    static String appKey = "";
    static String appSecret = "";
    static String SERVER_URL = "";
    
    public static void main(String[] args){
        JdClient jdClient = new DefaultJdClient(SERVER_URL,accessToken,appKey,appSecret); 
    }
}
