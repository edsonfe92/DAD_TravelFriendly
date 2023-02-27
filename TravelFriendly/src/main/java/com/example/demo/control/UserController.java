package com.example.demo.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@Controller 
public class UserController implements CommandLineRunner  {

	@Autowired
	private UserRepository repository;
	
	@Override
	public void run(String... args) throws Exception {
	/*repository.save(new User("Jack", "Bauer"));
	repository.save(new User("Chloe", "O'Brian"));
	repository.save(new User("Kim", "Bauer"));
	repository.save(new User("David", "Palmer"));
	repository.save(new User("Michelle", "Dessler"));*/

	}
	
}
