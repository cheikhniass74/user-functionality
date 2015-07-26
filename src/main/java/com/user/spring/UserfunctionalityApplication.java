package com.user.spring;

import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableAsync
public class UserfunctionalityApplication {
	private static final Log log= LogFactory.getLog(UserfunctionalityApplication.class);
    public static void main(String[] args) {
      ApplicationContext context =  SpringApplication.run(UserfunctionalityApplication.class, args);
      String[] beanames= context.getBeanDefinitionNames();
      Arrays.sort(beanames);
      
      log.info("Beans in application context");
      
     for(String beanname:beanames){
    	 log.info(beanname);
    	 
     }
    }
}
