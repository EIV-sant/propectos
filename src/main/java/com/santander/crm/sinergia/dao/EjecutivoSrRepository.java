package com.santander.crm.sinergia.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.santander.crm.sinergia.entity.EjecutivoSr;

public interface EjecutivoSrRepository extends CrudRepository<EjecutivoSr, String> {
	
	@Query("SELECT es FROM EjecutivoSr es WHERE es.idSucursal = :idSucursal ")
	List<EjecutivoSr> getEjecutivosSrByIdSucursal(@Param(value = "idSucursal") Integer idSucursal);

}
