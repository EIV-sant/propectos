package com.santander.crm.sinergia.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
	
	@Column(name = "NUM_CC")
	private Integer idSucursal;
	
	@JsonIgnore
	@ManyToOne(optional=true)
	@JoinColumn(name = "ID_TPO_EJV")
	private Tipo tipo;
	
	@JsonIgnore
	@ManyToOne(optional=true)
	@JoinColumn(name = "ID_TPO_BCA")
	private Banca banca;

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

	public Integer getIdSucursal() {
		return idSucursal;
	}

	public void setIdSucursal(Integer idSucursal) {
		this.idSucursal = idSucursal;
	}

	public Banca getBanca() {
		return banca;
	}

	public void setBanca(Banca banca) {
		this.banca = banca;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

}
