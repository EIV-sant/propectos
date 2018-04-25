package com.santander.crm.sinergia.service.impl;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.santander.crm.sinergia.dao.ProspectoRepository;
import com.santander.crm.sinergia.entity.Ejecutivo;
import com.santander.crm.sinergia.entity.Prospecto;
import com.santander.crm.sinergia.exceptions.AccessException;
import com.santander.crm.sinergia.filter.ProspectoFilter;
import com.santander.crm.sinergia.response.ConsultaProspectosRes;
import com.santander.crm.sinergia.response.GenericProspectoRes;
import com.santander.crm.sinergia.service.ProspectoService;
import com.santander.crm.sinergia.service.TokenService;

@Service("prospectoServiceImpl")
public class ProspectoServiceImpl implements ProspectoService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProspectoServiceImpl.class);

	@Autowired
	ProspectoRepository prospectoRepository;
	
	@Autowired
	TokenService tokenService;

	ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	Validator validator = factory.getValidator();

	// ESTATUS
	private static final Integer ESTATUS_NUEVO = 1;

	// CAMPOS ESPECIFICOS
	private static final Integer ACTIVIDAD_PARTICULAR = 7;
	private static final String NO_APLICA = "NO APLICA";

	@Override
	public ConsultaProspectosRes getProspectosByFilter(ProspectoFilter filter, String token) {
		ConsultaProspectosRes response = new ConsultaProspectosRes();
		
		try {
			
//			Ejecutivo ejecutivo = tokenService.desencriptarToken("af9AuquMutWZbRASD6N5mf3C8ZkKXC1kal5PrNAub6TtWt4uZJA97bNnYd39jf7wOkYLlx65qW2aEeNTrhKxPFriZSD%2BV9AXirewm8kE5rADxZbbhyRsAA%3D%3D");
			Ejecutivo ejecutivo = tokenService.desencriptarToken(token);
			
			if(filter.getOfiAct()==null) {
				filter.setOfiAct(ejecutivo.getOfiAct());
			}
			
			List<Prospecto> prospectos= prospectoRepository.getProspectosFiltered(filter);
			for(Prospecto p : prospectos) {
				p.setContactos(null);
			}
			response.setProspectos(prospectos);
			
			response.setTotal(prospectoRepository.countProspectosFiltered(filter));
			response.setConvertidos(prospectoRepository.countProspectosConvertidosFiltered(filter));
			response.setHttpStatus(HttpStatus.OK);
			
		}catch(AccessException ae) {
			response.setHttpStatus(HttpStatus.BAD_REQUEST);
			response.setMessage(ae.getMessage());
		}catch(Exception e) {
			e.printStackTrace();
			response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			response.setMessage(e.getMessage());
		}
		
		return response;
	}

	@Override
	public GenericProspectoRes saveProspecto(Prospecto prospecto, String token) {
		GenericProspectoRes response = new GenericProspectoRes();
		try {
			
			Ejecutivo ejecutivo = tokenService.desencriptarToken(token);
			
			prospecto.setExpReferente(ejecutivo.getExpediente());
			prospecto.setOfiReferente(ejecutivo.getOfiAct());
			
			// Validaciones
			validaProspectoBean(prospecto);

			// Setteos
			prospecto = setDefaultValues(prospecto);

			Prospecto prospectoSaved = prospectoRepository.save(prospecto);
			response.setProspecto(prospectoSaved);
			response.setHttpStatus(HttpStatus.OK);

		} catch (ValidationException ve) {
			response.setHttpStatus(HttpStatus.BAD_REQUEST);
			response.setMessage(ve.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage(e.getMessage());
			response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return response;

	}

	@Override
	public GenericProspectoRes getProspecto(Integer idProspecto) {
		GenericProspectoRes response = new GenericProspectoRes();
		try {
			
			response.setProspecto(prospectoRepository.getProspectoById(idProspecto));
			response.setHttpStatus(HttpStatus.OK);
		} catch(Exception e) {
			response.setMessage(e.getMessage());
			response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
	
	@Override 
	public GenericProspectoRes updateProspecto(Prospecto prospecto) {
		GenericProspectoRes response = new GenericProspectoRes();
		try {
			validaProspectoBean(prospecto);
			Prospecto prospectoAnt = prospectoRepository.getProspectoById(prospecto.getId());
			if(prospectoAnt != null) {
				
				//INFORMACIÓN QUE NO CAMBIA
				prospecto.setOfiReferente(prospectoAnt.getOfiAsignado());
				prospecto.setExpReferente(prospectoAnt.getExpReferente());
				prospecto.setIdEstatus(prospectoAnt.getIdEstatus());
				
				response.setProspecto(prospectoRepository.save(prospecto));
				response.setHttpStatus(HttpStatus.OK);
			} else {
				throw new ValidationException("No se encuentra el prospecto");
			}
			
		} catch (ValidationException ve) {
			response.setHttpStatus(HttpStatus.BAD_REQUEST);
			response.setMessage(ve.getMessage());
		} catch(Exception e) {
			response.setMessage(e.getMessage());
			response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	private void validaProspectoBean(Prospecto prospecto) {
		// Validaciones generales
		Set<ConstraintViolation<Prospecto>> violations = validator.validate(prospecto);

		for (ConstraintViolation<Prospecto> vi : violations) {
			LOGGER.error("Error: El campo " + vi.getPropertyPath() + " " + vi.getMessage());
			throw new ValidationException("Error: El campo " + vi.getPropertyPath() + " " + vi.getMessage());
		}

		// Validaciones especificas
		switch (prospecto.getIdBanca()) {
		case 1:
			if (prospecto.getEmail() == null && prospecto.getTelefono() == null) {
				LOGGER.error("Error: Se debe indicar al menos un teléfono o email");
				throw new ValidationException("Error: Se debe indicar al menos un teléfono o email");
			}
			if (prospecto.getGenero() == null) {
				LOGGER.error("Error: El campo genero no puede ser nulo");
				throw new ValidationException("Error: El campo genero no puede ser nulo");
			}
			if (prospecto.getEstadoCivil() == null) {
				LOGGER.error("Error: El campo estadoCivil no puede ser nulo");
				throw new ValidationException("Error: El campo estadoCivil no puede ser nulo");
			}
			if(prospecto.getPaterno() == null) {
				LOGGER.error("Error: El campo paterno no puede ser nulo");
				throw new ValidationException("Error: El campo paterno no puede ser nulo");
			}
			break;
		case 2:
		case 3:
			if(prospecto.getRfc() == null) {
				LOGGER.error("Error: El campo rfc no puede ser nulo");
				throw new ValidationException("Error: El campo rfc no puede ser nulo");
			}
			if(prospecto.getIdActividad() == null) {
				LOGGER.error("Error: El campo idActividad no puede ser nulo");
				throw new ValidationException("Error: El campo idActividad no puede ser nulo");
			}
			break;
		}

	}

	private Prospecto setDefaultValues(Prospecto prospecto) {
		// Estatus
		prospecto.setIdEstatus(ESTATUS_NUEVO);

		// Sets específicos
		switch (prospecto.getIdBanca()) {
		case 1:
			prospecto.setIdActividad(ACTIVIDAD_PARTICULAR);
			break;
		case 2:
		case 3:
			prospecto.setEstadoCivil(NO_APLICA);
			prospecto.setGenero(NO_APLICA);
			prospecto.setTipoPersona(NO_APLICA);
			break;
		}

		return prospecto;
	}

	

}
