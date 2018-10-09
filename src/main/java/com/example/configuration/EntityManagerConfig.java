package com.example.configuration;


import javax.persistence.EntityManagerFactory;

import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class EntityManagerConfig {
    
    public LocalContainerEntityManagerFactoryBean customerEntityManagerFactory(
            EntityManagerFactoryBuilder builder){
//        return builder.dataSource(dataSource);
        return null;
    }
    
    public PlatformTransactionManager jpaTxManager(EntityManagerFactory factory){ // jpaTxManager
        return new JpaTransactionManager(factory);
    }
}
