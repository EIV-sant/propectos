package com.santander.crm.sinergia.service.impl;

import java.io.IOException;
import java.util.Date;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.santander.crm.sinergia.dao.EjecutivoRepository;
import com.santander.crm.sinergia.dao.ParametroRepository;
import com.santander.crm.sinergia.dao.TokenRepository;
import com.santander.crm.sinergia.entity.Ejecutivo;
import com.santander.crm.sinergia.entity.Parametro;
import com.santander.crm.sinergia.entity.Token;
import com.santander.crm.sinergia.exceptions.AccessException;
import com.santander.crm.sinergia.response.TokenRes;
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

	@Autowired
	TokenRepository tokenRepository;

	@Override
	public Ejecutivo desencriptarToken(String token) {
		Ejecutivo ejecutivo = new Ejecutivo();
		String desencriptado = "";

		try {

			Parametro llave = parametroRepository.findByNombre(ID_LLAVE);

			if (llave == null || llave.getValor() == null || llave.getValor().isEmpty()) {
				throw new Exception("No se encuentra la llave para desencriptar");
			}
			// String encript = "1A0E|Manuel Antonio Garduño
			// Garduño|1A0E|1000|1502|21|pro|19/04/2018-15:18:20-GMT";
			// System.out.println("1-->"+CipherSinergias.cipherURL(encript,
			// llave.getValor()));
			// desencriptado = CipherSinergias.desCipher(token, llave);
			desencriptado = CipherSinergias.desCipherURL(token, llave.getValor());

			System.out.println("TOKEN -> " + desencriptado);

			String[] arrayValores = desencriptado.split(DELIMITER);

			if (arrayValores.length > 1) {
				ejecutivo = ejecutivoRepository.findOne(arrayValores[0]);
			} else {
				LOGGER.info("Token incorrecto");
				throw new AccessException("Token incorrecto");
			}

			// ejecutivo.setOfiAct(arrayValores[0]);

		} catch (Exception e) {

			LOGGER.info("Error al desencriptar el token" + e.getMessage());
			throw new AccessException("Error al desencriptar el token");
		}

		return ejecutivo;
	}

	@Override
	public Ejecutivo decodeToken(String sToken) {
		Ejecutivo ejecutivo = null;
		Token token = null;
		String desencriptado = "";
		Date hoy = new Date();

		try {
			byte[] message = Base64.decodeBase64(sToken);
			desencriptado = new String(message);
			System.out.println("TOKEN -->"+desencriptado);
			ObjectMapper om = new ObjectMapper();
			TokenRes tr = null;
			tr = om.readValue(desencriptado, TokenRes.class);
			token = tokenRepository.getBySToken(tr.getToken());
			if (token != null) {
				if(hoy.after(token.getFechaVigencia())) {
					LOGGER.info("El token caduco");
					throw new AccessException("El token caduco");
				} else {
					ejecutivo = ejecutivoRepository.findByOfiAct(tr.getOfiAct().trim());
					if (ejecutivo == null) {
						LOGGER.info("Ejecutivo sin acceso");
						throw new AccessException("Ejecutivo sin acceso");
					}
				}
			} else {
				LOGGER.info("Token inválido");
				throw new AccessException("Token inválido");
			}
		} catch (JsonParseException e) {
			LOGGER.info("Error al desencriptar el token1 " + e.getMessage());
			throw new AccessException("Error al desencriptar el token: " + e.getMessage());
		} catch (JsonMappingException e) {
			LOGGER.info("Error al desencriptar el token2 " + e.getMessage());
			throw new AccessException("Error al desencriptar el token: " + e.getMessage());
		} catch (IOException e) {
			LOGGER.info("Error al desencriptar el token3 " + e.getMessage());
			throw new AccessException("Error al desencriptar el token: " + e.getMessage());
		} catch (Exception e) {
			LOGGER.info("Error al desencriptar el token4 " + e.getMessage());
			throw new AccessException("Error al desencriptar el token: " + e.getMessage());
		}

		return ejecutivo;
	}

}
