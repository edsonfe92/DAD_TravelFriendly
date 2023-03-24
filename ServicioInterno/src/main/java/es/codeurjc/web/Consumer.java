package es.codeurjc.web;


import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.codeurjc.web.service.ContainerHttp;
import es.codeurjc.web.service.EmailService;
import es.codeurjc.web.service.PDFExportController;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.servlet.http.HttpServletResponse;





@Component
public class Consumer {
	
	@Autowired
	EmailService email;
	
	//@Autowired
	//PDFExportController pdfService;
	
	@RabbitListener(queues = "messages", ackMode = "AUTO")
	public void receivedMailData(Object[] data,HttpServletResponse res) throws IOException, ClassNotFoundException {
		
		if(((String) data[0]).equalsIgnoreCase("email")) {
			email.sendMail((String)data[1], (String)data[2], (String)data[3]);
			//System.out.println("EMAIL");
		}/*else if (((String) data[0]).equalsIgnoreCase("pdf")) {
			//ContainerHttp c= (ContainerHttp) data[5];
			//ByteArrayInputStream bs = new ByteArrayInputStream((byte[]) data[5]);
			//ObjectInputStream is = new ObjectInputStream(bs);
			//ContainerHttp c = (ContainerHttp)is.readObject();
			//is.close();
			pdfService.generatePDF(res,(String)data[1], (String)data[2], (String)data[3], (String)data[4]);
		}*/
		
	}
	
	
}