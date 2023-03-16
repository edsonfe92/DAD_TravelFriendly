package com.example.demo.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Chat {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id; 
	
	@OneToMany(cascade=CascadeType.ALL)
	private List<Message> mensg;
	
	public List<Message> getMensg() {
		return mensg;
	}

	public void setMensg(List<Message> mensg) {
		this.mensg = mensg;
	}

	public String mensajes ="";
	
	@ManyToMany(mappedBy="chats")
	private List<User> users; 
	
	
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	private String descripcion=""; 
	
	public Chat() {}
	
	public Chat(User conductor, User pasajero) {
					users.add(conductor);
					users.add(pasajero);
	}
	
	public void addMensaje(String m, User u) {
		this.mensajes += u.getUsername()+" : "+m+".\r\n";
	}
	
	public void setDescripcion(String origen, String destino, String conductor, String usuario ) {
		descripcion = origen+" - " + destino+" - "+conductor+" - "+usuario;
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



	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
}
