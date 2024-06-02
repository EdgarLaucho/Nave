package com.world2meet.naves.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.world2meet.naves.api.dto.NaveDto;
import com.world2meet.naves.api.entity.Nave;
import com.world2meet.naves.api.exception.NaveExistsException;
import com.world2meet.naves.api.exception.NaveNotFoundException;
import com.world2meet.naves.api.exception.NaveOperationException;
import com.world2meet.naves.api.service.NaveService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

/**
 * 
 * Controller encargado de realizar las operaciones CRUD:
 * <ul>
 * <li>C: Create.</li>
 * <li>R: Read.</li>
 * <li>U: Update.</li>
 * <li>D: Delete.</li>
 * <li>Listado de naves paginadas.</li>
 * <li>Listado por identificador de nave.</li>
 * </ul>
 */
@RestController
@RequestMapping("/api/nave")
public class NaveController {

	/*** Se inyecta dependecia naveService. */
	@Autowired
	private NaveService naveService;

	/**
	 * Instancia de logger para trazas.
	 */
	private static final Logger logger = LoggerFactory.getLogger(NaveController.class);

	@Operation(summary = "Método que guarda una nave en base de datos.")
	@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Json", content = @Content(schema = @Schema(implementation = NaveDto.class)))
	@ApiResponse(responseCode = "200", description = "Petición exitosa.")
	@ApiResponse(responseCode = "409", description = "Información duplicada.")
	@ApiResponse(responseCode = "500", description = "Ha ocurrido un error interno en el servidor.")
	/**
	 * Metodo que guarda una nave en base de datos.
	 * 
	 * @param nave : Datos enviados por el usuario.
	 * @return : Los datos guardados de la nave.
	 * @throws NaveOperationException : Ha ocurrido un error interno en el servidor.
	 * @throws NaveExistsException    : Información duplicada.
	 */
	@PostMapping("/guardar")
	public ResponseEntity<NaveDto> guardarNave(@RequestBody @Valid NaveDto nave)
			throws NaveOperationException, NaveExistsException {

		if (logger.isDebugEnabled()) {
			logger.debug(String.format("Entrada en guardarNave con los siguientes párametros %s", nave.toString()));
		}
		return new ResponseEntity<>(naveService.guardar(nave), HttpStatus.CREATED);
	}

	@Operation(summary = "Metodo que realiza la busqueda de naves por nombre paginadas.")
	@Parameter(name = "nombre", description = "Nombre a para buscar la nave.")
	@Parameter(name = "numeroPagina", description = "Parametro opcional ndica el numero de pagina que se esta consultando.")
	@Parameter(name = "tamano", description = "Parametro opcional indica el total de registro por pagina.")
	@ApiResponse(responseCode = "404", description = "No existe el parametro.")
	@ApiResponse(responseCode = "500", description = "Ha ocurrido un error interno en el servidor.")

	/**
	 * Metodo que realiza la busqueda de naves por nombre paginadas.
	 * 
	 * @param nombre       : Nombre a para buscar la nave.
	 * @param numeroPagina : Parametro opcional indica el numero de pagina que se
	 *                     esta consultando.
	 * @param tamano       : Parametro opcional indica el total de registro por
	 *                     pagina.
	 * @return : Devuelve los nombres parecidos a los ingresados.
	 * @throws NaveNotFoundException  : No existe la nave.
	 * @throws NaveOperationException :Ha ocurrido un error interno en el servidor
	 */
	@GetMapping("/buscar-por-nombre/{nombre}")
	public ResponseEntity<Page<Nave>> buscarPorNombre(@PathVariable(required = true) String nombre,
			@RequestParam(defaultValue = "0") int numeroPagina, @RequestParam(defaultValue = "8") int tamano)
			throws NaveNotFoundException, NaveOperationException {
		if (logger.isDebugEnabled()) {
			logger.debug(String.format("Entrada en buscarPorNombre con los siguientes parámetros: %s, %d, %d", nombre,
					numeroPagina, tamano));
		}

		Page<Nave> naves = naveService.obtenerNombres(nombre, numeroPagina, tamano);
		return new ResponseEntity<>(naves, HttpStatus.OK);

	}

