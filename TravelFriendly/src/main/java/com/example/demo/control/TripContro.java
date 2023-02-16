package com.example.demo.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import com.example.demo.model.Trip;
import com.example.demo.repository.TripRepository;

	@Controller 
	public class TripContro implements CommandLineRunner  {

		@Autowired
		private TripRepository repository;
		
		@Override
		public void run(String... args) throws Exception {
		repository.save(new Trip("Badajoz", "Madrid","17-02-2023",1,1,"vamonos"));
		repository.save(new Trip("Montijo", "Caceres","19-03-2023",1,1,"paseito"));


		}


	
}
