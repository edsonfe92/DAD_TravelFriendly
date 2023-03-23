package com.example.demo;

import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication 
public class TravelFriendlyApplication {

	public static void main(String[] args) {
		SpringApplication.run(TravelFriendlyApplication.class, args);
		
	}
	@Bean
	public Queue myQueue() {
    	return new Queue("messages", false);
	}
}
