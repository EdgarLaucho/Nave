package com.world2meet.naves.api.exception;

/**
 * 
 * @author edgar.laucho Clase NaveExistsException
 */
public class NaveExistsException extends RuntimeException {

	/**
	 * Identificador serial versión.
	 */
	private static final long serialVersionUID = 6711127291645598484L;

	/**
	 * Contructor que genera la instancia con mensaje personalizado
	 * @param message : El mensaje que describe la excepcion
	 */
	public NaveExistsException(String message) {
		super(message);
	}

}
