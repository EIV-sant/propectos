package com.santander.crm.sinergia.response;

import org.springframework.http.HttpStatus;

import com.santander.crm.sinergia.entity.Prospecto;

public class GenericProspectoRes {
	
	private Prospecto prospecto;
	
	private String message;

	private HttpStatus httpStatus;

	public Prospecto getProspecto() {
		return prospecto;
	}

	public void setProspecto(Prospecto prospecto) {
		this.prospecto = prospecto;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}
	
	

}
