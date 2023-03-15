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
	
	@Autowired
	private TripRepository rTrip;

	
	@PostConstruct
	public void init() throws IOException, URISyntaxException {
		
		rUser.save(new User("user", passwordEncoder.encode("pass"), "user@gmail.com","USER"));
		rUser.save(new User("admin", passwordEncoder.encode("adminpass"), "admin@gmail.com", "USER", "ADMIN"));
		
		User u1 = new User("Matias", passwordEncoder.encode("Sarajevo"), "sarajevo@gmail.com","USER");
		User u2 = new User("Marcos", passwordEncoder.encode("Zagreb"), "zagreb@gmail.com","USER");
		User u3 = new User("Bernardo", passwordEncoder.encode("Transilvania"), "transilvania@gmail.com","USER");
		
		rUser.save(u1);
		rUser.save(u2);
		rUser.save(u3);
		
		Trip t1 = new Trip("Badajoz", "Madrid","2023-02-17",2,1,"vamonos");
		Trip t2 = new Trip("Montijo", "Caceres","2023-03-19",3,1,"paseito");
		Trip t3 = new Trip("Valencia", "Bilbao","2023-03-22",1,1,"cruzada");
		
		t1.SetConductor(u1);
		t2.SetConductor(u2);
		t3.SetConductor(u3);
		
		rTrip.save(t1);
		rTrip.save(t2);
		rTrip.save(t3);
	
		
	}
	

}
