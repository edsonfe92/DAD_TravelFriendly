package com.example.demo.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import com.example.demo.model.Opinions;
import com.example.demo.model.User;
import com.example.demo.repository.OpinionsRepository;

@Controller
public class OpinionsController implements CommandLineRunner   {

	
	@Autowired
	private OpinionsRepository repository;
	
	@Override
	public void run(String... args) throws Exception {
	
		//repository.save(new Opinions("El viaje ha sido un poco mojón", new User("Pepe", "123"), new User("Laura", "444444")));
		//repository.save(new Opinions("El viaje ha estao chido", new User("Manolo", "123"), new User("Icenia", "444444")));
	}

}
