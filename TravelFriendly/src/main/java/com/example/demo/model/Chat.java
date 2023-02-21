package com.example.demo.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Chat {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id; 
	

	
	public String mensajes;
	
	
	public Chat() {
					
	}
	
	/*public void addMensaje(String m) {
		mensajes.add(m);
	}*/
}
