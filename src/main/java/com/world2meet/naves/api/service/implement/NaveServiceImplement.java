package com.world2meet.naves.api.service.implement;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.world2meet.naves.api.dto.NaveDto;
import com.world2meet.naves.api.entity.Nave;
import com.world2meet.naves.api.exception.NaveExistsException;
import com.world2meet.naves.api.exception.NaveNotFoundException;
import com.world2meet.naves.api.exception.NaveOperationException;
import com.world2meet.naves.api.repository.NaveRepository;
import com.world2meet.naves.api.repository.NaveRepositoryPagination;
import com.world2meet.naves.api.service.NaveService;

/**
 * Clase de implementacion de la interfaz servicio
 */
@Service
public class NaveServiceImplement implements NaveService {

	/**
	 * Instancia de logger para trazas.
	 */
	private static final Logger logger = LoggerFactory.getLogger(NaveServiceImplement.class);

	/**
	 * Instancia de objectMapper convertidor de dto a entity y viseversa
	 */
	private static final ObjectMapper objectMapper = new ObjectMapper();

	/**
	 * Instancia NaveRepositoryPagination de JPA paginacion
	 */
	@Autowired
	private NaveRepositoryPagination naveRepositoryPagination;

	/**
	 * Instancia NaveRepository de JPA para CRUD
	 */
	@Autowired
	private NaveRepository naveRepository;

	@Override
	public Page<Nave> navePaginacion(int numeroPagina, int tamano) throws NaveOperationException {
		logger.debug("Entrando al metodo navePaginacion");
		try {
			Pageable paginacion = PageRequest.of(numeroPagina, tamano);
			Page<Nave> nave = naveRepositoryPagination.findAll(paginacion);
			if (logger.isDebugEnabled()) {
				logger.debug(String.format("Ha encontrado los registros: %s", nave));
			}

			return nave;

		} catch (Exception e) {
			logger.error(String.format("Ha ocurrido un error realizando la consulta de naves numeroPagina %s tamano %s",
					numeroPagina, tamano), e);
			throw new NaveOperationException(null);
		}
	}

	@Override
	public Page<Nave> obtenerNombres(String nombre, int numeroPagina, int tamano)
			throws NaveOperationException, NaveNotFoundException {
		logger.debug("Entrando al metodo obtenerNombres");
		try {
			nombre = nombre.toUpperCase();
			Pageable paginacion = PageRequest.of(numeroPagina, tamano);

			Page<Nave> naveEntity = naveRepositoryPagination.busquedaNombre(nombre, paginacion);
			if (naveEntity.isEmpty()) {
				if (logger.isErrorEnabled()) {
					logger.error(String.format("no existen nombres de naves %s", nombre));
				}

				throw new NaveNotFoundException("no existen nombres de naves");

			} else {
				if (logger.isDebugEnabled()) {
					logger.debug(String.format("Ha encontrado las naves %s", naveEntity));
				}

			}
			return naveEntity;
		} catch (NaveNotFoundException e) {
			throw e;
		} catch (Exception e) {
			logger.error(String.format("Ha ocurrido un error realizando la consulta por nombre %s", nombre), e);
			throw new NaveOperationException("Error al buscar naves por nombre ");
		}
	}

	@Override
	@Cacheable(value = "naves", key = "#id")
	public NaveDto buscarId(Long id) throws NaveNotFoundException, NaveOperationException {
		logger.debug("Entrando al metodo buscarId");
		try {

			Optional<Nave> nave = naveRepository.findById(id);
			if (nave.isEmpty()) {
				if (logger.isErrorEnabled()) {
					logger.error(String.format("El identificador de la  nave no existe %s", id));
				}

				throw new NaveNotFoundException("El identificador de la nave no existe ");
			} else {
				if (logger.isDebugEnabled()) {
					logger.debug(String.format("Ha encontrado el registro %s", nave.toString()));
				}
			}
			return convertEntityToDTO(nave.get());
		} catch (NaveNotFoundException e) {
			throw e;
		} catch (Exception e) {
			logger.error(String.format("Ha ocurrido un error al buscar la nave %s", id), e);
			throw new NaveOperationException("Error al buscar nave");
		}
	}

	/**
	 * Metodo verificacion de usuarios para evitar duplicidad
	 */
	private boolean existeNave(String nombre, String obra) {
		logger.debug("Entrando al metodo existeNave");
		return naveRepository.existsByNombreAndObra(nombre, obra);
	}

	@Override
	public NaveDto guardar(NaveDto nave) throws NaveOperationException, NaveExistsException {
		logger.debug("Entrando al metodo guardar");
		try {
			if (existeNave(nave.getNombre(), nave.getObra())) {
				if (logger.isErrorEnabled()) {
					logger.error(String.format("Ha ocurrido un error al guardar la nave por duplicidad %s",
							nave.toString()));
					throw new NaveExistsException("Nave y obra existente");
				}

			} else {
				Nave naveEntity = naveRepository.save(convertDTOToEntity(nave));
				NaveDto naveDtoResponse = convertEntityToDTO(naveEntity);
				if (logger.isDebugEnabled()) {
					logger.debug(String.format("Datos guardados %s", naveDtoResponse.toString()));
				}

			}

		} catch (NaveExistsException e) {
			throw e;

		} catch (Exception e) {
			logger.error(String.format("Ha ocurrido un error al guardar la nave %s", nave.toString()), e);
			throw new NaveOperationException("Error al guardar la nave");
		}

		return nave;
	}

