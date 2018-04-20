package com.santander.crm.sinergia.service;

import java.util.List;

import com.santander.crm.sinergia.entity.Prospecto;
import com.santander.crm.sinergia.filter.ProspectoFilter;
import com.santander.crm.sinergia.response.AltaProspectoRes;

public interface ProspectoService {
	
	List<Prospecto> getProspectosByFilter(ProspectoFilter filter);
	
	AltaProspectoRes saveProspecto(Prospecto prospecto);

}
