package com.example.demo.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Trip {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id; 
	
	private String origin; //origen
	private String destiny; //destino
	private String date; //fecha
	private int sites; //plazas disponibles
	private int stops; //número de paradas
	private String info; //info relevante
	
	@ManyToOne //En user se enlaza con PTrip
	private User conductor;
	
	private String nombreconductor=""; // Para el chat 
	
	@ManyToMany
 	private List<User> use; //Usuarios que van a realizar el viaje //la primera posicion se va a corresponder a la del condunctor
	
	@OneToMany
	private List<Chat> chats;
	
	protected Trip() {}
	
	public Trip(String or, String dest, String date, 
					int sites, int stops, String info) {
		this.origin = or;
		this.destiny = dest;
		this.date = date;
		this.sites = sites;
		this.stops = stops;
		this.info = info;
		
	}
	
	public void AddChat(Chat c) {
		chats.add(c);
	}
	
	public long getId() {
		return this.id;
	}
	
	public String getOr() {
	return this.origin;
	}
	
	public String getDest() {
		return this.destiny;
	}
	
	public String getDate() {
		return this.date;
	}
	
	public int getSites() {
		return this.sites;
	}
	
	public int getStops() {
		return this.stops;
	}
	
	
	public String getInfo() {
		return this.info;
	}
	
	public void buyTrip() {
		this.sites--;
	}
	
	public User GetConductor() {
		return conductor;
	}
	public void SetConductor(User s) {
		this.conductor = s ; 
		nombreconductor = this.conductor.getUsername();
				
	}
	public void SetUsersinTrip(User s) {
		use.add(s);
	}
	
	public long getConductorId() {
		return this.conductor.getId();
	}
}
