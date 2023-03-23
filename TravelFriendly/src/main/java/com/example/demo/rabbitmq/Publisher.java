package com.example.demo.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Component
@EnableRabbit
@RequestMapping("test")
public class Publisher {
	
	//inyectamos dependencias del RabbitTemplate
	 @Autowired
	 private RabbitTemplate rabbitTemplate;
	 //inyectamos la cola previamente configurada para devolver un mensaje
     @Autowired
     private Queue queue;
     //esta clase envía el mensaje a la cola con un determinado mensaje
     public void send(Object message) {
         rabbitTemplate.convertAndSend(queue.getName(), message);
     }
     
     //Servicios Publicados
     public void sendPDFToRabbit(String m) {
    	 send(m);
     }
     
     @GetMapping
     public void testSendMessage() {
    	 String m= "hola cola";
    	 sendPDFToRabbit(m);
     }
     
}