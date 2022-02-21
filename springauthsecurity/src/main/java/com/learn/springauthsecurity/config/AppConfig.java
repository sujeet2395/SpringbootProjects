package com.learn.springauthsecurity.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class AppConfig {
	@Bean
	DataSource datasource() {
		DriverManagerDataSource driverManagerDataSource=new DriverManagerDataSource();
		driverManagerDataSource.setUrl("jdbc:mysql://localhost:3306/mydb");	
		driverManagerDataSource.setUsername("root");
		driverManagerDataSource.setPassword("Sharma@2395");
		driverManagerDataSource.setDriverClassName("com.mysql.jdbc.Driver");
		return driverManagerDataSource;
	}
}
