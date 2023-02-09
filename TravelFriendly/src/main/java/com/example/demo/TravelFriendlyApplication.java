package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import repository.UserRepository;

@SpringBootApplication 
public class TravelFriendlyApplication {

	public static void main(String[] args) {
		SpringApplication.run(TravelFriendlyApplication.class, args);
	}

}
