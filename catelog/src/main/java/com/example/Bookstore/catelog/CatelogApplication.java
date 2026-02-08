package com.example.Bookstore.catelog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationProperties.class)
public class CatelogApplication {

	public static void main(String[] args) {
		SpringApplication.run(CatelogApplication.class, args);
	}

}
