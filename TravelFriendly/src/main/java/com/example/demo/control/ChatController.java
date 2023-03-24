package com.example.demo.control;

import java.security.Principal;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.Chat;
import com.example.demo.model.Message;
import com.example.demo.model.User;
import com.example.demo.repository.BookingRepository;
import com.example.demo.repository.ChatRepository;
import com.example.demo.repository.OpinionsRepository;
import com.example.demo.repository.TripRepository;
import com.example.demo.repository.UserRepository;


@Controller
public class ChatController {

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



	@RequestMapping("/chat")
	public String Chat(Model model, HttpServletRequest request) {

		Principal principal = request.getUserPrincipal();

		Optional<User> user = repo.findByUsername(principal.getName());

		model.addAttribute("chats", user.get().getChats()); 
		return "chat";
	}

	@GetMapping("/chat/{id}")
	public String Chats(Model model, @PathVariable long id, HttpServletRequest request) {

		Principal principal = request.getUserPrincipal();

		Optional<User> user = repo.findByUsername(principal.getName());

		model.addAttribute("chats", user.get().getChats()); 
		Optional<Chat> chat = repoChat.findById(id);
		model.addAttribute("chat", chat.get());
		return "chats";
	}

	@PostMapping("/chat/{id}/SaveChats")
	public String MensajeS(Model model, Message m, @PathVariable long id, HttpServletRequest request) {

		Principal principal = request.getUserPrincipal();
		Optional<User> user = repo.findByUsername(principal.getName());

		model.addAttribute("chats", user.get().getChats());
		Optional<Chat> chat = repoChat.findById(id);

		m.setDescripcion(user.get().getUsername());
		chat.get().getMensg().add(m);

		repoChat.save(chat.get());
		return "redirect:/chat/" + id;
	}
	// Si creo una entidad mensaje y esta va a contener el id del chat al que
	// pertenecen. De esta forma podre buscar
	// todos los mensajes pertenecientes a un chat en concreto atraves de su
	// repositorio
	// ESta sera la unica forma e la cual podre listar (ya que no funcionan las
	// listas) todos los mensajes pertenecientes a un chat
	
	
}
