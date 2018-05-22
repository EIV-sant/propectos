package com.santander.crm.sinergia.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.santander.crm.sinergia.entity.Ejecutivo;

public interface EjecutivoRepository extends CrudRepository<Ejecutivo, String> {
	
	@Query("SELECT e FROM Ejecutivo e JOIN FETCH e.tipo p JOIN FETCH e.banca b WHERE p.id = :idTipo AND b.id = :idBanca AND e.idSucursal = :idSucursal ")
	Ejecutivo getEjecutivoByIdTipoIdTipoIdSucursal(@Param(value = "idTipo") Integer idTipo,
			@Param(value = "idBanca") Integer idBanca,
			@Param(value = "idSucursal") Integer idSucursal);
	
	@Query("SELECT e FROM Ejecutivo e JOIN FETCH e.tipo p JOIN FETCH e.banca b WHERE p.id = :idTipo AND (:idBanca IS NULL OR b.id = :idBanca) AND e.idSucursal = :idSucursal ")
	List<Ejecutivo> getEjecutivosByIdTipoIdBancaIdSucursal(@Param(value = "idTipo") Integer idTipo,
			@Param(value = "idBanca") Integer idBanca,
			@Param(value = "idSucursal") Integer idSucursal);
	
	Ejecutivo findByOfiAct(String ofiAct);

}
