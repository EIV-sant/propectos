package com.santander.crm.sinergia.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "SIN_MX_MAE_NC")
public class Prospecto {

	@Id
	@Column(name = "ID_NC")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CUST_SEQ_PROSP")
	@SequenceGenerator(sequenceName = "SIN_MX_MAE_NC_SEQ", allocationSize = 1, name = "CUST_SEQ_PROSP")
	private Integer id;

	// OBLIGATORIOS
	@NotEmpty
	@Column(name = "TXT_NOM_RAZ_SOC")
	private String nomRazSoc;

	@NotNull(message = "no puede ser nulo")
	@Column(name = "SIN_MX_CAT_TPO_BCA_ID_TPO_BCA")
	private Integer idBanca;

	@NotNull(message = "no puede ser nulo")
	@Column(name = "SIN_MX_CAT_TPO_TEL_ID_TPO_TEL")
	private Integer idTipoTelefono;

	// FIN OBLIGATORIOS

	// GENERADOS AUTOMATICAMENTE
	@Column(name = "FCH_ALTA")
	private Date fechaAlta;

	@Column(name = "FCH_UPDATE")
	private Date fechaActualizacion;

	@Column(name = "SIN_MX_CAT_ESTATUS_ID_ESTATUS")
	private Integer idEstatus;
	// FIN GENERADOS AUTOMATICAMENTE

	// INSERTADOS EN NEGOCIO
	/**
	 * Ofi act Ejecutivo que refiere
	 */
	@JsonIgnore
	@Column(name = "TXT_OFI")
	private String ofiReferente;

	@Column(name = "TXT_OFI_AGDO")
	private String ofiAsignado;
	// FIN INSERTADOS EN NEGOCIO

	// OPCIONALES

	@Column(name = "SIN_MX_CAT_LOC_ID_LOC")
	private Integer idLocalidad;
	
	/**
	 * Posibles valores: 1. PERSONA FISICA ACTIVIDAD EMPRESARIAL 2. PERSONA FISICA
	 */
	@Column(name = "TXT_PER_FIS")
	private String tipoPersona;

	@Column(name = "SIN_MX_CAT_ACT_ID_ACT")
	private Integer idActividad;
	/**
	 * Posibles valores: 1. FEMENINO 2. MASCULINO
	 */
	@Column(name = "TXT_GENERO")
	private String genero;

	@Column(name = "TXT_APE_PAT")
	private String paterno;

	@Column(name = "TXT_APE_MAT")
	private String materno;

	@Column(name = "TXT_TEL")
	private String telefono;
	
	@Column(name = "TXT_EXT")
	private String ext;

	@Email(message = "es incorrecto")
	@Column(name = "TXT_EMAIL")
	private String email;

	@Column(name = "TXT_COMENTARIOS")
	private String comentarios;

	@Column(name = "SIN_MX_CAT_EJEC_TXT_EXP_CMP")
	private String expReferente;

	@Size(min = 10, max = 13, message = "debe contener entre 10 y 13 digitos")
	@Column(name = "TXT_RFC")
	private String rfc;

	@Past
	@Column(name = "FCH_NAC")
	private Date fechaNacimiento;

	/**
	 * Posibles valores: 1. CASADO(A) 2. DIVORCIADO(A) 3. SOLTERO(A) 4. VIUDO(A)
	 */
	@Column(name = "TXT_EDO_CIV")
	private String estadoCivil;

	@Min(value = 0)
	@Column(name = "NUM_HIJOS")
	private Integer numHijos;

	@Column(name = "TXT_CALLE")
	private String calle;

	@Column(name = "TXT_NUM_EXT")
	private String numExt;

	@Column(name = "TXT_NUM_INT")
	private String numInt;

	@Column(name = "TXT_COLONIA")
	private String colonia;

	@Size(min = 5, max = 5, message = "debe ser de 5 digitos")
	@Column(name = "TXT_COD_POST")
	private String codPostal;

