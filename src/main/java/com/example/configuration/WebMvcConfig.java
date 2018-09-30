package com.example.configuration;

import javax.servlet.MultipartConfigElement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Configuration
public class WebMvcConfig implements WebMvcConfigurer {


    @Autowired
    private DispatcherServlet dispatcherServlet;
    
    @Autowired
    private MultipartConfigElement multipartConfigElement;
    
    
//  @Bean
  public ServletRegistrationBean apiServlet(){
      ServletRegistrationBean bean = new ServletRegistrationBean(dispatcherServlet);
      bean.addUrlMappings("*.do");
      bean.setMultipartConfig(multipartConfigElement);
      bean.setName("apiServlet");
      return bean;
  }
}
