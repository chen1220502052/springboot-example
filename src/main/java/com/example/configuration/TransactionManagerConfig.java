package com.example.configuration;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

@Configuration
public class TransactionManagerConfig implements TransactionManagementConfigurer {

    Logger log = LoggerFactory.getLogger(TransactionManagerConfig.class);

    
    @Resource(name="txManger")
    private PlatformTransactionManager txManger;
    
    
    @Bean(name = "txManger")
    public PlatformTransactionManager txManger(@Qualifier("firstDataSource") DataSource dataSource){
        log.info("create first txManger bean");
        return new DataSourceTransactionManager(dataSource);
    }
    
    @Bean(name = "txManagerSecond")
    public PlatformTransactionManager txMangerSecond(@Qualifier("secondDataSource") DataSource dataSource){
        log.info("create sencond txManger bean");
        return new DataSourceTransactionManager(dataSource);
    }
    
    // 实现接口 TransactionManagementConfigurer 方法，其返回值代表在拥有多个事务管理器的情况下默认使用的事务管理器
    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        log.info("create annotation default value is first txManger bean");
        return txManger;
    }

}
