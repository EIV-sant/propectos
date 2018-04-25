package com.santander.crm.sinergia.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SIN_MX_CAT_EJEC")
public class Ejecutivo {

	@Id
	@Column(name = "TXT_OFI_ACT")
	private String ofiAct;

	@Column(name = "TXT_NOM_EJV")
	private String nombre;

	@Column(name = "TXT_EXP_CMP")
	private String expediente;

	public String getOfiAct() {
		return ofiAct;
	}

	public void setOfiAct(String ofiAct) {
		this.ofiAct = ofiAct;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getExpediente() {
		return expediente;
	}

	public void setExpediente(String expediente) {
		this.expediente = expediente;
	}

}
