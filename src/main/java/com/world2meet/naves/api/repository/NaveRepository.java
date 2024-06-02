package com.world2meet.naves.api.repository;

/**
 * clase para generar las consultas de la base de datos heredada de JpaRepository
 */
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.world2meet.naves.api.entity.Nave;

/**
 * 
 * @author edgar.laucho 
 * Interfaz repositorio de JPA
 */

@Repository
public interface NaveRepository extends JpaRepository<Nave, Long> {

	/**
	 * Metodo comparador de existencia nombre y obra.
	 * @param nombre : Nombre nave.
	 * @param obra : Seria o pelicula de la nave
	 * @return : Retorna un boolean
	 */
	boolean existsByNombreAndObra(String nombre, String obra);

}
