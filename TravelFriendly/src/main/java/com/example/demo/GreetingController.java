package com.example.demo;

import java.util.ArrayList;
import java.util.List;
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

import com.example.demo.model.Booking;
import com.example.demo.model.Trip;
import com.example.demo.model.User;
import com.example.demo.repository.BookingRepository;
import com.example.demo.repository.OpinionsRepository;
import com.example.demo.repository.TripRepository;
import com.example.demo.repository.UserRepository;

import com.example.demo.model.Chat;
import com.example.demo.repository.ChatRepository;





@Controller
public class GreetingController {

	
	@Autowired 
	private UserRepository repo;
	
	@Autowired
	private TripRepository repoTrip;
	
	@Autowired
	private OpinionsRepository repoOpinion;
	
	@Autowired
	private BookingRepository repoBook;
	
	@Autowired
	private ChatRepository repoChat;
	
	//Usuario que se encuentre iniciando la aplicacion
	//De esta forma podremos acceder en todas las pantallas a los datos de este usuario sin necesidad de consultar la BD
	private User usuarioActual = new User();
	
	@GetMapping ("/greeting")
	public String greeting(Model model) {
		model.addAttribute("name", "World");
		return "greeting_template";
	}
	
	
	@RequestMapping("/Sesion")
	public String sesion(Model model, @RequestParam String user , @RequestParam String password ) {
		
		usuarioActual.setUsername(user);
		usuarioActual.setPassword(password);
		
		repo.save(usuarioActual);
		
		model.addAttribute("name", user);
		return "main";
	}
	
	@GetMapping("/")
	public String Base(Model model) {
		return "index";
	}
	
	@GetMapping("/bienvenida")
	public String bienvenida(Model model) {
		model.addAttribute("name", "Juan");
		return "main";
	}
	
	@GetMapping("/buscar")
	public String buscar(Model model) {
		model.addAttribute("searched", false);
		model.addAttribute("error", "");
		return "search";
	}
	
	@GetMapping("/publicar")
	public String Publicar(Model model) {
		
		return "publish";
	}
	
	@RequestMapping("/chat")
	public String Chat(Model model) {
		
		model.addAttribute("chats", repoChat.findAll()); //esta haciendo la seleccion con todos los viajes existentes pero tendría que hacerlo con los comprados por el usuario
		return "chat";
	}
	
	@GetMapping("/chat/{id}")
	public String Chats(Model model,@PathVariable long id) {
		model.addAttribute("chats", repoChat.findAll()); //esta haciendo la seleccion con todos los viajes existentes pero tendría que hacerlo con los comprados por el usuario
		return "chats";
	}
	
	
	@GetMapping("/perfil")
	public String Perfil(Model model) {
		//User u = repo.findByUsername("Pepe");
		//Optional<User> u = repo.findByUsername("Juan");
		//model.addAttribute("name", u.get().getUsername());
		//System.out.println("profile");
		model.addAttribute("name",usuarioActual.getUsername());
		
		
		return "profile";
	}
	
	@GetMapping("/tusViajes")
	public String tusViajes(Model model) {
		
		
		
		model.addAttribute("name", usuarioActual.getUsername());
		model.addAttribute("PTrip", usuarioActual.getPtrip());
		
		
		return "yourTravel";
	}
	
	@RequestMapping("/accionPublicar")
	public String publicar(Model model, @RequestParam String origin,
			@RequestParam String destiny,  @RequestParam String date,
			@RequestParam int sites, @RequestParam int stops, 
			@RequestParam String info) {
		
		
		Trip t = new Trip(origin, destiny, date, sites, stops, info);
		model.addAttribute("PTrip", t);
		t.SetConductor(usuarioActual);
		
		usuarioActual.addTripP(t);
		repoTrip.save(t);
		
	
			return "publish";

	}
	
	@RequestMapping("/accionBuscador")
	public String buscar(Model model, @RequestParam String origin,
						@RequestParam String destiny, @RequestParam String date) {
		
		List <Optional<Trip>> tripDate = repoTrip.findByDate(date); //consultamos a la BD por fecha (así evitamos problemas de mayúsculas)
		List <Trip> tripOutput = new ArrayList <Trip>(); //lista de viajes a mostrar, coinciden fecha, origen y destino (comprobar asientos libres)
		
		for(int i = 0; i < tripDate.size(); i++) {
			if((tripDate.get(i).get().getOr().equalsIgnoreCase(origin))&&(tripDate.get(i).get().getDest().equalsIgnoreCase(destiny))) {
				tripOutput.add(tripDate.get(i).get());
			}
		}
		
		if(tripOutput.size()>0) {
			model.addAttribute("searched", true);
			model.addAttribute("resultados", tripOutput);
		}
		else {
			model.addAttribute("searched", false);
			model.addAttribute("error", "No se han encontrado resultados");
		}
		
		
		return "search"; 
	}
	
	@RequestMapping("/accionReserva")
	public String comprar(Model model, @RequestParam long id) {
		
		Optional<Trip> t = repoTrip.findById(id);
		//t.get().buyTrip();
		
		Booking b = new Booking(t.get());
		repoBook.save(b);
		
		Chat c = new Chat(t.get().GetConductor(), usuarioActual);
		c.setDescripcion(t.get().getOr(),t.get().getDest(), t.get().GetConductor().getUsername());
		
		repoChat.save(c);
		
		
		
		model.addAttribute("searched", false);
		model.addAttribute("error", "");
		return "search";
	}
}
