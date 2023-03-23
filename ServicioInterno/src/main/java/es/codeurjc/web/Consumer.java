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
	EmailService mail;

	@RabbitListener(queues = "messages", ackMode = "AUTO")
	public void received(String[] message) {
		
		//System.out.println("Message: "+message[0]);
	}
	
	@RabbitListener(queues = "messages", ackMode = "AUTO")
	public void receivedMailData(String[] data) {
		
		System.out.println("Message: " + data[0]);
	}
	
}