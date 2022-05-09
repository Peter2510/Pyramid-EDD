package com.peterpyramid.eddpeter.tree;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.Map;


public class AuxiliarArbol {
	int codigoError = 0;

	public AuxiliarArbol() {
	}

	/***
	 * SE INSERTA CUANDO EL ARBOL ESTA VACIO
	 * 
	 * @param json
	 * @return
	 */
	public ArbolAVL arbolVacio(String json) {

		ArbolAVL tree = new ArbolAVL();
		Gson aux = new Gson();
		Map<?, ?> ingresoCarta = aux.fromJson(json, Map.class);
		int count = 0;
		datosCarta dato = new datosCarta();

		while (ingresoCarta.containsKey(""+count+"")) {

			try {
				
				String letterData = (String) ingresoCarta.get(""+count+"");

				if (letterData.length() == 3) {
					String letter = dato.getNombreCarta(letterData);
					String typeData = dato.obtenerTipoCarta(letterData);
					int valueLetter = dato.getValorCarta(letter, typeData);
					valueLetter = dato.valorTipo(valueLetter, typeData);
					if (valueLetter != 0 && dato.getErroresDatos()!=400) {
						tree.insertar(letter, typeData, valueLetter);
					} else {
						codigoError = 400;
					}
					if (tree.getErroresArbol() == 406) {
						codigoError = 406;
					}
					
				} else if (letterData.length() == 2) {

					String letter,typeData;
					letter = dato.getNombreCarta(letterData);
					typeData = dato.obtenerTipoCarta(letterData);
					int valueLetter = dato.getValorCarta(letter, typeData);
					//System.out.println("econtre valo" + valueLetter);
					valueLetter = dato.valorTipo(valueLetter, typeData);
					
					if (valueLetter != 0 && dato.getErroresDatos()!=400) {
						tree.insertar(letter, typeData, valueLetter);
					} else {
						codigoError = 400;
					}

					if (tree.getErroresArbol() == 406) {
						codigoError = 406;
					}

				} else {
					codigoError = 400;
				}
				count++;
				// System.out.println("Entre valosdf " + count); 
			} catch (Exception e) {
				codigoError = 400;
			}
			
			
		}
		return tree;
	}
	/***
	 * ERRORES AL INSERTAR
	 * 
	 * @return
	 */
	public int getErroresInsertar() {
		return codigoError;
	}

	/****
	 * ERRORES AL INSERTAR
	 */
	public void setErroresInsertar() {
		codigoError = 0;
	}
	
	/***
	 * SE INSERTA CUANDO EL ARBOL YA TIENE NODOS
	 * 
	 * @param json
	 * @param arbol
	 * @return
	 */
	public ArbolAVL addLetter(ArbolAVL tree,String json) {

		Gson aux = new Gson();
		Map<?, ?> data = aux.fromJson(json, Map.class);
		String letterData = (String) data.get("insert");
		datosCarta dato = new datosCarta();

		if (letterData.length() == 3) {

			String letter = dato.getNombreCarta(letterData);
			String typeData = dato.obtenerTipoCarta(letterData);
			//int valueLetter = null;
			int valueLetter = dato.getValorCarta(letter, typeData);

			valueLetter = dato.valorTipo(valueLetter, typeData);
			//System.out.println("econtre valo" + valueLetter);
			if (valueLetter != 0 && dato.getErroresDatos()!=400) {
				tree.insertar(letter, typeData, valueLetter);
				
			} else {
				codigoError = 400;
			}

			if (tree.getErroresArbol() == 406) {
				codigoError = 406;
			}

		} else if (letterData.length() == 2) {

			String letter,typeData;
			letter = dato.getNombreCarta(letterData);
			typeData = dato.obtenerTipoCarta(letterData);
			int valueLetter = dato.getValorCarta(letter, typeData);

			valueLetter = dato.valorTipo(valueLetter, typeData);
			
			if (valueLetter != 0 && dato.getErroresDatos()!=400) {
				tree.insertar(letter, typeData, valueLetter);
			} else {
				codigoError = 400;
			}

			if (tree.getErroresArbol() == 406) {
				codigoError = 406;
			}

		} else {

			codigoError = 400;
		}

		return tree;
	}

	
}
