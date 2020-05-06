package com.alex.instokram;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.alex.instokram.dao.jpa")
public class InstokramApplication {

	public static void main(String[] args) {
		SpringApplication.run(InstokramApplication.class, args);
	}

}
