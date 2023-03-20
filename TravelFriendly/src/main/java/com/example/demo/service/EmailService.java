package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender mail;
	
	public ResponseEntity<Void> sendMail(String destiny, String subject, String body){
		
		SimpleMailMessage email = new SimpleMailMessage();
		
		email.setTo(destiny);
		email.setFrom("travelfriendly@outlook.com");
		email.setSubject(subject);
		email.setText(body);
		
		mail.send(email);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
