package com.peterpyramid.eddpeter.tree;

import java.util.ArrayList;

public class Nodo {

	int factorEquilibrio;
	private int valor;
	private String nombre,tipo;
	Nodo hijoIzquierdo;
	Nodo hijoDerecho;

	public Nodo() {
	}

	public Nodo(String nombrePedro, String tipo, int valor) {
		this.hijoIzquierdo = null;
		this.hijoDerecho = null;
		this.tipo = tipo;
		this.valor = valor;
		this.nombre = nombrePedro;
		this.factorEquilibrio = 0;
		
	}
	
	public boolean nodoInterno() {
				
		if(getHijoDerecho() == null && getHijoIzquierdo()==null) {
			return false;
		}else {
			return true;
		}
		
	}

	public int getFactorEquilibrio() {
		return factorEquilibrio;
	}

	public void setFactorEquilibrio(int factorEquilibrio) {
		this.factorEquilibrio = factorEquilibrio;
	}

	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Nodo getHijoDerecho() {
		return hijoDerecho;
	}

	public void setHijoDerecho(Nodo hijoDerecho) {
		this.hijoDerecho = hijoDerecho;
	}

	public Nodo getHijoIzquierdo() {
		return hijoIzquierdo;
	}

	public void setHijoIzquierdo(Nodo hijoIzquierdo) {
		this.hijoIzquierdo = hijoIzquierdo;
	}
	
	public String convertGraphviz() {

	// SI ES NODO HOJA
	String tipoDeCarta = getTipoCarta(tipo);

	if (hijoIzquierdo == null && hijoDerecho == null) {

		return String.valueOf("\""+ nombre + tipoDeCarta + "\"");

	} 
	// SI ES PADRE
	else {

		String datos = "";
		if (hijoIzquierdo != null) {

			datos = "\""+ nombre + tipoDeCarta + "\"" + " -> " + hijoIzquierdo.convertGraphviz() + "\n";
		}
		if (hijoDerecho != null) {

			datos += "\""+ nombre + tipoDeCarta + "\"  -> " + hijoDerecho.convertGraphviz() + "\n";
		}

		return datos;
	}
} 
	
	
/***
 * Se retorna el codigo que proporciona  ISO 10646
 * @param tipoCarta
 * @return
 */
	public String getTipoCarta(String tipoCarta) {
		String tipoDeLaCarta = "";

		if (tipo.equals("♣")) {
			
			tipoDeLaCarta = "&#9827;";

		} else if (tipo.equals("♥")) {

			tipoDeLaCarta = "&#9829;";

		} else if (tipo.equals("♦")) {

			tipoDeLaCarta = "&#9830;";

		} else if (tipo.equals("♠")) {

			tipoDeLaCarta = "&#9824;";

		}

		return tipoDeLaCarta;
	}

	@Override
	public String toString() {
		return nombre+tipo;
	}

}
