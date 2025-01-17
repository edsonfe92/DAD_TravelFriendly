package com.example.demo.model;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import com.example.demo.model.Chat;


@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	
	private Long id; 
	private String username;
	private String password;
	private String mail;
	private int codeRec;
	
	@ElementCollection(fetch = FetchType.EAGER)
	private List<String> roles;
	
	// private List<Integer> valoraciones;

	@OneToMany (mappedBy="conductor")//He tocado esta
 	private List<Trip> Ptrip= new ArrayList<Trip>();
	@OneToMany(mappedBy="user")//He tocado esta
	private List<Booking> Btrip= new ArrayList<Booking>();
	
	@ManyToMany //Un usuario tiene multiples chats y un chat 2 usuarios (multiples tambien)
	private List<Chat> chats;
	
	
	public List<Chat> getChats() {
		return chats;
	}

	public void setChats(List<Chat> chats) {
		this.chats = chats;
	}
	

	//Lista de opiniones que tiene la gente del usuario
	@OneToMany 
	private List <Opinions> opinions = new ArrayList<Opinions>();;
	public Long getId() {
		return id;
	}
	//protected User() {} //Necesario para la BBDD
	
	public User() {}
	public User(String username, String password, String mail, String... roles) {
		this.username = username;
		this.password = password;
		this.mail = mail;
		this.codeRec = (int) (Math.random() * 9998 + 1);
		this.roles = List.of(roles);
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public int getCodeRec() {
		return this.codeRec;
	}
	public void setCodeRec(int code) {
		this.codeRec = code;
	}
	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public void addTripP(Trip p) {
		Ptrip.add(p);
	}
	
	public void addTripB(Booking b) {
		Btrip.add(b);
	}
	
	public void addOpinion(Opinions o) {
		opinions.add(o);
	}
	
	
	public List<Trip> getPtrip() {
		return Ptrip;
	}
	
	public List<Booking> getBtrip() {
		return Btrip;
	}
	
	public List<Opinions> getOpinions() {
		return opinions;
	}
	
	public String getMail() {
		return this.mail;
	}
	
	
	
}
