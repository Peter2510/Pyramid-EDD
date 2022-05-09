package com.peterpyramid.eddpeter.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.springframework.http.*;
import org.springframework.core.io.*;
import com.google.gson.Gson;
import com.peterpyramid.eddpeter.tree.*;

public class Services {

	ArbolAVL arbolGeneral;
	AuxiliarArbol auxInsertarAlArbol = new AuxiliarArbol();
	RecorridoArbolAVL recorrerArbol;

	/***
	 * Constructor por defecto
	 */
	public Services() {

	}

	/***
	 * Se inserta nodo para iniciar el arbol
	 * 
	 * @param jsonInicio
	 * @return respuesta si se logro insertar
	 */

	public ResponseEntity<String> start(String jsonInicio) {

		arbolGeneral = auxInsertarAlArbol.arbolVacio(jsonInicio);

		// Si la carta duplicada lanzar error 406
		if (auxInsertarAlArbol.getErroresInsertar() == 406) {

			auxInsertarAlArbol.setErroresInsertar();
			arbolGeneral.setErroresArbol();
			return new ResponseEntity<>("Error 406 \nCarta duplicada", HttpStatus.NOT_ACCEPTABLE);

			// si es otro errro lanzar 400
		} else if (auxInsertarAlArbol.getErroresInsertar() == 400) {

			auxInsertarAlArbol.setErroresInsertar();
			arbolGeneral.setErroresArbol();
			return new ResponseEntity<>("Error 400 \nNo especificado", HttpStatus.BAD_REQUEST);

		} else {

			return new ResponseEntity<>("", HttpStatus.OK);
		}

	}

	/***
	 * Agregar cartas al arbol ya generado
	 * 
	 * @param jsonAdd
	 * @return respuesta si se logro agregar la carta
	 */

	public ResponseEntity<String> addArbol(String jsonAdd) {

		if (arbolGeneral == null) {

			return new ResponseEntity<>("Error 400 \nNo especificado", HttpStatus.BAD_REQUEST);

		} else {

			arbolGeneral = auxInsertarAlArbol.addLetter(arbolGeneral, jsonAdd);

			// Si la carta esta duplicada lanza -> 406
			if (auxInsertarAlArbol.getErroresInsertar() == 406) {
				auxInsertarAlArbol.setErroresInsertar();
				arbolGeneral.setErroresArbol();
				return new ResponseEntity<>("Error 406 \nCarta duplicada", HttpStatus.NOT_ACCEPTABLE);

				// Si es otro tipo de error lanza -> 400
			} else if (auxInsertarAlArbol.getErroresInsertar() == 400) {

				auxInsertarAlArbol.setErroresInsertar();
				arbolGeneral.setErroresArbol();
				return new ResponseEntity<>("Error 400 \nNo especificado", HttpStatus.BAD_REQUEST);

			} else {
				return new ResponseEntity<>("", HttpStatus.OK);
			}
		}

	}

	/***
	 * Se realiza un recorrido del arbol
	 * 
	 * @param tipoRecorrido
	 * @return Recorrido realizado segun corresponda
	 */

	public ResponseEntity<String> realizarRecorrido(String tipoRecorrido) {

		Gson auxJson = new Gson();
		recorrerArbol = new RecorridoArbolAVL();

		// Si el arbol existe y tiene contenido
		if (!(arbolGeneral.isEmpty() || tipoRecorrido == null || arbolGeneral == null)) {

			if (tipoRecorrido.equals("inOrder")) {

				Map<String, String> inOrder = recorrerArbol.inOrder(arbolGeneral);
				arbolGeneral.setRecorridos();
				String inOrderJson = auxJson.toJson(inOrder);
				return new ResponseEntity<>(inOrderJson, HttpStatus.OK);

			} else if (tipoRecorrido.equals("preOrder")) {

				Map<String, String> preOrder = recorrerArbol.preOrder(arbolGeneral);
				arbolGeneral.setRecorridos();
				String jsonPreOrden = auxJson.toJson(preOrder);
				return new ResponseEntity<>(jsonPreOrden, HttpStatus.OK);

			} else if (tipoRecorrido.equals("postOrder")) {

				Map<String, String> postOrder = recorrerArbol.postOrder(arbolGeneral);
				arbolGeneral.setRecorridos();
				String jsonPostOrden = auxJson.toJson(postOrder);
				return new ResponseEntity<>(jsonPostOrden, HttpStatus.OK);

			} else {
				return new ResponseEntity<>("Error 400 \nNo especificado", HttpStatus.BAD_REQUEST);
			}

		} else {
			return new ResponseEntity<>("Error 400 \nNo especificado", HttpStatus.BAD_REQUEST);
		}
	}

