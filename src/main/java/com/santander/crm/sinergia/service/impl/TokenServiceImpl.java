package com.santander.crm.sinergia.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.santander.crm.sinergia.dao.EjecutivoRepository;
import com.santander.crm.sinergia.dao.ParametroRepository;
import com.santander.crm.sinergia.entity.Ejecutivo;
import com.santander.crm.sinergia.entity.Parametro;
import com.santander.crm.sinergia.exceptions.AccessException;
import com.santander.crm.sinergia.service.TokenService;
import com.santander.crm.sinergia.utils.CipherSinergias;

@Service
public class TokenServiceImpl implements TokenService {

	private static final Logger LOGGER = LoggerFactory.getLogger(TokenServiceImpl.class);

	private static final String ID_LLAVE = "decipher_key";
	private static final String DELIMITER = "\\|";

	@Autowired
	ParametroRepository parametroRepository;
	
	@Autowired
	EjecutivoRepository ejecutivoRepository;

	@Override
	public Ejecutivo desencriptarToken(String token) {
		Ejecutivo ejecutivo = new Ejecutivo();
		String desencriptado = "";

		try {

			Parametro llave = parametroRepository.findByNombre(ID_LLAVE);
			
			if(llave == null || llave.getValor()==null || llave.getValor().isEmpty()) {
				throw new Exception("No se encuentra la llave para desencriptar");
			}
			// desencriptado = CipherSinergias.desCipher(token, llave);
			desencriptado = CipherSinergias.desCipherURL(token, llave.getValor());

			System.out.println("TOKEN -> " + desencriptado);
			
			String[] arrayValores = desencriptado.split(DELIMITER);
			
			if(arrayValores.length > 1) {
				ejecutivo = ejecutivoRepository.findOne(arrayValores[0]);
			} else {
				LOGGER.info("Token incorrecto");
				throw new AccessException("Token incorrecto");
			}
			
			//ejecutivo.setOfiAct(arrayValores[0]);

		} catch (Exception e) {

			LOGGER.info("Error al desencriptar el token" + e.getMessage());
			throw new AccessException("Error al desencriptar el token");
		}

		return ejecutivo;
	}

}
