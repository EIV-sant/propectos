package com.santander.crm.sinergia.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "SIN_MX_MAE_NC")
public class Prospecto {

	@Id
	@Column(name = "ID_NC")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CUST_SEQ_PROSP")
	@SequenceGenerator(sequenceName = "SIN_MX_MAE_NC_SEQ", allocationSize = 1, name = "CUST_SEQ_PROSP")
	private Integer id;

	@NotNull
	@Column(name = "TXT_NOM_RAZ_SOC")
	private String nomRazSoc;

	@Column(name = "FCH_ALTA")
	private Date fechaAlta;

	// SECCION POR CAMBIAR
	@NotNull
	@Column(name = "SIN_MX_CAT_ACT_ID_ACT")
	private Integer idActividad;

	@NotNull
	@Column(name = "SIN_MX_CAT_LOC_ID_LOC")
	private Integer idLocalidad;

	@NotNull
	@Column(name = "SIN_MX_CAT_TPO_BCA_ID_TPO_BCA")
	private Integer idBanca;

	@NotNull
	@Column(name = "SIN_MX_CAT_ESTATUS_ID_ESTATUS")
	private Integer idEstatus;

	@NotNull
	@Column(name = "TXT_OFI")
	private String oficina;

	@NotNull
	@Column(name = "SIN_MX_CAT_TPO_TEL_ID_TPO_TEL")
	private Integer tipoTelefono;

	// FIN SECCION POR CAMBIAR

	@NotNull
	@Column(name = "TXT_GENERO")
	private String genero; // FEMENINO,MASCULINO

	@NotNull
	@Column(name = "TXT_PER_FIS")
	private String personaFisica; // PERSONA FISICA ACTIVIDAD EMPRESARIAL,PERSONA FISICA

	@NotNull
	@Column(name = "TXT_EDO_CIV")
	private String estadoCivil; // CASADO(A),DIVORCIADO(A),SOLTERO(A),VIUDO(A)

	@NotNull
	@Column(name = "SIN_MX_CAT_EJEC_TXT_EXP_CMP")
	private String expEjecutivo;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNomRazSoc() {
		return nomRazSoc;
	}

	public void setNomRazSoc(String nomRazSoc) {
		this.nomRazSoc = nomRazSoc;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	@PrePersist
	public void setFechaAlta() {
		this.fechaAlta = new Date();
	}

	public Integer getIdActividad() {
		return idActividad;
	}

	public void setIdActividad(Integer idActividad) {
		this.idActividad = idActividad;
	}

	public Integer getIdLocalidad() {
		return idLocalidad;
	}

	public void setIdLocalidad(Integer idLocalidad) {
		this.idLocalidad = idLocalidad;
	}

	public Integer getIdBanca() {
		return idBanca;
	}

	public void setIdBanca(Integer idBanca) {
		this.idBanca = idBanca;
	}

	public Integer getIdEstatus() {
		return idEstatus;
	}

	public void setIdEstatus(Integer idEstatus) {
		this.idEstatus = idEstatus;
	}

	public String getOficina() {
		return oficina;
	}

	public void setOficina(String oficina) {
		this.oficina = oficina;
	}

	public Integer getTipoTelefono() {
		return tipoTelefono;
	}

	public void setTipoTelefono(Integer tipoTelefono) {
		this.tipoTelefono = tipoTelefono;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getPersonaFisica() {
		return personaFisica;
	}

	public void setPersonaFisica(String personaFisica) {
		this.personaFisica = personaFisica;
	}

	public String getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(String estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public String getExpEjecutivo() {
		return expEjecutivo;
	}

	public void setExpEjecutivo(String expEjecutivo) {
		this.expEjecutivo = expEjecutivo;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	// refactor

}
