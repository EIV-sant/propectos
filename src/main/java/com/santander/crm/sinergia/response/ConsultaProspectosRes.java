package com.santander.crm.sinergia.response;

import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.ImmutableList;
import com.santander.crm.sinergia.filter.ProspectoSeguimiento;

public class ConsultaProspectosRes {

	private List<ProspectoSeguimiento> prospectos;

	private Long total;

	private Long convertidos;
	
	private Integer tpoBcaEjec;

	@JsonIgnore
	private HttpStatus httpStatus;

	@JsonIgnore
	private String message;

	public List<ProspectoSeguimiento> getProspectos() {
		List<ProspectoSeguimiento> listAux = prospectos;
		return listAux;
	}

	public void setProspectos(List<ProspectoSeguimiento> prospectos) {
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

	public Integer getTpoBcaEjec() {
		return tpoBcaEjec;
	}

	public void setTpoBcaEjec(Integer tpoBcaEjec) {
		this.tpoBcaEjec = tpoBcaEjec;
	}

}
