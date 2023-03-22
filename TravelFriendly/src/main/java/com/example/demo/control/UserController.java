package com.example.demo.control;

import java.security.Principal;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.User;
import com.example.demo.repository.BookingRepository;
import com.example.demo.repository.ChatRepository;
import com.example.demo.repository.OpinionsRepository;
import com.example.demo.repository.TripRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.EmailService;

@Controller 
public class UserController {

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
