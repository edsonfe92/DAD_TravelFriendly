package com.example.demo.control;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Booking;
import com.example.demo.model.Chat;
import com.example.demo.model.Comprobacion;
import com.example.demo.model.ContainerHttp;
import com.example.demo.model.Trip;
import com.example.demo.model.User;
import com.example.demo.rabbitmq.Publisher;
import com.example.demo.repository.BookingRepository;
import com.example.demo.repository.ChatRepository;
import com.example.demo.repository.OpinionsRepository;
import com.example.demo.repository.TripRepository;
import com.example.demo.repository.UserRepository;

import com.example.demo.service.PDFExportController;



@Controller
public class TripController {

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
	private PDFExportController pdfService;

	
	@Autowired
	private Publisher pub;

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
	//desalojamos caché porque cambian viajes
	@PostMapping("/accionPublicar") // metodo que gestiona cuando se publica un viaje
	public String publicar(Model model, @RequestParam String origin, @RequestParam String destiny,
			@RequestParam String date, @RequestParam int sites, @RequestParam int stops, @RequestParam String info,
			HttpServletRequest request) {

		String username = request.getUserPrincipal().getName();
		Optional<User> user = repo.findByUsername(username);

		Trip t = new Trip(origin, destiny, date, sites, stops, info); // crear un viaje con la info que ha introducido
																		// el usuario
		t.SetConductor(user.get()); // poner de conductor al usuario que ha publicado el viaje

		user.get().addTripP(t); // se añade a la lista de viajes publicados en el usuario
		
		//SE DESALOJA CACHE AL PUBLICAR VIAJES, SE INVALIDA CAMBIA LA INFO
		//CACHEEVICT INIT
		repoTrip.save(t); // se guarda el viaje en la BD
		//CACHEEVICT END
		repo.save(user.get()); // se actualiza el usuario en la BD
		return "publish";

	}

	@RequestMapping("/accionBuscador") // metodo que gestiona cuando se usa el buscador
	public String buscar(Model model, @RequestParam String origin, @RequestParam String destiny,
			@RequestParam String date, HttpServletRequest request) {

		List<Optional<Trip>> tripDate = repoTrip.findByDate(date); // consultamos a la BD por fecha (así evitamos
																	// problemas de mayúsculas)
		List<Comprobacion> cTrip = new ArrayList<Comprobacion>(); // acordarse de los import

		for (int i = 0; i < tripDate.size(); i++) { // recorrer lista por fecha
			if ((tripDate.get(i).get().getOr().equalsIgnoreCase(origin)) // si tiene el mismo origen
					&& (tripDate.get(i).get().getDest().equalsIgnoreCase(destiny)) // el mismo destino
					/* &&(tripDate.get(i).get().getConductorId()!=usuarioActual.getId()) */ // no esta publicado por el
																							// usuario
					&& (tripDate.get(i).get().getSites() > 0)) { // y tiene sitios libres

				cTrip.add(new Comprobacion(tripDate.get(i).get())); // añadir a la lista de salida ese viaje
			}
		}

		String username;
		Optional<User> user;

		try {
			username = request.getUserPrincipal().getName();
			user = repo.findByUsername(username);

			for (int i = 0; i < cTrip.size(); i++) {
				if (cTrip.get(i).getTrip().getConductorId() == user.get().getId()) {
					cTrip.get(i).setComp(true);
				}
			}
		} catch (Exception e) {
			username = null;
		}
		model.addAttribute("o", "");
		model.addAttribute("d", "");
		model.addAttribute("f", "");
		if (cTrip.size() > 0) { // si la lista de salida tiene algun viaje
			if (username != null) {
				model.addAttribute("searched", true);
				model.addAttribute("resultados", cTrip); // se muestran los viajes
				model.addAttribute("visitor", false); // no es invitado
			} else {
				model.addAttribute("searched", true);
				model.addAttribute("resultados", cTrip); // se muestran los viajes
				model.addAttribute("visitor", true); // es un invitado
			}
		} else {// si no
			model.addAttribute("searched", false);
			model.addAttribute("error", "No se han encontrado resultados"); // se devuelve mensaje de error
		}

		return "search";
	}
	
	
	//cachea todos los viajes

	/*@GetMapping(value= "/tusViajes")
	public List<Trip> getTrips(){
		return 
	}*/
	
	
	
	// Guarda el resultado del método en la caché que se haya
		//configurado para la aplicación, asociado a los valores de
		//los parámetros
	
	
	
	
	//Guarda el resultado del metodo en la cache de viajes;
	//un determinado usuario y todos los viajes
	//name
	//PTrip
	//BTrip
	
