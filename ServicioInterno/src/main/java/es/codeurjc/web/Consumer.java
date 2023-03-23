package es.codeurjc.web;


import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import es.codeurjc.web.service.EmailService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




@Component
public class Consumer {
	
	@Autowired
	EmailService email;
	
	@RabbitListener(queues = "messages", ackMode = "AUTO")
	public void receivedMailData(String[] data) {
		
		if(data[0].equalsIgnoreCase("email")) {
			email.sendMail(data[1], data[2], data[3]);
			//System.out.println("EMAIL");
		}
		
	}
	
}