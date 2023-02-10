package com.example.demo;

import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;





@Controller
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
	
	@GetMapping("/Sesion/{user}{password}")
	public String Sesion(Model model, @PathVariable String user, @PathVariable String password) {
		Optional<User> use = repo.findByUsername(user);
		if(use.isPresent()) {
			
			return "main";
		}else {
			
			return "index";
		}
	}
/*	@GetMapping("/Sesion/{id}")
	public String showBook(Model model, @PathVariable String id) {

		Optional<Book> book = bookService.findById(id);
		if (book.isPresent()) {
			model.addAttribute("book", book.get());
			return "book";
		} else {
			return "books";
		}

	}*/
	
	@GetMapping("/")
	public String Base(Model model) {
		return "index";
	}
}
