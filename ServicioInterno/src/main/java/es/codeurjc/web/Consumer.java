package es.codeurjc.web;


import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




@Component
public class Consumer {

	@RabbitListener(queues = "messages", ackMode = "AUTO")
	public void received(String message) {
		
		System.out.println("Message: "+message);
	}
}