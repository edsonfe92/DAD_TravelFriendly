package com.example.demo.model;

public class PdfInternalService {

	private String origin;
	private String destiny;
	private String date;
	private String user;
	
	public PdfInternalService(String origin, String destiny, String date, String user ) {
		this.user=user;
		this.origin=origin;
		this.date=date;
		this.destiny=destiny;
	}
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getDestiny() {
		return destiny;
	}
	public void setDestiny(String destiny) {
		this.destiny = destiny;
	}
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
}


