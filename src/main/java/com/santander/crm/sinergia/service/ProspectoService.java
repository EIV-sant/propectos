package com.santander.crm.sinergia.service;

import com.santander.crm.sinergia.entity.Prospecto;
import com.santander.crm.sinergia.filter.ProspectoFilter;
import com.santander.crm.sinergia.response.ConsultaProspectosRes;
import com.santander.crm.sinergia.response.GenericProspectoRes;

public interface ProspectoService {
	
	/**
	 * Metodo para consultar prospectos por filtro
	 * @param filter el filtro a aplicar
	 * @param token token enviado por NEO
	 * @return Lista de Prospectos
	 */
	ConsultaProspectosRes getProspectosByFilter(ProspectoFilter filter, String token);
	
	/**
	 * Metodo para guardar un prospecto
	 * @param prospecto prospecto a guardar
	 * @param token token enviado por NEO
	 * @return prospecto guardado
	 */
	GenericProspectoRes saveProspecto(Prospecto prospecto, String token);
	
	/**
	 * Metodo para consultar un prospecto por id
	 * @param idProspecto el identificador 
	 * @return prospecto
	 */
	GenericProspectoRes getProspecto(Integer idProspecto);
	
	/**
	 * Metodo para actualizar un prospecto
	 * @param prospecto a actualizar
	 * @return prospecto actualizado
	 */
	GenericProspectoRes updateProspecto(Prospecto prospecto);

}
