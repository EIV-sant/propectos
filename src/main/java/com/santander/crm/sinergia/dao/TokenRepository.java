package com.santander.crm.sinergia.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.santander.crm.sinergia.entity.Token;

public interface TokenRepository extends CrudRepository<Token, Integer> {
	
	@Query("SELECT t FROM Token t WHERE t.sToken = :sToken ")
	Token getBySToken(@Param(value = "sToken") String sToken);

}
