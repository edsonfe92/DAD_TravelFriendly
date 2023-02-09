package com.example.demo;

import model.User;
import repository.UserRepository;

import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class GreetingController {

	
	@Autowired 
	private UserRepository repo;
	
	@PostConstruct
	public void init() {
		repo.save(new User("Pepe", "a"));
		repo.save(new User("Juan", "b"));
	}
	
	@GetMapping ("/greeting")
	public String greeting(Model model) {
		model.addAttribute("name", "World");
		return "greeting_template";
	}
	
	@GetMapping("/Sesion")
	public String Sesion(Model model, @RequestParam String user, @RequestParam String password) {
		Optional<User> use = repo.findByName(user);
		if(use.isPresent()) {
			
			model.addAttribute("name","Paco");
			return "main";
		}else {
			
			return "index";
		}
			
	
	}
	@GetMapping("/")
	public String Base(Model model) {
		return "index";
	}
}
