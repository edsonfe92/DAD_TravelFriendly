package com.example.demo;

import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Trip;
import com.example.demo.model.User;
import com.example.demo.repository.TripRepository;
import com.example.demo.repository.UserRepository;





@Controller
public class GreetingController {

	
	@Autowired 
	private UserRepository repo;
	
	@Autowired
	private TripRepository repoTrip;
	
	@PostConstruct
	public void init() {
		repo.save(new User("Pepe", "a"));
		repo.save(new User("Juan", "b"));
		//repoTrip.save(new Trip("Madrid", "Badajoz", "12/09/2023",
				//4, true, "viajesito"));
	}
	
	@GetMapping ("/greeting")
	public String greeting(Model model) {
		model.addAttribute("name", "World");
		return "greeting_template";
	}
	
	/*@GetMapping("/Sesion/{user}{password}")
	public String Sesion(Model model, @PathVariable String user, @PathVariable String password) {
		Optional<User> use = repo.findByUsername(user);
		if(use.isPresent()) {
			
			return "main";
		}else {
			
			return "index";
		}
	}*/
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
	
	@GetMapping("/bienvenida")
	public String Bienvenida(Model model) {
		model.addAttribute("name", "Juan");
		return "main";
	}
	
	@GetMapping("/buscar")
	public String Buscar(Model model) {
		model.addAttribute("resultados", "Aquí saldrían los viajes, hay que añadir movidas Mustache");
		return "search";
	}
	
	@GetMapping("/publicar")
	public String Publicar(Model model) {
		
		return "publish";
	}
	
	@GetMapping("/chat")
	public String Chat(Model model) {

		return "chat";
	}
	
	@GetMapping("/perfil")
	public String Perfil(Model model) {
		//User u = repo.findByUsername("Pepe");
		Optional<User> u = repo.findByUsername("Juan");
		model.addAttribute("name", u.get().getUsername());
		//System.out.println("profile");
		return "profile";
	}
	
	@RequestMapping("/accionPublicar")
	public String publicar(Model model, @RequestParam String origin,
			@RequestParam String destiny,  @RequestParam String date,
			@RequestParam int sites, @RequestParam boolean stops, 
			@RequestParam String info) {
		Trip t = new Trip(origin, destiny, date, sites, stops, info);
		repoTrip.save(t);
		return "publish";
	}
	
	@RequestMapping("/accionBuscador")
	public String buscar(Model model, @RequestParam String search) {
		model.addAttribute("resultados", search);
		return "search"; 
	}
}
