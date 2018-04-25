package com.santander.crm.sinergia.service;

import com.santander.crm.sinergia.entity.Ejecutivo;

public interface TokenService {
	
	Ejecutivo desencriptarToken(String token);

}
