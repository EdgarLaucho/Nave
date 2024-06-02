# Prueba técnica Spring Boot.
Se realizan las siguientes actividades:

1. Consultar todas las naves utilizando paginación: Se muestra todo el listado de naves paginados.
2. Consultar una única nave por id.
3. Consultar todas las naves que contienen,en su nombre, el valor de un parámetro enviado en la petición: Se buscan naves por el nombre con el resultado paginado.
4. Crear una nueva nave.
5. Modificar una nave: Se modifica una nave por el identificador ingresado.
6. Eliminar una nave: Se elimina la nave por identificador.
7. Test unitario de como mínimo de una clase: Test sobre la clase controlador.
8. Desarrollar un aspecto que añada una línea de log cuando nos piden una nave con un identificador negativo: Cuando se realiza una consulta por identificador negativo, el aspecto intercepta la peticion y se valida el identificador para luego generar un mensaje en el log.
9. Gestión centralizada de excepciones.
10. Utilizar caches de algún tipo: Se genera cache en buscar por identificador @Cacheable, se actualiza al modificar la nave @CachePut, y se elimina el registro la cache con @CacheEvict.

## Opcionales
Se agregan trazas y se crea fichero logback.xml.
- Se utilizo el plugin de SonarLint para solucionar code smell.
- Documentación de la api: Se realiza documentación  con swagger [URL](http://localhost:8185/swagger-ui/index.html#) y javadoc en el código.
- Se crea collection de postman: [URL](https://github.com/EdgarLaucho/naves/blob/develop/src/main/resources/Naves.postman_collection.json)  
- Docker: Se crea fichero Dockerfile y docker-compose nave-dc.yml:
```sh
Construir imagen con el jar y el Dockerfile
docker build -t naves-1.0.0.jar . 
Iniciar contenedor docker con fichero docker compose
docker-compose -f nave-dc.yml up -d
3. Consultar logs
docker logs -f identificadorContenedor
```
