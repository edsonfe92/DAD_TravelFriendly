package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;



@Entity
public class Opinions {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id; 
	private String text="";
	@OneToOne
	private User orig, dest;
	private String nameOrig, nameDest;
	protected Opinions(){}
	
	public Opinions(String text, User orig, User dest, String nameOrig, String nameDest) {
		this.text=text;
		this.orig=orig;
		this.dest=dest;
		this.nameOrig=nameOrig;
		this.nameDest=nameDest;
	}
	
	public long getId() {
		return id;
	}
	
	public String getText() {
		return text;
	}
	
	public User getOrigin() {
		return this.orig;
	}
	
	public User getDestiny() {
		return this.dest;
	}
	public void setOpinion(String text) {
		this.text=text;
	}
}
	
	
