package com.santander.crm.sinergia.response;

import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.santander.crm.sinergia.entity.Prospecto;

public class ConsultaProspectosRes {

	private List<Prospecto> prospectos;

	private Long total;

	private Long convertidos;

	@JsonIgnore
	private HttpStatus httpStatus;

	@JsonIgnore
	private String message;

	public List<Prospecto> getProspectos() {
		return prospectos;
	}

	public void setProspectos(List<Prospecto> prospectos) {
		this.prospectos = prospectos;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public Long getConvertidos() {
		return convertidos;
	}

	public void setConvertidos(Long convertidos) {
		this.convertidos = convertidos;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
