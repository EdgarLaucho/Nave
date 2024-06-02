package com.world2meet.naves.api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Clase entidad
 */
@Entity
public class Nave {

	/**
	 * Identificador de la nave
	 */

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * Nombre de la nave
	 */
	@Column(length = 20, nullable = false)
	private String nombre;

	/**
	 * Serie o pelicula de la nave
	 */
	@Column(length = 20, nullable = false)
	private String obra;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 
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
	 * Método constructor
	 * 
	 * @param id     : Identificador de la nave
	 * @param nombre : Nombre de la nave
	 * @param obra   : Nombre de la serio o pelicula
	 */
	public Nave(Long id, String nombre, String obra) {
		super();
		this.id = id;
		this.nombre = nombre.toUpperCase();
		this.obra = obra.toUpperCase();
	}

	/**
	 * Constructor vacío necesario para Hibernate
	 */
	public Nave() {
		super();
	}
}
