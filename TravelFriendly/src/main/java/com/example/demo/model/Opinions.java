package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import java.util.List;


@Entity
public class Opinions {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id; 
	private String text;
	private User orig, dest;
	
	protected Opinions(){}
	
	public Opinions(String text, User orig, User dest) {
		this.text=text;
		this.orig=orig;
		this.dest=dest;
	}
	
	public long getId() {
		return id;
	}
	
	public String getText() {
		return text;
	}
	
	public void setOpinion(String text) {
		this.text=text;
	}
	
	
	
	
}