	/* GRAFICO DEL ARBOL */

	/***
	 * Grafico del arbol
	 * 
	 * @return
	 */
	public ResponseEntity<String> estadoGraficoAVL(Map<String, String> datos) {

		if (arbolGeneral.isEmpty() || arbolGeneral == null) {

			return new ResponseEntity<>("Error 400 \nNo especificado", HttpStatus.BAD_REQUEST);

		} else {

			arbolGeneral.ejecutarCodigoGraphviz();
			arbolGeneral.getCodeGraphviz();

			// se obtienen los datos por separado del url leido en "url"
			String protocol = datos.get("Protocol");
			String host = datos.get("Host");
			String portTmp = datos.get("Port");
			int port = Integer.parseInt(portTmp);
			Gson json = new Gson();

			Map<String, String> url = new LinkedHashMap<String, String>();

			if (port > -1) {
				// http://localhost:8080/Game/status-avltree
				String data;

				data = protocol + ":" + "//" + host + ":" + portTmp + "/Game/AVLStatus";

				url.put("path", data);

				String respuesta = json.toJson(url);

				return new ResponseEntity<>(respuesta, HttpStatus.OK);

			} else {

				// es ngrok
				// https://host.ngrok.io
				String data;

				data = protocol + ":" + "//" + host + "/" + "Game/AVLStatus";

				url.put("path", data);

				String respuesta = json.toJson(url);

				return new ResponseEntity<>(respuesta, HttpStatus.OK);

			}

		}

	}

	public ResponseEntity<Resource> getImagen() throws IOException {

		final ByteArrayResource img = new ByteArrayResource(
				Files.readAllBytes(Paths.get(System.getProperty("user.dir") + "\\EstadoArbolAVL.jpg")));

		return ResponseEntity.status(HttpStatus.OK).contentLength(img.contentLength()).body(img);

	}

