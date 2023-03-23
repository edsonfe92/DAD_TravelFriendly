package com.example.demo.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Component
public class Publisher {
	
	//inyectamos dependencias del RabbitTemplate
	 @Autowired
	 private RabbitTemplate rabbitTemplate;
	 
	 
	 //inyectamos la cola previamente configurada para devolver un mensaje
     @Autowired
     private Queue queue;
      private int dataAct;
     
     
     //esta clase envía el mensaje a la cola con un determinado mensaje
     @Scheduled(fixedRate = 1000)
     public void send(Object message) {
    	
		String data = "Data " + dataAct++;
 		
 		System.out.println("publishToQueue: '" + data + "'");
 				
 		rabbitTemplate.convertAndSend("messages", data);
        
     }
     



}
     
     
     
     
}