	@Min(value = 0)
	@Column(name = "NUM_CAPITAL_SOCIAL")
	private Long capital;

	@Column(name = "NUM_FACTURACION")
	private Long facturacion;

	@Min(value = 0)
	@Column(name = "NUM_EMPLEADOS")
	private Integer numEmpleados;

	@JsonManagedReference
	@OneToMany(mappedBy = "prospecto", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Contacto> contactos;
	
	//NO PERSISTEN
	@Transient
	private Integer numCC;
	
	@Transient
	private Integer idZon;
	
	@Transient
	private Integer idReg;
	

	public Date getFechaAlta() {
		return fechaAlta;
	}

	@PrePersist
	public void setFechaAlta() {
		this.fechaAlta = new Date();
	}

	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}

	@PreUpdate
	public void setFechaActualizacion() {
		this.fechaActualizacion = new Date();
	}

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

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public Integer getIdTipoTelefono() {
		return idTipoTelefono;
	}

	public void setIdTipoTelefono(Integer idTipoTelefono) {
		this.idTipoTelefono = idTipoTelefono;
	}

	public String getTipoPersona() {
		return tipoPersona;
	}

	public void setTipoPersona(String tipoPersona) {
		this.tipoPersona = tipoPersona;
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

	public String getOfiReferente() {
		return ofiReferente;
	}

	public void setOfiReferente(String ofiReferente) {
		this.ofiReferente = ofiReferente;
	}

	public String getOfiAsignado() {
		return ofiAsignado;
	}

	public void setOfiAsignado(String ofiAsignado) {
		this.ofiAsignado = ofiAsignado;
	}

	public String getPaterno() {
		return paterno;
	}

	public void setPaterno(String paterno) {
		this.paterno = paterno;
	}

	public String getMaterno() {
		return materno;
	}

	public void setMaterno(String materno) {
		this.materno = materno;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getComentarios() {
		return comentarios;
	}

	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}

	public String getExpReferente() {
		return expReferente;
	}

	public void setExpReferente(String expReferente) {
		this.expReferente = expReferente;
	}

	public String getRfc() {
		return rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(String estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public Integer getNumHijos() {
		return numHijos;
	}

	public void setNumHijos(Integer numHijos) {
		this.numHijos = numHijos;
	}

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public String getNumExt() {
		return numExt;
	}

	public void setNumExt(String numExt) {
		this.numExt = numExt;
	}

	public String getNumInt() {
		return numInt;
	}

	public void setNumInt(String numInt) {
		this.numInt = numInt;
	}

	public String getColonia() {
		return colonia;
	}

	public void setColonia(String colonia) {
		this.colonia = colonia;
	}

	public String getCodPostal() {
		return codPostal;
	}

	public void setCodPostal(String codPostal) {
		this.codPostal = codPostal;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public List<Contacto> getContactos() {
		return contactos;
	}

	public void setContactos(List<Contacto> contactos) {
		this.contactos = contactos;
	}

	public Integer getNumEmpleados() {
		return numEmpleados;
	}

	public void setNumEmpleados(Integer numEmpleados) {
		this.numEmpleados = numEmpleados;
	}

	public Long getCapital() {
		return capital;
	}

	public void setCapital(Long capital) {
		this.capital = capital;
	}

	public Long getFacturacion() {
		return facturacion;
	}

	public void setFacturacion(Long facturacion) {
		this.facturacion = facturacion;
	}

	public Integer getNumCC() {
		return numCC;
	}

	public void setNumCC(Integer numCC) {
		this.numCC = numCC;
	}

	public Integer getIdZon() {
		return idZon;
	}

	public void setIdZon(Integer idZon) {
		this.idZon = idZon;
	}

	public Integer getIdReg() {
		return idReg;
	}

	public void setIdReg(Integer idReg) {
		this.idReg = idReg;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

}
