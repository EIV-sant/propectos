package com.santander.crm.sinergia.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Email;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "SIN_MX_MAE_CONTACTO")
public class Contacto {

	@Id
	@Column(name = "ID_CONTACTO")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CUST_SEQ_CONT")
	@SequenceGenerator(sequenceName = "SIN_MX_MAE_CONTACTO_SEQ", allocationSize = 1, name = "CUST_SEQ_CONT")
	private Integer id;

	@Column(name = "TXT_CONTACTO")
	private String nombreContacto;

	@Column(name = "TXT_PUESTO")
	private String puesto;

	@Email
	@Column(name = "TXT_EMAIL")
	private String email;

	@Column(name = "TXT_TEL")
	private String telefono;
	
	@Column(name = "TXT_EXT")
	private String ext;

	@Column(name = "SIN_MX_CAT_TPO_TEL_ID_TPO_TEL")
	private Integer idTipoTelefono;

	// @JsonManagedReference
	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SIN_MX_MAE_NC_ID_NC")
	private Prospecto prospecto;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombreContacto() {
		return nombreContacto;
	}

	public void setNombreContacto(String nombreContacto) {
		this.nombreContacto = nombreContacto;
	}

	public String getPuesto() {
		return puesto;
	}

	public void setPuesto(String puesto) {
		this.puesto = puesto;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public Integer getIdTipoTelefono() {
		return idTipoTelefono;
	}

	public void setIdTipoTelefono(Integer idTipoTelefono) {
		this.idTipoTelefono = idTipoTelefono;
	}

	public Prospecto getProspecto() {
		return prospecto;
	}

	public void setProspecto(Prospecto prospecto) {
		this.prospecto = prospecto;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

}
