package com.example.configuration;

import java.io.File;
import java.io.IOException;

import javax.servlet.MultipartConfigElement;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.servlet.DispatcherServlet;

public class TomcatConfig {
  
//  @Bean
  public ServletWebServerFactory servletContainer(){
      TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
      tomcat.addAdditionalTomcatConnectors(createSslConnector());
      return tomcat;
  }
  
  private Connector createSslConnector(){
      Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
      Http11NioProtocol protocol = (Http11NioProtocol) connector.getProtocolHandler();
      try{
          File keystore = new ClassPathResource("keystore").getFile();
          File truststore = new ClassPathResource("keystore").getFile();
          connector.setScheme("https");
          connector.setSecure(true);
          protocol.setKeystoreFile(keystore.getAbsolutePath());
          protocol.setKeystorePass("changeit");
          protocol.setTruststoreFile(truststore.getAbsolutePath());
          protocol.setTruststorePass("changeit");
          protocol.setKeyAlias("apitester");
          return connector;
      }catch(IOException ex){
          throw new IllegalStateException("can't access keystore: [" + "keystore"
                  + "] or truststore: [" + "keystore" + "]", ex);
      }
  }
}