	@Override
	@CachePut(value = "naves", key = "#naveRequest.id")
	public NaveDto modificarNave(NaveDto naveRequest)
			throws NaveNotFoundException, NaveOperationException, NaveExistsException {
		
		logger.debug("Entrando al metodo modificarNave");
		try {

			Optional<Nave> naveBaseDatos = naveRepository.findById(naveRequest.getId());

			/*
			 * 1. Verificar si hay cambios en los datos recibidos. 
			 * 2. Si hay cambios, se debe validar si existe en base de datos los valores enviados.
			 */

			if (naveBaseDatos.isPresent()) {

				Nave actualizarNave = naveBaseDatos.get();
				this.actualizaSiHayCambios(naveRequest, actualizarNave);
				
			} else {
				if (logger.isErrorEnabled()) {
					logger.error(
							String.format("Ha ocurrido un error al actualizar la nave %s", naveRequest.toString()));
				}
				throw new NaveNotFoundException("No se encontró la nave con ID: " + naveRequest.getId());

			}
			return naveRequest;

		} catch (NaveExistsException i) {
			if (logger.isErrorEnabled()) {
				logger.error(String.format("Error insertando nave, ya existe %s", naveRequest.toString()));
			}
			throw i;
		} catch (NaveNotFoundException e) {
			if (logger.isErrorEnabled()) {
				logger.error(String.format("No existe el id para actualizar %s", naveRequest.toString()));
			}
			throw e;
		} catch (Exception e) {
			logger.error(String.format("Ha ocurrido un error al actualizar la nave %s", naveRequest.getId()), e);
			throw new NaveOperationException("Error al buscar nave");
		}
	}

	/**
	 * Método encargado de actualizar una nave solo si hay cambios.
	 * @param naveRequest
	 * @param actualizarNave
	 */
	private void actualizaSiHayCambios(NaveDto naveRequest, Nave actualizarNave) {
		
		// Si hay algún cambio, se verifica en base de datos la existencia de los nuevos
		// valores recibidos.
		if (!actualizarNave.getNombre().equals(naveRequest.getNombre())
				|| !actualizarNave.getObra().equals(naveRequest.getObra())) {

			this.existeNave(naveRequest);

			// Se actualiza registro en base de datos.
			actualizarNave.setNombre(naveRequest.getNombre());
			actualizarNave.setObra(naveRequest.getObra());
			naveRepository.save(actualizarNave);
			
			if (logger.isDebugEnabled()) {
				logger.debug(String.format("Actualizacion realizada %s", naveRequest.toString()));
			}

		}
	}

	/**
	 * Verifica si existe la nave.
	 * @param naveRequest
	 */
	private void existeNave(NaveDto naveRequest) {

		boolean existeNave = existeNave(naveRequest.getNombre(), naveRequest.getObra());

		if (existeNave) {

			// Si existe el registro se lanza Exception
			if (logger.isErrorEnabled()) {
				logger.error(String.format("La nave ya existe nombre: %s obra: %s", naveRequest.getNombre(),
						naveRequest.getObra()));
			}

			throw new NaveExistsException("Error insertando nave, ya existe" + naveRequest.toString());
		}
	}

	@Override
	@CacheEvict(value = "naves", key = "#id")
	public void borrarNave(Long id) throws NaveNotFoundException, NaveOperationException {
		logger.debug("Entrando al metodo borrarNave");
		try {

			Optional<Nave> nave = naveRepository.findById(id);

			if (nave.isEmpty()) {
				if (logger.isErrorEnabled()) {
					logger.error(String.format("Ha ocurrido un error al borrar la nave %s", nave.toString()));
				}
				throw new NaveNotFoundException("No se encontró la nave con ID: " + id + " para ser borrada.");
			} else {
				if (logger.isDebugEnabled()) {
					logger.debug(String.format("Ha borrado la nave exitosamente %s", nave.toString()));
				}

				naveRepository.deleteById(id);
			}

		} catch (NaveNotFoundException e) {
			throw e;
		} catch (Exception e) {
			logger.error(String.format("Ha ocurrido un error al intentar borrar la nave %s", id), e);
			throw new NaveOperationException("Error al borrar nave");
		}
	}

	/**
	 * Metodo convertidor de entidad a dto
	 */
	private static NaveDto convertEntityToDTO(Nave entity) {
		if (entity != null && entity.getNombre() != null) {
			entity.setNombre(entity.getNombre().toUpperCase());
		}
		return objectMapper.convertValue(entity, NaveDto.class);
	}

	/**
	 * Metodo convertidor de dto a entidad
	 */
	private static Nave convertDTOToEntity(NaveDto dto) {
		return objectMapper.convertValue(dto, Nave.class);
	}

}
