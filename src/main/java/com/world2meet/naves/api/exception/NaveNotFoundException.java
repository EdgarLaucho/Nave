package com.world2meet.naves.api.exception;

/**
 * clase nave NaveNotFoundException.
 */
public class NaveNotFoundException extends RuntimeException {

	/**
	 * Identificador serial versión.
	 */

	private static final long serialVersionUID = 3326465416666221363L;

	/**
	 * Contructor que genera la instancia con mensaje personalizado
	 * @param message : El mensaje que describe la excepcion
	 */
	public NaveNotFoundException(String message) {
		super(message);
	}
}
