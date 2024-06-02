package com.world2meet.naves.api.exception;

/**
 * Clase nave NaveNotFoundException
 */
public class NaveOperationException extends RuntimeException {

	/**
	 * Identificador serial versión.
	 */
	private static final long serialVersionUID = -2103528017373014657L;

	/**
	 * Contructor que genera la instancia con mensaje personalizado
	 * @param message : El mensaje que describe la excepcion
	 */
	public NaveOperationException(String message) {
		super(message);
	}

}
