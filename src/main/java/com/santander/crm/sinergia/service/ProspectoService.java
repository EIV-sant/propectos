package com.santander.crm.sinergia.service;

import java.util.List;

import com.santander.crm.sinergia.entity.Prospecto;
import com.santander.crm.sinergia.filter.ProspectoFilter;

public interface ProspectoService {
	
	List<Prospecto> getProspectosByFilter(ProspectoFilter filter);
	
	Prospecto saveProspecto(Prospecto prospecto);

}
