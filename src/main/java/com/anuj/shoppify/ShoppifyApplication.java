package com.anuj.shoppify;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class ShoppifyApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShoppifyApplication.class, args);
	}

}
