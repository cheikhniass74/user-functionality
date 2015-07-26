package com.user.spring.config;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

@Configuration
@EnableWebMvcSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	private static final Log LOG = LogFactory.getLog(WebSecurityConfig.class);
	@Resource
	private UserDetailsService userService;

	@Value("${rememberMe.privateKey}")
	private String key;

	@Bean
	public RememberMeServices rememberMeServices() {
		TokenBasedRememberMeServices tokenBasedRememberMeServices = new TokenBasedRememberMeServices(
				key, userService);
		return tokenBasedRememberMeServices;

	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		LOG.info("Creating a pasword encoder bean");
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/", "/home", "/forgot-password",
						"/reset-password/*", "/signup", "/public/**",
						"/users/*").permitAll().anyRequest().authenticated()
				.and().formLogin().loginPage("/login").permitAll().and()
				.rememberMe().key(key).rememberMeServices(rememberMeServices())
				.and().logout().permitAll();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.userDetailsService(userService).passwordEncoder(passwordEncoder());

	}

}
