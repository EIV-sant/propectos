package com.santander.crm.sinergia.response;

public class TokenRes {

private String ofiAct;
	
	private String expediente;
	
	private Integer idSuc;
	
	private Integer idZona;
	
	private Integer idRegion;
	
	private Integer tpoEjec;
	
	private String token;

	public String getOfiAct() {
		return ofiAct;
	}

	public void setOfiAct(String ofiAct) {
		this.ofiAct = ofiAct;
	}

	public String getExpediente() {
		return expediente;
	}

	public void setExpediente(String expediente) {
		this.expediente = expediente;
	}

	public Integer getIdSuc() {
		return idSuc;
	}

	public void setIdSuc(Integer idSuc) {
		this.idSuc = idSuc;
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

	public Integer getTpoEjec() {
		return tpoEjec;
	}

	public void setTpoEjec(Integer tpoEjec) {
		this.tpoEjec = tpoEjec;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
}
