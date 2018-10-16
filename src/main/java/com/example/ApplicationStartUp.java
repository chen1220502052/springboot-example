package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
@EnableAspectJAutoProxy  // aop config
@EnableTransactionManagement // transaciton config
@EnableCaching
public class ApplicationStartUp {

    public static void main(String[] args) throws Exception{
        SpringApplication.run(ApplicationStartUp.class, args);
    }

}
