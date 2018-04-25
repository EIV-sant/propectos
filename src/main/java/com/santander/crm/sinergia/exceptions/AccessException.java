package com.santander.crm.sinergia.exceptions;

public class AccessException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 909680457695257236L;
	
	private String error;

	public AccessException() {
		super();
	}

	public AccessException(String message) {
		super(message);
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

}
