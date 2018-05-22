package com.santander.crm.sinergia.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SIN_MX_EJV_SR_SUC")
public class EjecutivoSr {
	
	@Id
	@Column(name = "ID_CONS_SR_SUC")
	private Integer id;

	@Column(name = "TXT_OFI_ACT")
	private String ofiAct;

	@Column(name = "NUM_CC")
	private Integer idSucursal;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdSucursal() {
		return idSucursal;
	}

	public void setIdSucursal(Integer idSucursal) {
		this.idSucursal = idSucursal;
	}

	public String getOfiAct() {
		return ofiAct;
	}

	public void setOfiAct(String ofiAct) {
		this.ofiAct = ofiAct;
	}
	
}
