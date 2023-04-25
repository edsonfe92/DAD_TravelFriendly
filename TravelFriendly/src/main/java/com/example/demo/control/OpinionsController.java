package com.example.demo.control;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Opinions;
import com.example.demo.model.Trip;
import com.example.demo.model.User;
import com.example.demo.repository.BookingRepository;
import com.example.demo.repository.ChatRepository;
import com.example.demo.repository.OpinionsRepository;
import com.example.demo.repository.TripRepository;
import com.example.demo.repository.UserRepository;


@Controller
public class OpinionsController {

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

	@Cacheable("opiniones")
	@GetMapping("/perfil")
	public String Perfil(Model model, HttpServletRequest request) {

		// recogemos el nombre del usuario real a través del srrvicio http
		// buscamos su nombre en el repositorio
		String username = request.getUserPrincipal().getName();
		Optional<User> user = repo.findByUsername(username);
		List<Opinions> o = new ArrayList<Opinions>();

		for (int i = 0; i < user.get().getOpinions().size(); i++) {

			o.add(user.get().getOpinions().get(i));

		}

		model.addAttribute("opin", o);

		model.addAttribute("name", user.get().getUsername());

		return "profile";

	}

	

	@GetMapping("/opinarConductor/{id}")
	public String opinarC(Model model, @PathVariable long id, HttpServletRequest request) {
		// recogemos el nombre del usuario real a través del srrvicio http
		// buscamos su nombre en el repositorio
		String username = request.getUserPrincipal().getName();
		Optional<User> user = repo.findByUsername(username);

		// se declaran dos listas de donde se va a sacar la lista de todas las opiniones
		// que el usuario ha realizado
		// y la segunda lista para recoger el id del conductor
		List<User> l = new ArrayList<User>();
		List<Trip> t = new ArrayList<Trip>();

		for (int i = 0; i < user.get().getBtrip().size(); i++) {
			t.add(user.get().getBtrip().get(i).getTrip());
			repoTrip.save(user.get().getBtrip().get(i).getTrip());
		}

		for (int i = 0; i < user.get().getBtrip().size(); i++) {
			l.add(user.get().getBtrip().get(i).getTrip().GetConductor());
			repo.save(user.get().getBtrip().get(i).getTrip().GetConductor());
		}
		// se recoge el del que se va a opinar viaje
		Optional<Trip> t2 = repoTrip.findById(id);
		// se recoge el conductor de dicho viaje
		Optional<User> l2 = repo.findById(t2.get().GetConductor().getId());

		model.addAttribute("name", user.get().getUsername());
		model.addAttribute("nameConductor", t2.get().GetConductor().getUsername());
		model.addAttribute("IdConductor", l);

		return "opinion";
	}

	@GetMapping("/opinarPasajero/{us}")
	public String opinarP(Model model, @PathVariable String us,
			HttpServletRequest request ) {

		// recogemos el nombre del usuario real a través del srrvicio http
		// buscamos su nombre en el repositorio

		String username = request.getUserPrincipal().getName();
		Optional<User> user = repo.findByUsername(username);
		// se declaran dos listas de donde se va a sacar la lista de todas las opiniones
		// que el usuario ha realizado
		// y la segunda lista para recoger el id del conductor
		List<User> l = new ArrayList<User>();
		List<Trip> t = new ArrayList<Trip>();
		// Para opinar sobre un pasajero en concreto y que no imprima la lista entera le
		// pasamos
		// el nombre de ese pasajero en vez de la id del viaje para opinar sobre él
		// Para opinar sobre un conductor con la id del viaje es suficiente porque solo
		// hay un conductor.
		Optional<User> user2 = repo.findByUsername(us);

		model.addAttribute("name", user.get().getUsername());
		model.addAttribute("us2", user2.get().getUsername());
		model.addAttribute("IdPasajero", l);

		return "opinionPasajero";
	}
	
	@RequestMapping("/accionOpinarConductor/{id}")
	public String accionOpinar(Model model, @RequestParam String text, @PathVariable long id,
			HttpServletRequest request) {
		String username = request.getUserPrincipal().getName();
		Optional<User> user = repo.findByUsername(username);

		Optional<Trip> t = repoTrip.findById(id);

		Opinions o2 = new Opinions(text, user.get(), t.get().GetConductor(), user.get().getUsername(),
				t.get().GetConductor().getUsername());
		user.get().addOpinion(o2);
		//DESALOJA CACHÉ DE OPINIONES:
		repoOpinion.save(o2);

		model.addAttribute("name", user.get().getUsername());

		return "main";
	}

	@RequestMapping("/accionOpinarPasajero/{us}")
	public String accionOpinarp(Model model, @RequestParam String text, @PathVariable String us,
			HttpServletRequest request) {
		String username = request.getUserPrincipal().getName();
		Optional<User> user = repo.findByUsername(username);

		// Para opinar sobre un pasajero en concreto y que no imprima la lista entera le
		// pasamos
		// el nombre de ese pasajero en vez de la id del viaje para opinar sobre él
		// Para opinar sobre un conductor con la id del viaje es suficiente porque solo
		// hay un conductor.
		Optional<User> pasajero = repo.findByUsername(us);
		Opinions o = new Opinions(text, user.get(), pasajero.get(), user.get().getUsername(),
				pasajero.get().getUsername());
		user.get().addOpinion(o);
		//DESALOJA CACHÉ DE OPINIONES:
		repoOpinion.save(o);

		model.addAttribute("name", user.get().getUsername());
		return "main";
	}

}
