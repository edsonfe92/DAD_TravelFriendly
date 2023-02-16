package com.example.demo.model;
import java.util.List;
import javax.persistence.*;


@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id; 
	
	private String username;
	private String password;
	// private List<Integer> valoraciones;


	
	public long getId() {
		return id;
	}
	protected User() {} //Necesario para la BBDD
	
	public User(String username, String pass) {
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
	
	
	
}
