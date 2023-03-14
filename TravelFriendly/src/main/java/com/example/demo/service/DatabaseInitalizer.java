package com.example.demo.service;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.repository.*;

import com.example.demo.model.*;

@Service
public class DatabaseInitalizer {

	
	@Autowired
	private UserRepository rUser;

	@Autowired
	private PasswordEncoder passwordEncoder;

	
	@PostConstruct
	public void init() throws IOException, URISyntaxException {
		
		rUser.save(new User("user", passwordEncoder.encode("pass"), "user@gmail.com","USER"));
		rUser.save(new User("admin", passwordEncoder.encode("adminpass"), "admin@gmail.com", "USER", "ADMIN"));
	
		
	}
	

}
