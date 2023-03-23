package com.example.demo.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;



@Component
public class Publisher {

	@Autowired
	RabbitTemplate rabbitTemplate;
	
	private int numData;

	@Scheduled(fixedRate = 1000)
	public void sendMessage() {
		
		String data = "Data " + numData++;
		
		System.out.println("publishToQueue: '" + data + "'");
				
		rabbitTemplate.convertAndSend("messages", data);
	}
}
     
     
     
     
