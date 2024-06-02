package com.world2meet.naves.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Datos esperados por el usuario para transferir a la entidad
 */
public class NaveDto {

	/**
	 * Identificador de la nave del dto.
	 */
	private Long id;
	/**
	 * Nombre de la nave
	 */
	@NotBlank(message = "El nombre es obligatorio")
	@Size(max = 20)
	private String nombre;

	/**
	 * Pelicula o serie donde ha aparecido la nave.
	 */
	@NotBlank(message = "La obra es obligatoria")
	private String obra;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		if (nombre != null) {
	        return nombre.toUpperCase();
	    } else {
	        return null; 
	    }
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		if (nombre != null) {
	        this.nombre = nombre.toUpperCase();
	    } else {
	        this.nombre = null;
	    }
	}

	/**
	 * @return the obra
	 */
	public String getObra() {
		if (obra != null) {
	        return obra.toUpperCase();
	    } else {
	        return null; 
	    }
	}

	/**
	 * @param obra the obra to set
	 */
	public void setObra(String obra) {
		if (obra != null) {
	        this.obra = obra.toUpperCase();
	    } else {
	        this.obra = null; 
	    }
	}

	/**
	 * Metodo toString 
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("NaveDto [");
		if (id != null) {
			builder.append("id=");
			builder.append(id);
			builder.append(", ");
		}
		if (nombre != null) {
			builder.append("nombre=");
			builder.append(nombre);
			builder.append(", ");
		}
		if (obra != null) {
			builder.append("obra=");
			builder.append(obra);
		}
		builder.append("]");
		return builder.toString();
	}

}
