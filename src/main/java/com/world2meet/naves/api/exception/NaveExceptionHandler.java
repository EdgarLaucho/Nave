package com.world2meet.naves.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.world2meet.naves.api.response.NaveResponse;

/**
 * 
 * @author edgar.laucho Clese manejador de excepciones
 */
@ControllerAdvice
public class NaveExceptionHandler {

	/**
	 * Metodo NaveNotFoundException que devuelve un estado 404
	 * 
	 * @param notFoundTransactionException : Excepcion de datos no encontrados
	 * @return : 404
	 */

	@ExceptionHandler(value = NaveNotFoundException.class)
	protected ResponseEntity<NaveResponse> naveNotFoundException(NaveNotFoundException notFoundTransactionException) {

		NaveResponse naveResponse = new NaveResponse(notFoundTransactionException.getMessage());
		return new ResponseEntity<>(naveResponse, HttpStatus.NOT_FOUND);

	}// Fin naveNotFoundException

	/**
	 * Metodo NaveNotFoundException que devuelve un estado 500
	 * 
	 * @param naveOperationException : Excepcion para conflictos internos en el
	 *                               sistema
	 * @return : 500
	 */
	@ExceptionHandler(value = NaveOperationException.class)
	protected ResponseEntity<NaveResponse> naveOperationException(NaveOperationException naveOperationException) {

		NaveResponse naveResponse = new NaveResponse(naveOperationException.getMessage());
		return new ResponseEntity<>(naveResponse, HttpStatus.INTERNAL_SERVER_ERROR);

	}// Fin NaveOperationException

	/**
	 * Metodo NaveNotFoundException que devuelve un estado 409
	 * 
	 * @param naveExistsException : Excepcion para duplicidad de informacion
	 * @return : 409
	 */
	@ExceptionHandler(value = NaveExistsException.class)
	protected ResponseEntity<NaveResponse> naveExistsException(NaveExistsException naveExistsException) {

		NaveResponse naveResponse = new NaveResponse(naveExistsException.getMessage());
		return new ResponseEntity<>(naveResponse, HttpStatus.CONFLICT);

	}// Fin NaveExistsException

	/**
	 * Método que captura todos los errores diferente a las excepciones creadas
	 * NaveExistsException, NaveOperationException, NaveNotFoundException
	 * 
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(value = Throwable.class)
	protected ResponseEntity<NaveResponse> errorDesconocido(Throwable exception) {

		NaveResponse naveResponse = new NaveResponse(exception.getMessage());
		return new ResponseEntity<>(naveResponse, HttpStatus.INTERNAL_SERVER_ERROR);

	}

}
