package com.peterpyramid.eddpeter.tree;

public class datosNivel {

	int nivel;
	Nodo nodo;
	
	public datosNivel(int nivel, Nodo nodo) {
		this.nivel = nivel;
		this.nodo = nodo;
	}

	public int getNivel() {
		return nivel;
	}

	public void setNivel(int nivel) {
		this.nivel = nivel;
	}

	public Nodo getNodo() {
		return nodo;
	}

	public void setNodo(Nodo nodo) {
		this.nodo = nodo;
	}
	
	
}
