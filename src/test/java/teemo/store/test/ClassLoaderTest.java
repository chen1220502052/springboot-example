package teemo.store.test;

import teem.store.loader.MyClassLoader;

public class ClassLoaderTest {

    public static void main(String[] args){
        MyClassLoader classLoader = new MyClassLoader();
        classLoader.setPath("D:\\myWorkplace\\teemo.store\\target\\test-classes\\java\\");
        try {
            Class<?> clazz = classLoader.loadClass("lang.Object");
            clazz.newInstance();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
