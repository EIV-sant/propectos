package com.santander.crm.sinergia.dao;

import java.util.List;

import com.santander.crm.sinergia.filter.ProspectoFilter;
import com.santander.crm.sinergia.filter.ProspectoSeguimiento;

public interface ProspectoCustomRepository {
	
	List<ProspectoSeguimiento> getProspectosFiltered(ProspectoFilter filter);
	
	Long countProspectosFiltered(ProspectoFilter filter);
	
	Long countProspectosConvertidosFiltered(ProspectoFilter filter);
	
	Long countProspectosByEjecutivo(String ofiAsignado);

}
