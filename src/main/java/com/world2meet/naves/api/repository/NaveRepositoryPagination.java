package com.world2meet.naves.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.world2meet.naves.api.entity.Nave;

/**
 * Interfaz Repositorio para paginacion
 */

@Repository
public interface NaveRepositoryPagination extends PagingAndSortingRepository<Nave, Long> {

	/**
	 * Metodo busqueda por nombres parecidos.
	 * @param nombre : Nombre nave.
	 * @param paginación : Paginacion de las naves.
	 * @return : Retorna una busqueda segun el nombre paginado.
	 */
	@Query("SELECT n FROM Nave n WHERE n.nombre LIKE %:nombre%")
	Page<Nave> busquedaNombre(@Param("nombre") String nombre, Pageable paginacion);

}
