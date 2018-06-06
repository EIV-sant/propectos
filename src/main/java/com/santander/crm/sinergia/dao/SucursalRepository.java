package com.santander.crm.sinergia.dao;

import org.springframework.data.repository.CrudRepository;

import com.santander.crm.sinergia.entity.Sucursal;

public interface SucursalRepository extends CrudRepository<Sucursal, Integer> {
	
	Sucursal findByid(Integer id); 

}
