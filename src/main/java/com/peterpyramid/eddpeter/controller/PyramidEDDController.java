package com.peterpyramid.eddpeter.controller;

import java.io.IOException;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;
import com.peterpyramid.eddpeter.services.Services;
import org.springframework.core.io.Resource;
import org.springframework.http.*;

@RestController
@RequestMapping("/")
public class PyramidEDDController {

	Services service = new Services();
	
	/***
	 * CREAR ARBOL Y AGREGAR CARTAS
	 * 
	 * @param nodo
	 * @return respuesta del servidor
	 */
	@RequestMapping(value = "/Game/start", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<String> insertarStart(@RequestBody String jsonInicio) {

		ResponseEntity<String> respuesta = service.start(jsonInicio);
		return respuesta;
	}

	/***
	 * INSERTAR CARTAS AL ARBOL CREADO
	 * 
	 * @param gson
	 * @return respuesta del servidor
	 */
	@RequestMapping(value = "/Game/add", consumes = "application/json", method = RequestMethod.POST)
	public ResponseEntity<String> insertar(@RequestBody String jsonInsertar) {

		ResponseEntity<String> respuesta = service.addArbol(jsonInsertar);
		return respuesta;

	}

	/***
	 * OBTENER RECORRIDO DEL ARBOL
	 * 
	 * @param move
	 * @return respuesta del servidor
	 */

	@GetMapping(value = "/Game/avltree", produces = "application/json")
	public ResponseEntity<String> ippAVL(@RequestParam(name = "transversal") String move) {

		ResponseEntity<String> respuesta = service.realizarRecorrido(move);
		return respuesta;
		

	}

	/***
	 * ELIMINAR DOS O UNA CARTA DEPENDIENDO EL VALOR
	 * 
	 * @param eliminarJson
	 * @return
	 */

	@RequestMapping(value = "/Game/delete", method = RequestMethod.DELETE, consumes = "application/json", produces = "application/json")
	public ResponseEntity<String> eliminar(@RequestBody String jsonDelete) {

		ResponseEntity<String> respuesta = service.eliminarCartas(jsonDelete);
		return respuesta;

	}

	// GRAFICO DEL ARBOL
	/***
	 * SE OBTIENE EL ESTADO GRAFICO DEL ARBOL AVL
	 * 
	 * @return respuesta del servidor
	 */
	@GetMapping(value = "/Game/status-avltree", produces = "application/json")
	public ResponseEntity<String> statusArbol(HttpServletRequest request) {
	
		// VER INFO DEL SERVIDOR
		Map<String, String> datos = service.url(request);
		
		return service.estadoGraficoAVL(datos);

	}

	/***
	 * SE REALIZA UNA REDIRECCION PARA MOSTRAR LA IMAGEN
	 * @return respuesta del servidor
	 * @throws IOException
	 */
	@GetMapping(value = "/Game/AVLStatus", produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<Resource> getImage() throws IOException {
		return service.getImagen();

	}
	
	/***
	 * Obtener los nodos en un nivel especifico
	 * @param nivel
	 * @return
	 */
	@GetMapping(value = "/Game/get-level", produces = "application/json")
	public ResponseEntity<String> getNivel(@RequestParam(name = "level") int nivel) {
	
		return service.getNiveles(nivel);	
		
	}
	
}
