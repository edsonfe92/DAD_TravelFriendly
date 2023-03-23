package es.rabbitmq;


import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




@Component
public class Consumer {

	private static final Logger log = LoggerFactory.getLogger(Consumer.class);
	@RabbitListener(queues = { "${colaDeMensajes.queue.name}" })
	public void receive(@Payload String message) {

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