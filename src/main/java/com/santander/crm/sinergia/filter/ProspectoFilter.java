package com.santander.crm.sinergia.filter;

public class ProspectoFilter {

	private Integer pageNum;

	private Integer pageSize;

	private Integer tipoConsulta;

	private String ofiAct;

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getTipoConsulta() {
		return tipoConsulta;
	}

	public void setTipoConsulta(Integer tipoConsulta) {
		this.tipoConsulta = tipoConsulta;
	}

	public String getOfiAct() {
		return ofiAct;
	}

	public void setOfiAct(String ofiAct) {
		this.ofiAct = ofiAct;
	}

}
