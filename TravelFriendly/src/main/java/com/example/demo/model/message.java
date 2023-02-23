package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class message {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id; 
	
	private String body;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public message() {}
	public message(String mBody) {
		this.body = mBody;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
	public void setDescripcion(String conductor) {
		this.body = conductor+": "+this.body;
	}
	
	
}
