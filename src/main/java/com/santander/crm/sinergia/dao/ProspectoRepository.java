package com.santander.crm.sinergia.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.santander.crm.sinergia.entity.Prospecto;

public interface ProspectoRepository extends CrudRepository<Prospecto, Integer>, ProspectoCustomRepository{
	
	@Query("SELECT p FROM Prospecto p LEFT JOIN FETCH p.contactos WHERE p.id = :idProspecto")
	Prospecto getProspectoById(@Param(value = "idProspecto") Integer idProspecto);
	
}
