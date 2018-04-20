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
import com.santander.crm.sinergia.entity.Prospecto;
import com.santander.crm.sinergia.filter.ProspectoFilter;
import com.santander.crm.sinergia.response.AltaProspectoRes;
import com.santander.crm.sinergia.service.ProspectoService;

@Service("prospectoServiceImpl")
public class ProspectoServiceImpl implements ProspectoService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProspectoServiceImpl.class);

	@Autowired
	ProspectoRepository prospectoRepository;

	ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	Validator validator = factory.getValidator();

	// ESTATUS
	private static final Integer ESTATUS_NUEVO = 1;

	// CAMPOS ESPECIFICOS
	private static final Integer ACTIVIDAD_PARTICULAR = 7;

	@Override
	public List<Prospecto> getProspectosByFilter(ProspectoFilter filter) {

		return (List<Prospecto>) prospectoRepository.findAll();
	}

	@Override
	public AltaProspectoRes saveProspecto(Prospecto prospecto) {
		AltaProspectoRes response = new AltaProspectoRes();
		try {
			prospecto.setExpReferente("C787961");//Cambiar por token
			prospecto.setOfiReferente("9500");//Cambiar por token
			
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
			break;
		case 3:
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
			break;
		case 3:
			break;
		}

		return prospecto;
	}

}
