package com.santander.crm.sinergia.dao;

import java.util.List;

import com.santander.crm.sinergia.entity.Prospecto;
import com.santander.crm.sinergia.filter.ProspectoFilter;

public interface ProspectoCustomRepository {
	
	List<Prospecto> getProspectosFiltered(ProspectoFilter filter);
	
	Long countProspectosFiltered(ProspectoFilter filter);
	
	Long countProspectosConvertidosFiltered(ProspectoFilter filter);
	
	Long countProspectosByEjecutivo(String ofiAsignado);

}
