package com.example.demo.rabbitmq;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.demo.service.PDFExportController;



@Component
public class Publisher {

	@Autowired
	RabbitTemplate rabbitTemplate;
	@Autowired
	private PDFExportController pdfService;
	
	
	

	
	@Scheduled(fixedRate = 1000)
	public void sendPDF(HttpServletResponse response, String o, String d, String f, String u) throws IOException {
		
		
		String  array []= new String[4];
		array[0]=o;
		array[1]=d;
		array[2]=f;
		array[3]=u;
		//System.out.println("publishToQueue: '" + data + "'");
				
		rabbitTemplate.convertAndSend("messages", array);
	}
	
	public void sendMailData(String destiny, String subject, String body) {
		
		String [] dataMail = new String[3];
		dataMail[0] = destiny;
		dataMail[1] = subject;
		dataMail[2] = body;
		
		rabbitTemplate.convertAndSend("messages", dataMail);
	}
}
     
     
     
     
