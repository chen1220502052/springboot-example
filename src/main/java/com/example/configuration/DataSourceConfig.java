package com.example.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class DataSourceConfig {
    
    
    @Bean("firstDataSourceProperties")
    @Qualifier("firstDataSourceProperties")
    @Primary
    @ConfigurationProperties(prefix="example.datasource.first")
    public DataSourceProperties firstDataSourceProperties(){
        return new DataSourceProperties();
    }
    
    @Bean("firstDataSource")
    @Qualifier("firstDataSource")
    @Primary
    @ConfigurationProperties(prefix="example.datasource.first")
    public DataSource firstDataSource(@Qualifier("firstDataSourceProperties") DataSourceProperties firstDataSourceProperties){
        return firstDataSourceProperties.initializeDataSourceBuilder().build();
    }
    
    @Bean
    @Qualifier("secondDataSourceProperties")
    @ConfigurationProperties(prefix="example.datasource.second")
    public DataSourceProperties secondDataSourceProperties(){
        return new DataSourceProperties();
    }
    
    @Bean("secondDataSource")
    @Qualifier("secondDataSource")
    @ConfigurationProperties(prefix="example.datasource.second")
    public DataSource secondDataSource(@Qualifier("secondDataSourceProperties") DataSourceProperties secondDataSourceProperties) {
        return secondDataSourceProperties.initializeDataSourceBuilder().build();
    }
    
    @Bean("jdbcTemplate")
    public JdbcTemplate jdbcTemplate(@Qualifier("firstDataSource") DataSource firstDataSource){
        JdbcTemplate jdbcTemplate = new JdbcTemplate(firstDataSource);
        return jdbcTemplate;
    }
    
    @Bean("secondJdbcTemplate")
    public JdbcTemplate secondJdbcTemplate(@Qualifier("secondDataSource") DataSource secondDataSource){
        JdbcTemplate jdbcTemplate = new JdbcTemplate(secondDataSource);
//        JdbcProperties.Template template = this.properties.getTemplate();
//        jdbcTemplate.setFetchSize(template.getFetchSize());
//        jdbcTemplate.setMaxRows(template.getMaxRows());
//        if (template.getQueryTimeout() != null) {
//            jdbcTemplate
//                    .setQueryTimeout((int) template.getQueryTimeout().getSeconds());
//        }
        return jdbcTemplate;
    }
}
