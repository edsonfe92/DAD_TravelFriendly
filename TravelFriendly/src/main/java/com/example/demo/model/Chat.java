package com.example.demo.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Chat {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id; 
	
	@OneToMany(cascade=CascadeType.ALL)
	private List<message> mensg;
	
	public List<message> getMensg() {
		return mensg;
	}

	public void setMensg(List<message> mensg) {
		this.mensg = mensg;
	}

	public String mensajes ="";
	
	@ManyToOne
	private User conductor;
	@ManyToOne
	private User pasajero;
	
	private String descripcion=""; 
	
	public Chat() {}
	
	public Chat(User conductor, User pasajero) {
					this.conductor=conductor;
					this.pasajero= pasajero;
	}
	
	public void addMensaje(String m, User u) {
		this.mensajes += u.getUsername()+" : "+m+".\r\n";
	}
	
	public void setDescripcion(String origen, String destino, String usuario ) {
		descripcion = origen+" - " + destino+" - "+usuario;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getMensajes() {
		return mensajes;
	}

	public void setMensajes(String mensajes) {
		this.mensajes = mensajes;
	}

	public User getPasajero() {
		return pasajero;
	}

	public void setPasajero(User pasajero) {
		this.pasajero = pasajero;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
}
