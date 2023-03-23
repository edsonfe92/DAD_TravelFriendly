package com.example.demo.model;

import java.io.Serializable;

import javax.servlet.http.HttpServletResponse;

public class ContainerHttp implements Serializable{
	private HttpServletResponse response;
	
	public ContainerHttp(HttpServletResponse response) {
		this.response=response;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}
}
