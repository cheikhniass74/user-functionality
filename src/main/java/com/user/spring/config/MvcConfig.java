package com.user.spring.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter{
	@Override
    public void addViewControllers(ViewControllerRegistry viewControllerRegistry){
		viewControllerRegistry.addViewController("/").setViewName("home");
		viewControllerRegistry.addViewController("/login").setViewName("login");
	}
	

}
