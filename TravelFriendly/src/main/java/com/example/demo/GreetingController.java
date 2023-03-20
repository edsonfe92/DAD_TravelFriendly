package com.example.demo;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.example.demo.generarPDF.ListarReservaPDF;
import com.example.demo.generarPDF.PDFExportController;
import com.example.demo.model.Booking;
import com.example.demo.model.Trip;
import com.example.demo.model.User;
import com.example.demo.model.Message;
import com.example.demo.repository.BookingRepository;
import com.example.demo.repository.OpinionsRepository;
import com.example.demo.repository.TripRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.EmailService;
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
	
	@Autowired
	private EmailService email;
	
	@Autowired
	private PDFExportController pdfService;
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
		model.addAttribute("o", "");
		model.addAttribute("d", "");
		model.addAttribute("f", "");
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
		Optional <User> us = repo.findByUsername(username);
		for(int i = 0; i<user.get().getBtrip().size(); i++) {
			t.add(user.get().getBtrip().get(i).getTrip());
			
		}
		
	
		
		
		model.addAttribute("name",user.get().getUsername());
		model.addAttribute("PTrip", user.get().getPtrip());
		//model.addAttribute("namePas", us.get().getUsername());
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
	
	@GetMapping("/opinarPasajero/{us}")
	public String opinarP(Model model, @PathVariable String us, HttpServletRequest request /*@PathVariable String usernm */) {
		
		//recogemos el nombre del usuario real a través del srrvicio http
		//buscamos su nombre en el repositorio
		
		String username = request.getUserPrincipal().getName();
		Optional <User> user = repo.findByUsername(username);
		//Optional <User> user2 = repo.findByUsername(usernm);
		//se declaran dos listas de donde se va a sacar la lista de todas las opiniones que el usuario ha realizado
		//y la segunda lista para recoger el id del conductor
		List<User> l = new ArrayList<User>();
		List<Trip> t = new ArrayList<Trip>();
		//Para opinar sobre un pasajero en concreto y que no imprima la lista entera le pasamos
				//el nombre de ese pasajero en vez de la id del viaje para opinar sobre él
				//Para opinar sobre un conductor con la id del viaje es suficiente porque solo hay un conductor.
		Optional<User> user2 = repo.findByUsername(us);
		
		model.addAttribute("name", user.get().getUsername());
		model.addAttribute("us2", user2.get().getUsername());
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
	
	@RequestMapping("/accionOpinarPasajero/{us}")
	public String accionOpinarp(Model model, @RequestParam String text ,
			@PathVariable String us, HttpServletRequest request/*, @PathVariable long id2*/) {
		String username = request.getUserPrincipal().getName();
		Optional <User> user = repo.findByUsername(username);
		
		//Para opinar sobre un pasajero en concreto y que no imprima la lista entera le pasamos
		//el nombre de ese pasajero en vez de la id del viaje para opinar sobre él
		//Para opinar sobre un conductor con la id del viaje es suficiente porque solo hay un conductor.
		Optional<User> pasajero = repo.findByUsername(us);
		Opinions o =  new Opinions(text,user.get(), pasajero.get(), user.get().getUsername(), pasajero.get().getUsername());
		user.get().addOpinion(o);
		
		
		repoOpinion.save(o);
	
		
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
		model.addAttribute("o", "");
		model.addAttribute("d", "");
		model.addAttribute("f", "");
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
	public String comprar(Model model, @RequestParam long id, HttpServletRequest request, HttpServletResponse response) throws Exception { //le llega el id del viaje de un formulario invisible en html
		
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
		
		String destiny = user.get().getMail();
		String subject = "Resguardo viaje reservado";
		String body = "Has reservado sitio en un viaje con destino " + t.get().getDest() + " con salida desde " + t.get().getOr()
				+ " el día " + t.get().getDate() + "\n" + "Contacto del conductor: \n"
				+ "Nombre de usuario: " + t.get().GetConductor().getUsername() + "\n" + "Correo: " +t.get().GetConductor().getMail();
		
		email.sendMail(destiny, subject, body);
		
		model.addAttribute("searched", false);
		model.addAttribute("error", "Reservado con éxito tu viaje:");
		model.addAttribute("id", id);
		model.addAttribute("o", t.get().getOr());
		model.addAttribute("d", t.get().getDest());
		model.addAttribute("f", t.get().getDate());
		//pdfService.generatePDF(response, t.get().getOr(), t.get().getDest(), t.get().getDate(), username);
		//En el hueco de error muestra que la reserva fue bien
		return "descargaBillete";
		
	}
	
	@PostMapping("/accionReserva/descarga") //metodo que se gestiona al reservar un viaje
	public String comprar2(Model model, @RequestParam long id, HttpServletRequest request, HttpServletResponse response) throws Exception { //le llega el id del viaje de un formulario invisible en html
		
		Optional<Trip> t = repoTrip.findById(id); //recupera el viaje reservado

		String username = request.getUserPrincipal().getName();
		Optional <User> user = repo.findByUsername(username);
		
		
	


		
		model.addAttribute("searched", false);
		model.addAttribute("error", "Reservado con éxito tu viaje:");
		model.addAttribute("o", t.get().getOr());
		model.addAttribute("id", id);
		model.addAttribute("d", t.get().getDest());
		model.addAttribute("f", t.get().getDate());
		pdfService.generatePDF(response, t.get().getOr(), t.get().getDest(), t.get().getDate(), username);
		//En el hueco de error muestra que la reserva fue bien
		return "descargaBillete";
		
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
	
	@GetMapping("/recuperarContra")
	public String recuperarContra() {
		return "recoverCode";
	}
	
	@PostMapping("/recuperarCodigo")
	public String correoCodigo(Model model, @RequestParam String mail) {
		
		Optional<User> user = repo.findByMail(mail);
		String destiny = user.get().getMail();
		String subject = "Recuperar Contraseña";
		String body = "Tu código de recuperación es: " + user.get().getCodeRec();
		
		email.sendMail(destiny, subject, body);
		model.addAttribute("id", user.get().getId());
		return "setCode";
	}
	
	@PostMapping("/introducirContra/{id}")
	public String introducirContra(Model model, @RequestParam String codeRec, @PathVariable long id) {
		
		int code = Integer.parseInt(codeRec);
		int codeNew = (int) (Math.random() * 9998 + 1);
		
		Optional<User> user = repo.findById(id);
		
		if(user != null && user.get().getCodeRec()==code) {
			user.get().setCodeRec(codeNew);
			repo.save(user.get());
			model.addAttribute("id", user.get().getId());
			return "setPassword";
		}
		else {
			model.addAttribute("code", true);
			return "index";
		}
	}
	
	@PostMapping("/cambiarContra/{id}")
	public String cambiarContra(Model model, @RequestParam String password, @PathVariable long id) {
		
		Optional<User> user = repo.findById(id);
		user.get().setPassword(passwordEncoder.encode(password));
		repo.save(user.get());
		model.addAttribute("code", false);
		return "index";
	}
}
