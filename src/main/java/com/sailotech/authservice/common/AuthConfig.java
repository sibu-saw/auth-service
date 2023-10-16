package com.sailotech.authservice.common;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class AuthConfig {

	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public ModelMapper getModelMapper() {
		return new ModelMapper();
	}
	
	@Bean
	public SecurityFilterChain configureSecurity(HttpSecurity httpSecurity) throws Exception {
		
		httpSecurity.cors().disable(); //Cross origin resource sharing
		httpSecurity.csrf().disable();
		
		httpSecurity.authorizeHttpRequests(config -> {
			config.anyRequest().permitAll();
		});
		
		return httpSecurity.build();
	}
}