	@Operation(summary = "Metodo que lista las naves existentes paginadas")
	@Parameter(name = "numeroPagina", description = "Parametro opcional indica el numero de pagina que se esta consultando")
	@Parameter(name = "tamano", description = "Cantidad de naves a recibir por hoja")
	@ApiResponse(responseCode = "500", description = "Ha ocurrido un error interno en el servidor.")
	/**
	 * Metodo que lista las naves existentes paginadas.
	 * 
	 * @param numeroPagina : Parametro opcional indica el numero de pagina que se
	 *                     esta consultando.
	 * @param tamano       : Parametro opcional indica el total de registro por
	 *                     pagina.
	 * @return : Lista de registros paginados.
	 */
	@GetMapping("/obtener-listado")
	public ResponseEntity<Page<Nave>> obtenerNavesPaginadas(@RequestParam(defaultValue = "0") int numeroPagina,
			@RequestParam(defaultValue = "8") int tamano) throws NaveOperationException {
		logger.debug("Entrada en obtener listado de nave");
		Page<Nave> naves = naveService.navePaginacion(numeroPagina, tamano);
		return new ResponseEntity<>(naves, HttpStatus.OK);
	}

	@Operation(summary = "Método que obtiene  la nave por su identificador.")
	@Parameter(name = "id", description = "Identificador para la busqueda de la nave.")
	@ApiResponse(responseCode = "404", description = "No existe el parametro.")
	@ApiResponse(responseCode = "500", description = "Ha ocurrido un error interno en el servidor.")

	/**
	 * Método que obtiene la nave por su identificador.
	 * 
	 * @param id :Identificador para la busqueda de la nave.")
	 * @return : Retorna la busqueda de la nave por identificador
	 * @throws NaveNotFoundException  :No existe el parametro.
	 * @throws NaveOperationException :Ha ocurrido un error interno en el servidor.
	 */
	@GetMapping("/buscar-por-identificador/{id}")
	public ResponseEntity<NaveDto> buscarId(@PathVariable Long id)
			throws NaveNotFoundException, NaveOperationException {
		
		if (logger.isDebugEnabled()) {
			logger.debug(String.format("Entrada en busqueda por identificador %s", id));
		}
		NaveDto nave = naveService.buscarId(id);
		return ResponseEntity.ok(nave);

	}

	@Operation(summary = "Método que actualiza una nave por el identificador.")
	@ApiResponse(responseCode = "404", description = "No existe el parametro.")
	@ApiResponse(responseCode = "409", description = "Información duplicada.")
	@ApiResponse(responseCode = "500", description = "Ha ocurrido un error interno en el servidor.")

	/**
	 * Método que actualiza una nave por el identificador.
	 * 
	 * @param id   : Identificador para la busqueda de la nave.
	 * @param nave : Datos enviados por el usuario.
	 * @return : Datos actualizados en objeto NaveDto.
	 * @throws NaveOperationException : Ha ocurrido un error interno en el servidor.
	 * @throws NaveNotFoundException  : No existe la nave.
	 * @throws NaveExistsException    : Información duplicada.
	 */

	@PutMapping("/actualizar")
	public ResponseEntity<NaveDto> modificarNave(@RequestBody NaveDto nave)
			throws NaveOperationException, NaveNotFoundException, NaveExistsException {

		if (logger.isDebugEnabled()) {
			logger.debug(String.format("Entrada en actualizar con los siguientes parámetros: %s", nave.toString()));
		}
		return new ResponseEntity<>(naveService.modificarNave(nave), HttpStatus.CREATED);

	}

	@Operation(summary = "Método que borra una nave por el identificador.")
	@Parameter(name = "id", description = "Identificador para la busqueda de la nave.")
	@ApiResponse(responseCode = "404", description = "No existe el parametro.")
	@ApiResponse(responseCode = "409", description = "Información duplicada.")
	@ApiResponse(responseCode = "500", description = "Ha ocurrido un error interno en el servidor.")

	/**
	 * Método que borra una nave por el identificador.
	 * 
	 * @param id : Identificador para la busqueda de la nave.
	 * @return
	 * @throws NaveOperationException : Ha ocurrido un error interno en el servidor.
	 * @throws NaveNotFoundException  : No existe la nave.
	 */
	@DeleteMapping("/borrar/{id}")
	public ResponseEntity<?> borrarNave(@PathVariable Long id) throws NaveOperationException, NaveNotFoundException {
		
		if (logger.isDebugEnabled()) {
			logger.debug(String.format("Entrada en borra  la nave por identificador %s", id));
		}
		naveService.borrarNave(id);
		return ResponseEntity.noContent().build();
	}
}
