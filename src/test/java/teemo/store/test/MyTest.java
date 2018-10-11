package teemo.store.test;

import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@Import(MyTestConfiguration.class)
public class MyTest {

    @Test
    public void exampleTest(){
        String s = "\uFE64" + "script" + "\uFE65";
        
        s = Normalizer.normalize(s, Form.NFKC);
        
        Pattern pattern = Pattern.compile("[<>]");
        Matcher mathcer = pattern.matcher(s);
        
        if(mathcer.find()){
            System.out.println("true");
        }else{
            System.out.println("false");           
        }
    }
}
