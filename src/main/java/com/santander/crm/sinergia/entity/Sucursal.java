package com.santander.crm.sinergia.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SIN_MX_MAE_SUC")
public class Sucursal {
	
	@Id
	@Column(name = "NUM_CC")
	private Integer id;
	
	@Column(name = "SIN_MX_CAT_ZON_ID_ZON")
	private Integer idZona;
	
	@Column(name = "SIN_MX_CAT_REG_ID_REG")
	private Integer idRegion;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdZona() {
		return idZona;
	}

	public void setIdZona(Integer idZona) {
		this.idZona = idZona;
	}

	public Integer getIdRegion() {
		return idRegion;
	}

	public void setIdRegion(Integer idRegion) {
		this.idRegion = idRegion;
	}

}
