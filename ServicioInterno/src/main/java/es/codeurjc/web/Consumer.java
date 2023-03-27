package es.codeurjc.web;


import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import es.codeurjc.web.service.EmailService;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.servlet.http.HttpServletResponse;





@Component
public class Consumer {
	
	@Autowired
	EmailService email;
	
	@RabbitListener(queues = "messages", ackMode = "AUTO")
	public void receivedMailData(Object[] data) throws IOException, ClassNotFoundException {
		
		if(((String) data[0]).equalsIgnoreCase("email")) {
			email.sendMail((String)data[1], (String)data[2], (String)data[3]);
		}
	}
	
	
}