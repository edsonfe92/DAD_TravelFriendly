package com.example.demo.rabbitmq;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.Queue;

@Configuration
public class PublisherConfig {
	//valor del nombre de la cola en la que publicamos
    @Value("${sacavix.queue.name}")
    //variable local para guardar nuestro mensaje
    private String message;

    @Bean
    //retornamos el mensaje de la cola
    public Queue queue() {
        return new Queue(message, true);
    }

}