	public Map<String, String> url(HttpServletRequest request) {

		String urlEntrante = request.getRequestURL().toString();
		URL url;
		String host = " ";
		int port = 0;
		String protocol = " ";
		// String result = "";
		Map<String, String> redireccion = new LinkedHashMap<String, String>();

		try {

			url = new URL(urlEntrante);
			protocol = url.getProtocol();
			host = url.getHost();
			port = url.getPort();

			// result = protocol + " " + host + " " + port;
			redireccion.put("Protocol", "" + protocol + "");
			redireccion.put("Host", "" + host + "");
			redireccion.put("Port", "" + port + "");

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return redireccion;

	}

	/* ELIMINAR LA CARTA */
	public ResponseEntity<String> eliminarCartas(String eliminarJson) {

		Gson jsonAux = new Gson();
		datosCarta dato = new datosCarta();

		Map<?, ?> cartaEliminar = jsonAux.fromJson(eliminarJson, Map.class);

		// si lo que ingresa esta vacio, es mayor a dos cartas o el arbol es null
		// retorna error

		if (cartaEliminar.isEmpty() || cartaEliminar.size() > 2 || arbolGeneral == null) {

			return new ResponseEntity<>("Error 400 \nNo especificado", HttpStatus.BAD_REQUEST);

		}

		// si no intenta eliminar
		else {

			if (cartaEliminar.size() == 1) {

				if (cartaEliminar.containsKey("delete_1")) {

					String nombre = dato.getNombreCarta((String) cartaEliminar.get("delete_1"));
					String tipo = dato.obtenerTipoCarta((String) cartaEliminar.get("delete_1"));
					int valor = dato.getValorCarta(nombre, tipo);
					int valReal = dato.valorTipo(valor, tipo);
					// System.out.println(valor);
					// System.out.println(valReal);

					if (valor == 13) {

						return eliminarCarta(valReal);

					} else {
						return new ResponseEntity<>("Error 406 \nNo suma 13", HttpStatus.NOT_ACCEPTABLE);
					}

				} else {
					return new ResponseEntity<>("Error 400 \nNo especificado", HttpStatus.BAD_REQUEST);
				}

			} else {

				if (cartaEliminar.containsKey("delete_1") && cartaEliminar.containsKey("delete_2")) {

					String nombre = dato.getNombreCarta((String) cartaEliminar.get("delete_1"));
					String tipo = dato.obtenerTipoCarta((String) cartaEliminar.get("delete_1"));
					int valor = dato.getValorCarta(nombre, tipo);
					int valReal = dato.valorTipo(valor, tipo);

					String nombre2 = dato.getNombreCarta((String) cartaEliminar.get("delete_2"));
					String tipo2 = dato.obtenerTipoCarta((String) cartaEliminar.get("delete_2"));
					int valor2 = dato.getValorCarta(nombre2, tipo2);
					int valReal2 = dato.valorTipo(valor2, tipo2);

					// System.out.println(valor);
					// System.out.println(valor2);
					int suma = valor + valor2;

					Nodo carta1 = arbolGeneral.buscarEnArbolAVL(valReal, arbolGeneral.getRaizArbol());
					Nodo carta2 = arbolGeneral.buscarEnArbolAVL(valReal2, arbolGeneral.getRaizArbol());

					if (carta1 != null && carta2 != null) {
						if (carta1.nodoInterno() == false && carta2.nodoInterno() == false) {

							if (suma == 13) {

								ResponseEntity<String> condicion1 = eliminarCarta(valReal);

								if (condicion1.getStatusCode() == HttpStatus.OK) {
									ResponseEntity<String> condicion2 = eliminarCarta(valReal2);

									if (condicion2.getStatusCode() == HttpStatus.OK) {
										return new ResponseEntity<>("", HttpStatus.OK);
									} else {
										return condicion2;
									}

								} else {
									return condicion1;
								}

							} else {
								return new ResponseEntity<>("Error 406 \nNo suma 13", HttpStatus.NOT_ACCEPTABLE);
							}
						} else {
							return new ResponseEntity<>("Error 409 \nLa carta cuenta con hijos", HttpStatus.CONFLICT);
						}
					} else {
						return new ResponseEntity<>("Error 404 \nLa carta no se encuentra en el arbol",
								HttpStatus.NOT_FOUND);
					}

				} else {

					return new ResponseEntity<>("Error 400 \nNo especificado", HttpStatus.BAD_REQUEST);
				}
			}

		}

	}

	/***
	 * Eliminar carta unitaria
	 * @param valor
	 * @return
	 */
	private ResponseEntity<String> eliminarCarta(int valor) {

		if (arbolGeneral != null) {

			if ((arbolGeneral.buscarEnArbolAVL(valor, arbolGeneral.getRaizArbol())) != null) {

				arbolGeneral.eliminar(valor);

				if (arbolGeneral.getErroresArbol() == 409) {
					arbolGeneral.setErroresArbol();
					return new ResponseEntity<>("Error 409 \nLa carta cuenta con hijos", HttpStatus.CONFLICT);
				} else {
					// String ymm = arbol.inOrden(arbol.getRaizArbol());
					return new ResponseEntity<>("", HttpStatus.OK);
				}

			} else {
				return new ResponseEntity<>("Error 404 \nLa carta no se encuentra en el arbol", HttpStatus.NOT_FOUND);
			}

		} else {
			return new ResponseEntity<>("Error 400 \nNo especificado", HttpStatus.BAD_REQUEST);
		}
	}

	private int valorRealCarta(int valor, String tipo) {
		int real = 0;
		switch (tipo) {
		case "♥":
			real = valor - 40;
			break;
		case "♦":
			real = valor - 20;
			break;
		case "♣":
			real = valor - 0;
			break;
		case "♠":
			real = valor - 60;
			break;
		}
		System.out.print(real);
		return real;
	}
	
	/***
	 * Obtener cartas de nivel especifico solicitado
	 * @param nivel
	 * @return
	 */

	public ResponseEntity<String> getNiveles(int nivel) {

		nivel = nivel - 1;
		int count = 0;

		if (arbolGeneral.isEmpty() == false || arbolGeneral != null) {

			ArrayList<datosNivel> nodoNivel = arbolGeneral.niveles();

			Map<String, String> nodosPorNivel = new LinkedHashMap<String, String>();

			for (int i = 0; i < nodoNivel.size(); i++) {

				int level = nodoNivel.get(i).getNivel();

				if (nivel == level) {
					// System.out.println("s "+e.get(i).getNodo().toString());
					nodosPorNivel.put("" + count + "", nodoNivel.get(i).getNodo().toString());
					count++;
				}

			}

			if (nodosPorNivel.isEmpty()) {
				return new ResponseEntity<>("Error 400 \nNo especificado", HttpStatus.BAD_REQUEST);
			}

			Gson aux = new Gson();
			String respuestaJson = aux.toJson(nodosPorNivel);
			// System.out.println(nodoNivel.size());

			return new ResponseEntity<>(respuestaJson, HttpStatus.OK);
		} else {

			return new ResponseEntity<>("Error 400 \nNo especificado", HttpStatus.BAD_REQUEST);
		}

	}

}
