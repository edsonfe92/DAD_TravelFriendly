package com.example.demo.model;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import com.example.demo.model.Chat;





@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	
	private long id; 
	private String username;
	private String password;
	
	// private List<Integer> valoraciones;

	@OneToMany (mappedBy="conductor")//He tocado esta
 	private List<Trip> Ptrip= new ArrayList<Trip>();
	@OneToMany(mappedBy="user")//He tocado esta
	private List<Booking> Btrip= new ArrayList<Booking>();
	
	@OneToMany
	private List<Chat> chats;
	
	
	//Lista de opiniones que tiene la gente del usuario
	@OneToMany 
	private List <Opinions> opinions = new ArrayList<Opinions>();;
	public long getId() {
		return id;
	}
	//protected User() {} //Necesario para la BBDD
	
	public User() {}
	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}
	public void setId(long id) {
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
	
	
	
}
