package com.santander.crm.sinergia.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "SIN_MX_MAE_TOKEN")
public class Token {

	@Id
	@Column(name = "ID_TOKEN")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CUST_SEQ_TOKEN")
	@SequenceGenerator(sequenceName = "SIN_MX_MAE_TOKEN_SEQ", allocationSize = 1, name = "CUST_SEQ_TOKEN")
	private Integer id;

	// OBLIGATORIOS
	@NotEmpty
	@Column(name = "TXT_TOKEN")
	private String sToken;
	
	@Column(name = "FCH_ALTA")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaAlta;
	
	@Column(name = "FCH_VIG")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaVigencia;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getsToken() {
		return sToken;
	}

	public void setsToken(String sToken) {
		this.sToken = sToken;
	}

	public Date getFechaVigencia() {
		Date fecha = fechaVigencia;
		return fecha;
	}

	public void setFechaVigencia(Date fechaVigencia) {
		this.fechaVigencia = (Date)fechaVigencia.clone();
	}

	public Date getFechaAlta() {
		Date fecha = fechaAlta;
		return fecha;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = (Date)fechaAlta.clone();;
	}

}
