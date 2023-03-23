package com.example.demo.rabbitmq;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.servlet.http.HttpServletResponse;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.demo.model.ContainerHttp;
import com.example.demo.service.PDFExportController;



@Component
public class Publisher{

	@Autowired
	RabbitTemplate rabbitTemplate;
	
	
	
	

	
	//@Scheduled(fixedRate = 1000)
	public void sendPDF(ContainerHttp c, String o, String d, String f, String u) throws IOException {
		
		ByteArrayOutputStream bs = new ByteArrayOutputStream();
		ObjectOutputStream os = new ObjectOutputStream(bs);
		os.writeObject(c);
		os.close();
		byte[] bytes = bs.toByteArray();
		
		Object array []= new Object[6];
		array[0]="pdf";
		array[1]=o;
		array[2]=d;
		array[3]=f;
		array[4]=u;
		array[5]=bytes;
		//System.out.println("publishToQueue: '" + data + "'");
				
		rabbitTemplate.convertAndSend("messages", array);
	}
	
	public void sendMailData(String destiny, String subject, String body) {
		
		String [] dataMail = new String[4];
		dataMail[0] = "email";
		dataMail[1] = destiny;
		dataMail[2] = subject;
		dataMail[3] = body;
		
		rabbitTemplate.convertAndSend("messages", dataMail);
	}
}
     
     
     
     
