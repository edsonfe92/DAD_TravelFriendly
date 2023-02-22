package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Booking {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id; 
	
	@ManyToOne
	private User user;
	
	@ManyToOne
	private Trip trip;
	
	
	protected Booking(){}
	
	public Booking(User user, Trip trip) {
		this.user=user;
		this.trip = trip;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getId() {
		return this.id;
	}
	
	public Trip getTrip() {
		return this.trip;
	}
	
	public void setTrip(Trip t) {
		this.trip = t;
	}
	
	
}
