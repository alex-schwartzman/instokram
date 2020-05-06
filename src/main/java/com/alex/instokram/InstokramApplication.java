package com.alex.instokram;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.alex.instokram.dao.jpa")
@ComponentScan(basePackages = "com.alex.instokram")
@EnableAutoConfiguration  // Sprint Boot Auto Configuration
@Configuration
public class InstokramApplication {

	public static void main(String[] args) {
		SpringApplication.run(InstokramApplication.class, args);
	}

}
