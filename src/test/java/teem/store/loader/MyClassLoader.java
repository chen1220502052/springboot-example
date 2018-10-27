package teem.store.loader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


public class MyClassLoader extends ClassLoader {
    private String path;   //类的加载路径
    private String name;   //类加载器的名字
    
    
    public MyClassLoader() {

    }
    
    public static void main(String[] args){
        String name = "java.lang.Object";
        System.out.println(name.replaceAll("\\.", "\\\\"));
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        // TODO Auto-generated method stub
        return findClass(name);
    }



    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] bytes = loadClassData(name);
        return defineClass(name, bytes, 0, bytes.length);
//        return super.findClass(name);
    }
    
    //用于加载类文件
    private byte[] loadClassData(String name) {
        name = name.replaceAll("\\.", "\\\\");
        name = path + name + ".class";  
        System.out.println(name);
        //使用输入流读取类文件
        InputStream in = null;
        //使用byteArrayOutputStream保存类文件。然后转化为byte数组
        ByteArrayOutputStream out = null;
        try {
            in = new FileInputStream(new File(name));
            out = new ByteArrayOutputStream();
            int i = 0;
            while ( (i = in.read()) != -1){
                out.write(i);
            }
 
        }catch (Exception e){}
        finally {
            try {
                out.close();
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
 
        }
 
        return out.toByteArray();
 
    }
 
    public String getName() {
        return name;
    }
 
    public void setPath(String path) {
        this.path = path;
    }
    
}
