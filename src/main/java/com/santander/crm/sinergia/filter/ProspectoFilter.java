package com.santander.crm.sinergia.filter;

import javax.validation.constraints.NotNull;

public class ProspectoFilter {

	@NotNull
	private Integer idEjecutivo;

	public Integer getIdEjecutivo() {
		return idEjecutivo;
	}

	public void setIdEjecutivo(Integer idEjecutivo) {
		this.idEjecutivo = idEjecutivo;
	}

}
