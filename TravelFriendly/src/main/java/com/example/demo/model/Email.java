package com.example.demo.model;

public class Email {
	private String origin;
	private String destiny;
	private String header;
	private String body;
	
	public Email(String origin, String destiny, String header, String body) {
		this.origin = origin;
		this.destiny = destiny;
		this.header = header;
		this.body = body;
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
	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
		this.header = header;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	
}
