package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Trip {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id; 
	
	private String origin; //origen
	private String destiny; //destino
	private String date; //fecha
	private int sites; //plazas disponibles
	private boolean stops; //viaje directo o no
	private String info; //info relevante
	
	protected Trip() {}
	
	public Trip(String or, String dest, String date, 
					int sites, boolean stops, String info) {
		this.origin = or;
		this.destiny = dest;
		this.date = date;
		this.sites = sites;
		this.stops = stops;
		this.info = info;
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
	
	public boolean getStops() {
		return this.stops;
	}
	
	public String getInfo() {
		return this.info;
	}
}
