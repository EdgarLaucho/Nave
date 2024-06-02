package com.world2meet.naves.api.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 
 * @author edgar.laucho Clase Aspect
 */
@Aspect
@Component
public class NaveAspect {

	/**
	 * Instancia de logger para trazas.
	 */
	private static final Logger logger = LoggerFactory.getLogger(NaveAspect.class);

	/**
	 * Punto de intercepcion del metodo buscarId capturando el id.
	 */
	@Pointcut("execution(* com.world2meet.naves.api.service.implement.NaveServiceImplement.buscarId(..)) && args(id)")
	public void buscarIdPointcut(Long id) {
	}

	/**
	 * 
	 * Aspecto para mostrar advertencia de un id negativo.
	 */
	@Before("buscarIdPointcut(id)")
	public void logIdnegativo(Long id) {
		if (id < 0) {
			if (logger.isWarnEnabled()) {
				logger.warn(String.format("Se solicitó una nave con un ID negativo: %s", id));
			}
			
		}
	}
}
