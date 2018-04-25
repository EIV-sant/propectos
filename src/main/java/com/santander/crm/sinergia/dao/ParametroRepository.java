package com.santander.crm.sinergia.dao;

import org.springframework.data.repository.CrudRepository;

import com.santander.crm.sinergia.entity.Parametro;

public interface ParametroRepository extends CrudRepository<Parametro, String> {
	
	Parametro findByNombre(String nombre);

}
