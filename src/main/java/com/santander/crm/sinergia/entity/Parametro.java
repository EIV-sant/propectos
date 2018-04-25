package com.santander.crm.sinergia.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SIN_MX_CAT_PARAM_GRALES")
public class Parametro {

	@Id
	@Column(name = "NOM_PAR")
	private String nombre;

	@Column(name = "VAL_PAR")
	private String valor;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

}
