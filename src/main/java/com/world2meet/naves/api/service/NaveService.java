package com.world2meet.naves.api.service;

/**
 * métodos  que se implementaran en el servicio de implementación 
 */

import org.springframework.data.domain.Page;

import com.world2meet.naves.api.dto.NaveDto;
import com.world2meet.naves.api.entity.Nave;
import com.world2meet.naves.api.exception.NaveExistsException;
import com.world2meet.naves.api.exception.NaveNotFoundException;
import com.world2meet.naves.api.exception.NaveOperationException;

/**
 * 
 * @author edgar.laucho Interfaz de los servicio.
 */
public interface NaveService {

	/**
	 * Metodo lista todas las naves.
	 * 
	 * @param numeroPagina : Parametro opcional indica el numero de pagina que se
	 *                     esta consultando.
	 * @param tamano       : Parametro opcional indica el total de registro por
	 *                     pagina.
	 * @return : Lista de naves.
	 * @throws NaveNotFoundException  : No existe la nave.
	 * @throws NaveOperationException : Ha ocurrido un error interno en el servidor.
	 * @throws NaveExistsException    : Información duplicada.
	 */
	Page<Nave> navePaginacion(int numeroPagina, int tamano) throws NaveOperationException;

	/**
	 * Metodo listar naves por nombre.
	 * 
	 * @param nombre       : Nombre nave.
	 * @param numeroPagina : Parametro opcional indica el numero de pagina que se
	 *                     esta consultando.
	 * @param tamano       : Parametro opcional indica el total de registro por
	 *                     pagina.
	 * @return : Lista de naves por nombre paginadas.
	 * @throws NaveOperationException : Ha ocurrido un error interno en el servidor.
	 * @throws NaveNotFoundException  : No existe la nave.
	 */
	Page<Nave> obtenerNombres(String nombre, int numeroPagina, int tamano)
			throws NaveOperationException, NaveNotFoundException;

	/**
	 * Metodo busqueda por identificador
	 * 
	 * @param id : Identificador.
	 * @return : Nave por su identificador.
	 * @throws NaveNotFoundException  : NaveNotFoundException : No existe la nave.
	 * @throws NaveOperationException : Ha ocurrido un error interno en el servidor.
	 */
	NaveDto buscarId(Long id) throws NaveNotFoundException, NaveOperationException;

	/**
	 * Metodo guardar nave
	 * 
	 * @param nave : Nombre nave.
	 * @return : Nave guardada.
	 * @throws NaveOperationException : Ha ocurrido un error interno en el servidor.
	 * @throws NaveExistsException    : Información duplicada.
	 */
	NaveDto guardar(NaveDto nave) throws NaveOperationException, NaveExistsException;

	/**
	 * Metodo modificar nave por el identificador
	 * 
	 * @param id   : Identificador.
	 * @param nave : Nombre nave.
	 * @return : La nave modificada
	 * @throws NaveNotFoundException  : NaveNotFoundException : No existe la nave.
	 * @throws NaveOperationException : Ha ocurrido un error interno en el servidor.
	 * @throws NaveExistsException    : Información duplicada.
	 */
	NaveDto modificarNave(NaveDto nave)
			throws NaveNotFoundException, NaveOperationException, NaveExistsException;

	/**
	 * Metodo borrar nave
	 * 
	 * @param id : Identificador.
	 * @throws NaveNotFoundException : NaveNotFoundException : No existe la nave.
	 * @throws NaveOperationException : Ha ocurrido un error interno en el servidor.
	 */
	void borrarNave(Long id) throws NaveNotFoundException, NaveOperationException;
}
