package com.santander.crm.sinergia.service;

import com.santander.crm.sinergia.entity.Prospecto;
import com.santander.crm.sinergia.filter.ProspectoFilter;
import com.santander.crm.sinergia.response.AltaProspectoRes;
import com.santander.crm.sinergia.response.ConsultaProspectosRes;

public interface ProspectoService {
	
	ConsultaProspectosRes getProspectosByFilter(ProspectoFilter filter);
	
	AltaProspectoRes saveProspecto(Prospecto prospecto);

}
