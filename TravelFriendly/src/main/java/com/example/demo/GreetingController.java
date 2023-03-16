package com.example.demo;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Booking;
import com.example.demo.model.Trip;
import com.example.demo.model.User;
import com.example.demo.model.Message;
import com.example.demo.repository.BookingRepository;
import com.example.demo.repository.OpinionsRepository;
import com.example.demo.repository.TripRepository;
import com.example.demo.repository.UserRepository;

import com.example.demo.model.Chat;
import com.example.demo.model.Comprobacion;
import com.example.demo.model.Opinions;
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
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	//Usuario que se encuentre iniciando la aplicacion
	//De esta forma podremos acceder en todas las pantallas a los datos de este usuario sin necesidad de consultar la BD
	private User usuarioActual = new User();
	
	
	@ModelAttribute
	public void addAttributes(Model model, HttpServletRequest request) {

		Principal principal = request.getUserPrincipal();

		if (principal != null) {

			model.addAttribute("logged", true);
			model.addAttribute("userName", principal.getName()); //En cualquier vista se puede acceder a este valor
			model.addAttribute("admin", request.isUserInRole("ADMIN"));

		} else {
			model.addAttribute("logged", false);
			model.addAttribute("userName","Invitado");
		}
	}
	@RequestMapping("/")
	public String Base(Model model) {
		return "index";
	}
	
	@GetMapping("/Sesion")
	public String sesion(Model model) {
		
		
		return "main";
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
	public String Chat(Model model,HttpServletRequest request) {
		
		Principal principal = request.getUserPrincipal();
		
		Optional<User> user = repo.findByUsername(principal.getName());
		
		model.addAttribute("chats", user.get().getChats()); //esta haciendo la seleccion con todos los viajes existentes pero tendría que hacerlo con los comprados por el usuario
		return "chat";
	}
	

	@GetMapping("/chat/{id}")
	public String Chats(Model model,@PathVariable long id,HttpServletRequest request) {
		
		Principal principal = request.getUserPrincipal();
		
		Optional<User> user = repo.findByUsername(principal.getName());
		
		model.addAttribute("chats", user.get().getChats()); //esta haciendo la seleccion con todos los viajes existentes pero tendría que hacerlo con los comprados por el usuario
		Optional<Chat> chat = repoChat.findById(id);
		model.addAttribute("chat",chat.get());
		return "chats";
	}
	
	@PostMapping("/chat/{id}/SaveChats")
	public String MensajeS(Model model, Message m,@PathVariable long id,HttpServletRequest request ) {
		
		
        Principal principal = request.getUserPrincipal();
		Optional<User> user = repo.findByUsername(principal.getName());
		
		model.addAttribute("chats", user.get().getChats());
		Optional<Chat> chat = repoChat.findById(id);
      
		m.setDescripcion(user.get().getUsername());
		chat.get().getMensg().add(m);
      
		repoChat.save(chat.get());
		return "redirect:/chat/"+id;
	}
	//Si creo una entidad mensaje  y esta va a contener el id del chat al que pertenecen. De esta forma podre buscar 
	//todos los mensajes pertenecientes a un chat en concreto atraves de su repositorio 
	//ESta sera la unica forma e la cual podre listar (ya que no funcionan las listas) todos los mensajes pertenecientes a un chat
	
	@GetMapping("/perfil")
	public String Perfil(Model model, HttpServletRequest request) {
	
		//recogemos el nombre del usuario real a través del srrvicio http
		//buscamos su nombre en el repositorio
		String username = request.getUserPrincipal().getName();
		Optional <User> user = repo.findByUsername(username);
		List<Opinions> o = new ArrayList<Opinions>();
		List<Opinions> o2 = new ArrayList<Opinions>();
		for(int i=0;i<user.get().getOpinions().size();i++) {
			o.add(user.get().getOpinions().get(i));
			//repo.save(user.get().getOpinions().get(i).getDestiny());	
		}
		
		//for(int i=0;i<user.get().getPtrip().get(i).GetConductor().getOpinions().size();i++) {
			//o2.add(user.get().getPtrip().get(i).GetConductor().getOpinions().get(i));
			//repo.save(user.get().getOpinions().get(i).getDestiny());	
		//}
		
		model.addAttribute("opin", o);		
		//model.addAttribute("opin2", o2);		
		model.addAttribute("name", user.get().getUsername());
	
		return"profile";
	
	}
	
	@GetMapping("/tusViajes")
	public String tusViajes(Model model, HttpServletRequest request) {

		//recogemos el nombre del usuario real a través del srrvicio http
		//buscamos su nombre en el repositorio
		String username = request.getUserPrincipal().getName();
		Optional <User> user = repo.findByUsername(username);
		List<Trip> t = new ArrayList<Trip>();
		List<Trip> t2 = new ArrayList<Trip>();
		for(int i = 0; i<user.get().getBtrip().size(); i++) {
			t.add(user.get().getBtrip().get(i).getTrip());
			
		}
		
		model.addAttribute("name",user.get().getUsername());
		model.addAttribute("PTrip", user.get().getPtrip());
		model.addAttribute("BTrip", t);
		return "yourTravel";
	}
	@GetMapping("/opinarConductor/{id}")
	public String opinarC(Model model, @PathVariable long id, HttpServletRequest request) {
		//recogemos el nombre del usuario real a través del srrvicio http
		//buscamos su nombre en el repositorio
		String username = request.getUserPrincipal().getName();
		Optional <User> user = repo.findByUsername(username);
		
		//se declaran dos listas de donde se va a sacar la lista de todas las opiniones que el usuario ha realizado
		//y la segunda lista para recoger el id del conductor
		List<User> l = new ArrayList<User>();
		List<Trip> t = new ArrayList<Trip>();

		for(int i = 0; i<user.get().getBtrip().size(); i++) {
			t.add(user.get().getBtrip().get(i).getTrip());
			repoTrip.save(user.get().getBtrip().get(i).getTrip());
		}
		
		for(int i = 0; i<user.get().getBtrip().size(); i++) {
			l.add(user.get().getBtrip().get(i).getTrip().GetConductor());
			repo.save(user.get().getBtrip().get(i).getTrip().GetConductor());
		}
		//se recoge el del que se va a opinar viaje
		Optional<Trip> t2 = repoTrip.findById(id);
		//se recoge el conductor de dicho viaje
		Optional<User> l2= repo.findById(t2.get().GetConductor().getId());
		
		model.addAttribute("name", user.get().getUsername());
		model.addAttribute("nameConductor",t2.get().GetConductor().getUsername());
		model.addAttribute("IdConductor", l);
		
		
		return "opinion";
	}
	
	@GetMapping("/opinarPasajero/{id}")
	public String opinarP(Model model, @PathVariable long id, HttpServletRequest request) {
		
		//recogemos el nombre del usuario real a través del srrvicio http
		//buscamos su nombre en el repositorio
		
		String username = request.getUserPrincipal().getName();
		Optional <User> user = repo.findByUsername(username);
		
		//se declaran dos listas de donde se va a sacar la lista de todas las opiniones que el usuario ha realizado
		//y la segunda lista para recoger el id del conductor
		List<User> l = new ArrayList<User>();
		List<Trip> t = new ArrayList<Trip>();

		for(int i = 0; i<user.get().getPtrip().size(); i++) {
			t.add(user.get().getPtrip().get(i));
			repoTrip.save(user.get().getPtrip().get(i));
		}
		
		for(int i = 0; i<user.get().getPtrip().size(); i++) {
			l.add(user.get().getPtrip().get(i).getPasajeros().get(i));
			repo.save(user.get().getPtrip().get(i).getPasajeros().get(i));
		}
		
		//se recoge el del que se va a opinar viaje
		Optional<Trip> t2 = repoTrip.findById(id);
		model.addAttribute("name", user.get().getUsername());
		model.addAttribute("opina", t2.get().getPasajeros());
		model.addAttribute("IdPasajero", l);
		
		
		return "opinionPasajero";
	}
	
	@RequestMapping("/accionOpinarConductor/{id}")
	public String accionOpinar(Model model, @RequestParam String text ,
			@PathVariable long id, HttpServletRequest request) {
		String username = request.getUserPrincipal().getName();
		Optional <User> user = repo.findByUsername(username);
		
		Optional<Trip> t = repoTrip.findById(id);
		
		Opinions o2 = new Opinions(text,user.get(),t.get().GetConductor(), user.get().getUsername(), t.get().GetConductor().getUsername());
		user.get().addOpinion(o2);
		repoOpinion.save(o2);
		
		model.addAttribute("name", user.get().getUsername());
		
		return "main";
	}
	
	@RequestMapping("/accionOpinarPasajero/{id}")
	public String accionOpinarp(Model model, @RequestParam String text ,
			@PathVariable long id, HttpServletRequest request/*, @PathVariable long id2*/) {
		String username = request.getUserPrincipal().getName();
		Optional <User> user = repo.findByUsername(username);
		
		Optional<Trip> t = repoTrip.findById(id);
		//Optional<User> p =repo.findById(id2);
		
		//for(int i = 1; i<user.get().getOpinions().size(); i++) {
		Opinions o2 = new Opinions(text,user.get(),t.get().getPasajeros().get(t.get().getPasajeros().size()-1), user.get().getUsername(), t.get().getPasajeros().get(t.get().getPasajeros().size()-1).getUsername());
			user.get().addOpinion(o2);
			
			//repoTrip.save(user.get().getPtrip().get(i));
			
			repoOpinion.save(o2);
		//}
		model.addAttribute("name", user.get().getUsername());
		return "main";
	}
	
	@PostMapping("/accionPublicar") //metodo que gestiona cuando se publica un viaje
	public String publicar(Model model, @RequestParam String origin,
			@RequestParam String destiny,  @RequestParam String date,
			@RequestParam int sites, @RequestParam int stops, 
			@RequestParam String info, HttpServletRequest request) {
		
		String username = request.getUserPrincipal().getName();
		Optional <User> user = repo.findByUsername(username);
		
		Trip t = new Trip(origin, destiny, date, sites, stops, info); //crear un viaje con la info que ha introducido el usuario
		t.SetConductor(user.get()); //poner de conductor al usuario que ha publicado el viaje
		
		user.get().addTripP(t); //se añade a la lista de viajes publicados en el usuario
		repoTrip.save(t); //se guarda el viaje en la BD
		repo.save(user.get()); //se actualiza el usuario en la BD
		return "publish";

	}
	
	@RequestMapping("/accionBuscador") //metodo que gestiona cuando se usa el buscador
	public String buscar(Model model, @RequestParam String origin,
						@RequestParam String destiny, @RequestParam String date, HttpServletRequest request) {
		
		List <Optional<Trip>> tripDate = repoTrip.findByDate(date); //consultamos a la BD por fecha (así evitamos problemas de mayúsculas)
		List<Comprobacion> cTrip = new ArrayList<Comprobacion>(); //acordarse de los import
		
		for(int i = 0; i < tripDate.size(); i++) { //recorrer lista por fecha
			if((tripDate.get(i).get().getOr().equalsIgnoreCase(origin)) //si tiene el mismo origen
					&&(tripDate.get(i).get().getDest().equalsIgnoreCase(destiny)) //el mismo destino
					/*&&(tripDate.get(i).get().getConductorId()!=usuarioActual.getId())*/ //no esta publicado por el usuario
					&&(tripDate.get(i).get().getSites()>0)) { //y tiene sitios libres

				cTrip.add(new Comprobacion(tripDate.get(i).get())); //añadir a la lista de salida ese viaje
			}
		}
		
		String username;
		Optional <User> user;
		
		try {
			username = request.getUserPrincipal().getName();
			user = repo.findByUsername(username);
			
			for (int i = 0; i < cTrip.size(); i++) {
				if(cTrip.get(i).getTrip().getConductorId()==user.get().getId()) {
					cTrip.get(i).setComp(true);
				}
			}
		}catch(Exception e) {
			username = null;
		}
		
		if(cTrip.size()>0) { //si la lista de salida tiene algun viaje
			if(username!=null) {
				model.addAttribute("searched", true);
				model.addAttribute("resultados", cTrip); //se muestran los viajes
				model.addAttribute("visitor", false); //no es invitado
			}
			else {
				model.addAttribute("searched", true);
				model.addAttribute("resultados", cTrip); //se muestran los viajes
				model.addAttribute("visitor", true); //es un invitado
			}
		}
		else {//si no
			model.addAttribute("searched", false);
			model.addAttribute("error", "No se han encontrado resultados"); //se devuelve mensaje de error
		}
		
		return "search"; 
	}
	
	@PostMapping("/accionReserva") //metodo que se gestiona al reservar un viaje
	public String comprar(Model model, @RequestParam long id, HttpServletRequest request) { //le llega el id del viaje de un formulario invisible en html
		
		Optional<Trip> t = repoTrip.findById(id); //recupera el viaje reservado

		String username = request.getUserPrincipal().getName();
		Optional <User> user = repo.findByUsername(username);
		
		t.get().buyTrip(); //resta un hueco libre al viaje
		t.get().SetUsersinTrip(user.get()); //en la lista de usuarios del viaje mete al usuario
		repoTrip.save(t.get()); //volvemos a guardar el viaje en la BD

		Booking b = new Booking(user.get(), t.get()); //creamos una reserva a nombre del usuario y se le mete el viaje
		user.get().addTripB(b); //se añade la reserva al usuario
		//t.get().AddPasajeros(user.get()); //se añade al viaje un pasajero xd
		repo.save(user.get()); //actualizamos el usuario en la BD
		
		repoBook.save(b); //guardar reserva en la BD
		
		Chat c = new Chat(); //creamos el chat con el conductor y el usuario
		
		c.setDescripcion(t.get().getOr(),t.get().getDest(), t.get().GetConductor().getUsername(),user.get().getUsername()); //le añadimos la descripción

		repoChat.save(c); //se guarda el chat en la BD
		
		t.get().GetConductor().getChats().add(c);
		user.get().getChats().add(c);
		repo.save(user.get());
		repo.save(t.get().GetConductor());


		
		model.addAttribute("searched", false);
		model.addAttribute("error", "Reservado con éxito"); //En el hueco de error muestra que la reserva fue bien
		return "search";
	}
	
	@GetMapping("/inscribirse")
	public String crearCuenta(Model model) {
		
		return "signUp";
	}
	
	@PostMapping("/signedUp")
	public String registrarse(Model model, @RequestParam String username, @RequestParam String password, @RequestParam String mail) {
		repo.save(new User(username, passwordEncoder.encode(password), mail,"USER"));
		return "index";
	}
}
