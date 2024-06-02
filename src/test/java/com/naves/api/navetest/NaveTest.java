package com.naves.api.navetest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.world2meet.naves.api.controller.NaveController;
import com.world2meet.naves.api.dto.NaveDto;
import com.world2meet.naves.api.entity.Nave;
import com.world2meet.naves.api.exception.NaveExistsException;
import com.world2meet.naves.api.exception.NaveNotFoundException;
import com.world2meet.naves.api.exception.NaveOperationException;
import com.world2meet.naves.api.service.NaveService;
/**
 * Clase de test sobre el controller
 */
@ExtendWith(MockitoExtension.class)
class NaveTest {
	
	
	

		/**
		 * Mock del servicio
		 */
	    @Mock
	    private NaveService naveService;
	    
	    /**
	     * Clase que contiene al servicio
	     */

	    @InjectMocks
	    private NaveController naveController;

	    /**
	     * Constructor del test
	     */
	    private NaveDto createNaveDto(String nombre, String obra) {
	        NaveDto naveDto = new NaveDto();
	        naveDto.setNombre(nombre);
	        naveDto.setNombre(obra);
	        // Establecer otros campos según sea necesario
	        return naveDto;
	    }

	    /**
	     * Test guardar nave
	     */
	    @Test
	    void testGuardarNave() throws NaveOperationException, NaveExistsException {
	        NaveDto naveDto = createNaveDto("Nave 1", "Obra1"); 
	        when(naveService.guardar(any(NaveDto.class))).thenReturn(naveDto);
	        ResponseEntity<?> responseEntity = naveController.guardarNave(naveDto);
	        assertNotNull(responseEntity);
	        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
	        assertEquals(naveDto, responseEntity.getBody());
	    }
	    @Test
	    void testBuscarPorNombre() throws NaveNotFoundException, NaveOperationException {
	        // Configurar datos de prueba
	        String nombre = "nombre de la nave";
	        int numeroPagina = 0;
	        int tamano = 8;
	        Page<Nave> naves = new PageImpl<>(Collections.emptyList());
	        
	        // Simular el comportamiento del servicio
	        when(naveService.obtenerNombres(nombre, numeroPagina, tamano)).thenReturn(naves);
	        
	        // Ejecutar el método del controlador
	        ResponseEntity<?> responseEntity = naveController.buscarPorNombre(nombre, numeroPagina, tamano);
	        
	        // Verificar la respuesta
	        assertNotNull(responseEntity);
	        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	        assertEquals(naves, responseEntity.getBody());
	        //hola
	    }
	    
	    @Test
	    void testObtenerNavesPaginadas() throws NaveOperationException {
	        // Configurar datos de prueba
	        int numeroPagina = 0;
	        int tamano = 8;
	        Page<Nave> naves = new PageImpl<>(Collections.emptyList());
	        
	        // Simular el comportamiento del servicio
	        when(naveService.navePaginacion(numeroPagina, tamano)).thenReturn(naves);
	        
	        // Ejecutar el método del controlador
	        ResponseEntity<?> responseEntity = naveController.obtenerNavesPaginadas(numeroPagina, tamano);
	        
	        // Verificar la respuesta
	        assertNotNull(responseEntity);
	        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	        assertEquals(naves, responseEntity.getBody());
	    }
	    
	    @Test
	    void testBuscarPorId() throws NaveNotFoundException, NaveOperationException {
	        // Configurar datos de prueba
	        Long id = 1L;
	        NaveDto naveDto = new NaveDto(); // Supongamos que aquí construyes un objeto NaveDto válido
	        
	        // Simular el comportamiento del servicio
	        when(naveService.buscarId(id)).thenReturn(naveDto);
	        
	        // Ejecutar el método del controlador
	        ResponseEntity<?> responseEntity = naveController.buscarId(id);
	        
	        // Verificar la respuesta
	        assertNotNull(responseEntity);
	        assertEquals(naveDto, responseEntity.getBody());
	    }
	    
	    @Test
	    void testModificarNave() throws NaveOperationException, NaveNotFoundException, NaveExistsException {
	        // Configurar datos de prueba
	        NaveDto naveDto = new NaveDto(); // Supongamos que aquí construyes un objeto NaveDto válido
	        
	        // Simular el comportamiento del servicio
	        when(naveService.modificarNave(naveDto)).thenReturn(naveDto);
	        
	        // Ejecutar el método del controlador
	        ResponseEntity<?> responseEntity = naveController.modificarNave(naveDto);
	        
	        // Verificar la respuesta
	        assertNotNull(responseEntity);
	        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
	        assertEquals(naveDto, responseEntity.getBody());
	    }
	    
	    @Test
	    void testBorrarNave() throws NaveOperationException, NaveNotFoundException {
	        // Configurar datos de prueba
	        Long id = 1L;
	        
	        // Ejecutar el método del controlador
	        ResponseEntity<?> responseEntity = naveController.borrarNave(id);
	        
	        // Verificar la respuesta
	        assertNotNull(responseEntity);
	        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
	        
	        // Verificar que el método del servicio fue invocado con el id correcto
	        verify(naveService).borrarNave(id);
	    }
}
