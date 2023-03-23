package com.example.demo.rabbitmq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class ControllerService {

	@Autowired
	private InternalService service;
	
	@GetMapping
    public void testSendMessage() {
   	 String m= "hola cola";
   	 service.sendPDFToRabbit(m);
    }
}
