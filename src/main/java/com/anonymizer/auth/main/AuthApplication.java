package com.anonymizer.auth.main;

import com.anonymizer.auth.controller.jpa.UserController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableAutoConfiguration(exclude = {
		SecurityAutoConfiguration.class
})
//@ComponentScan(basePackageClasses  = UserController.class)
@EntityScan(basePackages = {"com.anonymizer.auth.models"})
@EnableJpaRepositories("com.anonymizer.auth.repository")
@SpringBootApplication(scanBasePackages={
		"com.anonymizer.auth.controller.jpa",
		"com.anonymizer.auth.services",
		"com.anonymizer.auth.repository",
		"com.anonymizer.auth.security",
		"com.anonymizer.auth.configuration"},
	exclude = SecurityAutoConfiguration.class)

public class AuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthApplication.class, args);
	}

}
