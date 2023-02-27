package com.example.demo.model;

public class Comprobacion {

	private boolean comp;
	private Trip trip;
	
	public Comprobacion(Trip t) {
		this.comp = false;
		this.trip = t;
	}
	
	public boolean isOwner() {
		return comp;
	}
	public void setComp(boolean comp) {
		this.comp = comp;
	}
	public Trip getTrip() {
		return trip;
	}
	public void setTrip(Trip t) {
		this.trip = t;
	}	
	
}
