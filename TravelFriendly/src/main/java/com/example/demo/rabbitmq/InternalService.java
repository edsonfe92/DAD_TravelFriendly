package com.example.demo.rabbitmq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InternalService {

	@Autowired
	private Publisher publisher;
	 //Servicios Publicados
    public void sendPDFToRabbit(String m) {
   	 publisher.send(m);
    }
}
