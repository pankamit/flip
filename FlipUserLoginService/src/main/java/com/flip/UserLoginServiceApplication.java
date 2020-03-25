package com.flip;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.flip.repository.UserRepository;

@EnableDiscoveryClient
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
public class UserLoginServiceApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(UserLoginServiceApplication.class, args);
		System.out.println("User Login Service");
	}
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(UserLoginServiceApplication.class);
    }

}