	@Cacheable("viajes")
	@GetMapping("/tusViajes")
	public String tusViajes(Model model, HttpServletRequest request) {

		// recogemos el nombre del usuario real a través del srrvicio http
		// buscamos su nombre en el repositorio
		String username = request.getUserPrincipal().getName();
		Optional<User> user = repo.findByUsername(username);
		List<Trip> t = new ArrayList<Trip>();
		
		for (int i = 0; i < user.get().getBtrip().size(); i++) {
			//Encuentra el viaje reservado(cacheable guarda valor en caché) a raiz del repositorio
			//cacheable
			
			//repoTrip.findByConductor_Id(user.get().getBtrip().get(i).getTrip().getConductorId());
			//end cacheable
			t.add(user.get().getBtrip().get(i).getTrip());
			
		}
		//cacheable
		//encuentra el viaje publicado
		//repoTrip.findByConductor_Id(user.get().getId());
		//end cacheable
		//repoTrip.findAll();
		model.addAttribute("name", user.get().getUsername());
		model.addAttribute("PTrip", user.get().getPtrip());
		model.addAttribute("BTrip", t);

		return "yourTravel";
	}
	
	//desalojamos caché porque cambian viajes
	@PostMapping("/accionReserva") // metodo que se gestiona al reservar un viaje; ACTÚA AQUÍ LA CACHÉ SE DESALOJA
	public String comprar(Model model, @RequestParam long id, HttpServletRequest request, HttpServletResponse response)
			throws Exception { // le llega el id del viaje de un formulario invisible en html

		Optional<Trip> t = repoTrip.findById(id); // recupera el viaje reservado

		String username = request.getUserPrincipal().getName();
		Optional<User> user = repo.findByUsername(username);

		t.get().buyTrip(); // resta un hueco libre al viaje
		t.get().SetUsersinTrip(user.get()); // en la lista de usuarios del viaje mete al usuario
		
		//SAVE HA SIDO CAMBIADO, CADA VEZ QUE SE INVOCA AL METODO 
		//SAVE DEL REPOSITORIO DE VIAJES ESTAREMOS DESALOJANDO LA CACHÉ!!!
		//CACHEEVICT 
		repoTrip.save(t.get()); // volvemos a guardar el viaje en la BD
		
		
		Booking b = new Booking(user.get(), t.get()); // creamos una reserva a nombre del usuario y se le mete el viaje
		user.get().addTripB(b); // se añade la reserva al usuario
		// t.get().AddPasajeros(user.get()); //se añade al viaje un pasajero xd
		repo.save(user.get()); // actualizamos el usuario en la BD

		repoBook.save(b); // guardar reserva en la BD

		Chat c = new Chat(); // creamos el chat con el conductor y el usuario

		c.setDescripcion(t.get().getOr(), t.get().getDest(), t.get().GetConductor().getUsername(),
				user.get().getUsername()); // le añadimos la descripción

		repoChat.save(c); // se guarda el chat en la BD

		t.get().GetConductor().getChats().add(c);
		user.get().getChats().add(c);
		repo.save(user.get());
		repo.save(t.get().GetConductor());

		String destiny = user.get().getMail();
		String subject = "Resguardo viaje reservado";
		String body = "Has reservado sitio en un viaje con destino " + t.get().getDest() + " con salida desde "
				+ t.get().getOr() + " el día " + t.get().getDate() + "\n" + "Contacto del conductor: \n"
				+ "Nombre de usuario: " + t.get().GetConductor().getUsername() + "\n" + "Correo: "
				+ t.get().GetConductor().getMail();

		pub.sendMailData(destiny, subject, body);
		//email.sendMail(destiny, subject, body);

		model.addAttribute("searched", false);
		model.addAttribute("error", "Reservado con éxito tu viaje:");
		model.addAttribute("id", id);
		model.addAttribute("o", t.get().getOr());
		model.addAttribute("d", t.get().getDest());
		model.addAttribute("f", t.get().getDate());
		
		return "descargaBillete";

	}

	@PostMapping("/accionReserva/descarga") // metodo que se gestiona al reservar un viaje
	public String comprar2(Model model, @RequestParam long id, HttpServletRequest request, HttpServletResponse response)
			throws Exception { // le llega el id del viaje de un formulario invisible en html

		Optional<Trip> t = repoTrip.findById(id); // recupera el viaje reservado

		String username = request.getUserPrincipal().getName();
		Optional<User> user = repo.findByUsername(username);
		ContainerHttp c = new ContainerHttp(response);
		
		
		model.addAttribute("searched", false);
		model.addAttribute("error", "Reservado con éxito tu viaje:");
		model.addAttribute("o", t.get().getOr());
		model.addAttribute("id", id);
		model.addAttribute("d", t.get().getDest());
		model.addAttribute("f", t.get().getDate());
		//pub.sendPDF(t.get().getOr(), t.get().getDest(), t.get().getDate(), username);
		pdfService.generatePDF(response, t.get().getOr(), t.get().getDest(), t.get().getDate(), username);
		// En el hueco de error muestra que la reserva fue bien
		return "descargaBillete";

	}

}
