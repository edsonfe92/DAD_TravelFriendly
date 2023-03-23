package com.example.demo.rabbitmq;

import javax.xml.crypto.Data;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;




@Component
public class Consummer {

	@RabbitListener(queues = { "${colaDeMensajes.queue.name}" })
	public void receive(@Payload Data message) {

		log.info("Received message {}", message);

		makeSlow();

	}

	private void makeSlow() {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}