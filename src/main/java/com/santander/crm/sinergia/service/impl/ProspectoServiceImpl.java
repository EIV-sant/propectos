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

import com.santander.crm.sinergia.dao.EjecutivoRepository;
import com.santander.crm.sinergia.dao.EjecutivoSrRepository;
import com.santander.crm.sinergia.dao.ProspectoRepository;
import com.santander.crm.sinergia.entity.Ejecutivo;
import com.santander.crm.sinergia.entity.EjecutivoSr;
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
	EjecutivoRepository ejecutivoRepository;

	@Autowired
	EjecutivoSrRepository ejecutivoSrRepository;

	@Autowired
	TokenService tokenService;

	ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	Validator validator = factory.getValidator();

	// ESTATUS
	private static final Integer ESTATUS_NUEVO = 1;
	
	//TIPO TELÉFONO
//	private static final Integer TELEFONO_OFICINA = 3;

	// PERFILES EJECUTIVOS
	private static final Integer TIPO_DIRECTOR = 1;
	private static final Integer TIPO_GERENTENEGOCIOSELECT = 62;
	private static final Integer TIPO_PREMIER = 2;
	private static final Integer TIPO_COMERCIAL6 = 6;
	private static final Integer TIPO_COMERCIAL8 = 8;
	private static final Integer TIPO_JR = 7;

	// CAMPOS ESPECIFICOS
	private static final Integer ACTIVIDAD_PARTICULAR = 7;
	private static final String NO_APLICA = "NO APLICA";

	@Override
	public ConsultaProspectosRes getProspectosByFilter(ProspectoFilter filter, String token) {
		ConsultaProspectosRes response = new ConsultaProspectosRes();

		try {

			// Ejecutivo ejecutivo =
			// tokenService.desencriptarToken("af9AuquMutWZbRASD6N5mf3C8ZkKXC1kal5PrNAub6TtWt4uZJA97bNnYd39jf7wOkYLlx65qW2aEeNTrhKxPFriZSD%2BV9AXirewm8kE5rADxZbbhyRsAA%3D%3D");
			Ejecutivo ejecutivo = tokenService.desencriptarToken(token);

			if (filter.getOfiAct() == null) {
				filter.setOfiAct(ejecutivo.getOfiAct());
			}

			List<Prospecto> prospectos = prospectoRepository.getProspectosFiltered(filter);
			for (Prospecto p : prospectos) {
				p.setContactos(null);
			}
			response.setProspectos(prospectos);

			response.setTotal(prospectoRepository.countProspectosFiltered(filter));
			response.setConvertidos(prospectoRepository.countProspectosConvertidosFiltered(filter));
			response.setHttpStatus(HttpStatus.OK);

		} catch (AccessException ae) {
			response.setHttpStatus(HttpStatus.BAD_REQUEST);
			response.setMessage(ae.getMessage());
		} catch (Exception e) {
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

			// Asignacion}switch(prospecto.getIdBanca()) {
			Ejecutivo ejecAsignado = asignarEjecutivo(prospecto);
			prospecto.setOfiAsignado(ejecAsignado.getOfiAct());

			Prospecto prospectoSaved = prospectoRepository.save(prospecto);
			response.setProspecto(prospectoSaved);
			// response.setProspecto(prospecto);
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
		} catch (Exception e) {
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
			if (prospectoAnt != null) {

				// INFORMACIÓN QUE NO CAMBIA
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
		} catch (Exception e) {
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
		case 1: // Particulares
			if (prospecto.getEmail() == null && prospecto.getTelefono() == null) {
				LOGGER.error("Error: Se debe indicar al menos un teléfono o email");
				throw new ValidationException("Error: Se debe indicar al menos un teléfono o email");
			}
			if (prospecto.getGenero() == null) {
				LOGGER.error("Error: El campo genero no puede ser nulo");
				throw new ValidationException("Error: El campo genero no puede ser nulo");
			}
			if (prospecto.getEstadoCivil() == null) {
				prospecto.setEstadoCivil(NO_APLICA);
			}
			if (prospecto.getPaterno() == null) {
				LOGGER.error("Error: El campo paterno no puede ser nulo");
				throw new ValidationException("Error: El campo paterno no puede ser nulo");
			}
			if(prospecto.getNumCC() == null) {
				LOGGER.error("Error: El campo sucursal no puede ser nulo");
				throw new ValidationException("Error: El campo sucursal no puede ser nulo");
			}
			break;
		case 2: // Pyme
			if (prospecto.getRfc() == null) {
				LOGGER.error("Error: El campo rfc no puede ser nulo");
				throw new ValidationException("Error: El campo rfc no puede ser nulo");
			}
			if (prospecto.getIdActividad() == null) {
				LOGGER.error("Error: El campo idActividad no puede ser nulo");
				throw new ValidationException("Error: El campo idActividad no puede ser nulo");
			}
			if(prospecto.getNumCC() == null) {
				LOGGER.error("Error: El campo sucursal no puede ser nulo");
				throw new ValidationException("Error: El campo sucursal no puede ser nulo");
			}
			break;
		case 3: // Bei
			if (prospecto.getRfc() == null) {
				LOGGER.error("Error: El campo rfc no puede ser nulo");
				throw new ValidationException("Error: El campo rfc no puede ser nulo");
			}
			if (prospecto.getIdActividad() == null) {
				LOGGER.error("Error: El campo idActividad no puede ser nulo");
				throw new ValidationException("Error: El campo idActividad no puede ser nulo");
			}
			if (prospecto.getOfiAsignado() == null) {
				LOGGER.error("Error: El campo ofiAsignado no puede ser nulo");
				throw new ValidationException("Error: El campo ofiAsignado no puede ser nulo");
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

	private Ejecutivo asignarEjecutivo(Prospecto prospecto) {
		Ejecutivo ejec = null;
		List<Ejecutivo> ejecutivoList = null;

		// set ejecutivo
		switch (prospecto.getIdBanca()) {
		case 1: // Particular
			if (prospecto.getCapital() == null) {
				// ejecutivo director
				ejec = ejecutivoRepository.getEjecutivoByIdTipoIdTipoIdSucursal(TIPO_DIRECTOR, 1, prospecto.getNumCC());
			} else {
				if (prospecto.getCapital() >= 75000) { // monto mayor o igual a 75,000
					// Ej. Gerente de negocio select --> 62, sino al Ej. premier --> 2
					ejecutivoList = ejecutivoRepository
							.getEjecutivosByIdTipoIdBancaIdSucursal(TIPO_GERENTENEGOCIOSELECT, 1, prospecto.getNumCC());
					System.out.println("gerente select lista-->" + ejecutivoList);
					if (!ejecutivoList.isEmpty()) { // Ej. Gerente de negocio select --> 62
						System.out.println("gerente select-->");
						ejec = this.obtieneEjecutivoMenorProspectos(ejecutivoList);
						System.out.println("gerente select seleccionado-->" + ejec.getOfiAct());
					} else { // Ej. premier --> 2
						ejecutivoList = ejecutivoRepository.getEjecutivosByIdTipoIdBancaIdSucursal(TIPO_PREMIER, 1,
								prospecto.getNumCC());
						if (!ejecutivoList.isEmpty()) { // Ej. Premier --> 2
							ejec = this.obtieneEjecutivoMenorProspectos(ejecutivoList);
						} else {
							ejec = ejecutivoRepository.getEjecutivoByIdTipoIdTipoIdSucursal(TIPO_DIRECTOR, 1,
									prospecto.getNumCC());
						}
					}
				} else { // monto menor a 75,000
					// Ej. comercial --> 6 y 8
					ejecutivoList = ejecutivoRepository.getEjecutivosByIdTipoIdBancaIdSucursal(TIPO_COMERCIAL6, 1,
							prospecto.getNumCC());
					if (!ejecutivoList.isEmpty()) { // Ej. Comercial --> 6
						ejec = this.obtieneEjecutivoMenorProspectos(ejecutivoList);
					} else { // Ej. Comercial --> 8
						ejecutivoList = ejecutivoRepository.getEjecutivosByIdTipoIdBancaIdSucursal(TIPO_COMERCIAL8, 1,
								prospecto.getNumCC());
						if (!ejecutivoList.isEmpty()) { // Ej. Comercial --> 8
							ejec = this.obtieneEjecutivoMenorProspectos(ejecutivoList);
						} else {
							ejec = ejecutivoRepository.getEjecutivoByIdTipoIdTipoIdSucursal(TIPO_DIRECTOR, 1,
									prospecto.getNumCC());
						}
					}
				}
			}
			System.out.println("ejecutivo particular-->" + ejec);
			break;
		case 2: // PyME
			ejecutivoList = ejecutivoRepository.getEjecutivosByIdTipoIdBancaIdSucursal(TIPO_JR, 2,
					prospecto.getNumCC());
			if (!ejecutivoList.isEmpty()) { // Ej. Jr --> 7
				ejec = this.obtieneEjecutivoMenorProspectos(ejecutivoList);
			} else {
				List<EjecutivoSr> ejecutivoSrList = ejecutivoSrRepository
						.getEjecutivosSrByIdSucursal(prospecto.getNumCC());
				if (!ejecutivoSrList.isEmpty()) { // Ej Sr --> tabla relación
					EjecutivoSr es = ejecutivoSrList.get(0);
					ejec = ejecutivoRepository.findByOfiAct(es.getOfiAct());
				} else { // Ej Director sin banca
					ejecutivoList = ejecutivoRepository.getEjecutivosByIdTipoIdBancaIdSucursal(TIPO_DIRECTOR, null,
							prospecto.getNumCC());
					ejec = this.obtieneEjecutivoMenorProspectos(ejecutivoList);
				}
			}
			break;
		case 3: //BEI
			ejec = new Ejecutivo();
			ejec.setOfiAct(prospecto.getOfiAsignado());
			break;
		}
		return ejec;
	}

	private Ejecutivo obtieneEjecutivoMenorProspectos(List<Ejecutivo> ejecutivoList) {
		Ejecutivo ejec = null;
		boolean band = true;
		Long count = null;
		if (ejecutivoList.size() == 1) {
			ejec = ejecutivoList.get(0);
		} else { // selecciona el ejecutivo con menos prospectos asignados
			for (Ejecutivo e : ejecutivoList) {
				if (band) {// si es el primer registro
					System.out.println("primer registro-->");
					count = prospectoRepository.countProspectosByEjecutivo(e.getOfiAct());
					System.out.println("primer registro count -->" + count);
					ejec = e;
					if (count == null) { // si no tiene se asigna este ejec por default
						System.out.println("primer registro count null-->");
						break;
					}
					band = false;
				} else {
					System.out.println("registro-->");
					Long countAux = prospectoRepository.countProspectosByEjecutivo(e.getOfiAct());
					if (countAux != null) {
						if (countAux < count) { // si es menor se reasigna el ejec
							System.out.println("registro menor-->");
							ejec = e;
							count = countAux;
						}
					} else { // si no tiene se asigna este ejec por default
						System.out.println("registro sin asignados-->");
						ejec = e;
						break;
					}
				}
			}
			System.out.println("ejec seleccionado-->" + ejec);
		}
		return ejec;
	}

}
