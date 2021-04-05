package com.anonymizer.auth.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableAutoConfiguration(exclude = {
		SecurityAutoConfiguration.class
})
@EntityScan(basePackages = {"com.anonymizer.auth.model"})
@EnableJpaRepositories("com.anonymizer.auth.repository")
@SpringBootApplication(scanBasePackages = {
		"com.anonymizer.auth.controller",
		"com.anonymizer.auth.service",
		"com.anonymizer.auth.security",
		"com.anonymizer.auth.configuration",
		"com.anonymizer.auth.util"})

public class AuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthApplication.class, args);
	}

}
