package com.example.demo.rabbitmq;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.Queue;

@Configuration
public class PublisherConfig {
	//valor del nombre de la cola en la que publicamos
	@Value("${colaDeMensajes.queue.name}")
	private String message;
    //variable local para guardar nuestro mensaje
    

    
    //retornamos el mensaje de la cola
    @Bean
    public Queue queue() {
    	
        return new Queue(message, true);
    }

}