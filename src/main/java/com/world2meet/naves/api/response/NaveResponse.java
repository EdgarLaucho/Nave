package com.world2meet.naves.api.response;

public class NaveResponse {
	
	public NaveResponse(String mensaje) {
		super();
		this.mensaje = mensaje;
	}

	/**
	 * Mensaje de error.
	 */
	String mensaje;

	/**
	 * @return the mensaje
	 */
	public String getMensaje() {
		return mensaje;
	}

	/**
	 * @param mensaje the mensaje to set
	 */
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("NaveResponse [");
		if (mensaje != null) {
			builder.append("mensaje=");
			builder.append(mensaje);
		}
		builder.append("]");
		return builder.toString();
	}

}
