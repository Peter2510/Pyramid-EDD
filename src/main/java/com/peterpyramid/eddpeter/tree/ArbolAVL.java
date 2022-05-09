package com.peterpyramid.eddpeter.tree;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class ArbolAVL {
	private Nodo raiz;
	private String inOrder = "";
	private String postOrder = "";
	private String preOrder = "";
	private int codigoError = 0;

	public Nodo getRaizArbol() {
		return raiz;
	}

	public ArbolAVL() {
		this.raiz = null;
	}

	private int getFactorEquilibrio(Nodo nodo) {
		if (nodo == null) {
			return -1;
		} else {
			return nodo.getFactorEquilibrio();
		}
	}

	/***
	 * Rotacion simple a la deraecha
	 * 
	 * @param nodo
	 * @return
	 */
	private Nodo rotacionIzquierda(Nodo nodo) {
		
		Nodo nodoIzquierdoAux = nodo.hijoIzquierdo;
		nodo.hijoIzquierdo = nodoIzquierdoAux.hijoDerecho;
		nodoIzquierdoAux.hijoDerecho = nodo;
		nodo.factorEquilibrio = Math.max(getFactorEquilibrio(nodo.hijoIzquierdo), getFactorEquilibrio(nodo.hijoDerecho))+ 1;
		nodoIzquierdoAux.factorEquilibrio = Math.max(getFactorEquilibrio(nodoIzquierdoAux.hijoIzquierdo),getFactorEquilibrio(nodoIzquierdoAux.hijoDerecho)) + 1;
		return nodoIzquierdoAux;
	}

	/***
	 * Rotacion simple a la izquierda
	 * 
	 * @param nodo
	 * @return
	 */
	private Nodo rotacionDerecha(Nodo nodo) {
		Nodo nodoDerechoAux = nodo.hijoDerecho;
		nodo.hijoDerecho = nodoDerechoAux.hijoIzquierdo;
		nodoDerechoAux.hijoIzquierdo = nodo;
		nodo.factorEquilibrio = Math.max(getFactorEquilibrio(nodo.hijoIzquierdo), getFactorEquilibrio(nodo.hijoDerecho))+ 1;
		nodoDerechoAux.factorEquilibrio = Math.max(getFactorEquilibrio(nodoDerechoAux.hijoIzquierdo),getFactorEquilibrio(nodoDerechoAux.hijoDerecho)) + 1;
		return nodoDerechoAux;
	}

	/***
	 * Rotacion doble a la derecha
	 * 
	 * @param nodo
	 * @return
	 */
	private Nodo rotacionDobleDerecha(Nodo nodo) {
		Nodo auxiliar;
		nodo.hijoDerecho = rotacionIzquierda(nodo.hijoDerecho);
		auxiliar = rotacionDerecha(nodo);
		return auxiliar;
	}

	/***
	 * Rotacion doble a la izquierda
	 * 
	 * @param nodo
	 * @return
	 */
	private Nodo rotacionDobleIzquierda(Nodo nodo) {
		Nodo auxiliar;
		nodo.hijoIzquierdo = rotacionDerecha(nodo.hijoIzquierdo);
		auxiliar = rotacionIzquierda(nodo);
		return auxiliar;
	}

	/***
	 * Se inserta en el arbol Avl
	 * 
	 * @param insertNodo
	 * @param complemento
	 * @return
	 */
	private Nodo insertarAVL(Nodo insertNodo, Nodo complemento) {

		Nodo newRaiz = complemento;

		if (insertNodo.getValor() < complemento.getValor()) {

			if (complemento.hijoIzquierdo == null) {
				complemento.hijoIzquierdo = insertNodo;
			} else {
				complemento.hijoIzquierdo = insertarAVL(insertNodo, complemento.hijoIzquierdo);
				if ((getFactorEquilibrio(complemento.hijoIzquierdo)- getFactorEquilibrio(complemento.hijoDerecho) == 2)) {
					if (insertNodo.getValor() < complemento.hijoIzquierdo.getValor()) {
						newRaiz = rotacionIzquierda(complemento);
					} else {
						newRaiz = rotacionDobleIzquierda(complemento);

					}
				}
			}

		} else if (insertNodo.getValor() > complemento.getValor()) {
			if (complemento.hijoDerecho == null) {
				complemento.hijoDerecho = insertNodo;
			} else {
				complemento.hijoDerecho = insertarAVL(insertNodo, complemento.hijoDerecho);
				if ((getFactorEquilibrio(complemento.hijoDerecho)- getFactorEquilibrio(complemento.hijoIzquierdo) == 2)) {
					if (insertNodo.getValor() > complemento.hijoDerecho.getValor()) {
						newRaiz = rotacionDerecha(complemento);
					} else {
						newRaiz = rotacionDobleDerecha(complemento);
					}
				}
			}
		} else {

			// Entra si el nodo a insertar es duplicado -> ERROR 406
			codigoError = 406;
		}

		// arbol actualizando
		if ((complemento.hijoIzquierdo == null) && (complemento.hijoDerecho != null)) {
			complemento.factorEquilibrio = complemento.hijoDerecho.factorEquilibrio + 1;
		} else if ((complemento.hijoDerecho == null) && (complemento.hijoIzquierdo != null)) {
			complemento.factorEquilibrio = complemento.hijoIzquierdo.factorEquilibrio + 1;
		} else {
			complemento.factorEquilibrio = Math.max(getFactorEquilibrio(complemento.hijoIzquierdo),getFactorEquilibrio(complemento.hijoDerecho)) + 1;
		}
		return newRaiz;
	}

	/***
	 * Valores a insertar
	 * 
	 * @param carta
	 * @param tipo
	 * @param valor
	 */
	public void insertar(String carta, String tipo, int valor) {
		Nodo nuevo = new Nodo(carta, tipo, valor);
		if (raiz == null) {
			raiz = nuevo;
		} else {
			raiz = insertarAVL(nuevo, raiz);
		}
	}

	/***
	 * Eliminar nodo solicitado
	 * 
	 * @param valor
	 */
	public void eliminar(int valor) {
		raiz = eliminarAvl(valor, raiz);
	}

	private Nodo eliminarAvl(int valor, Nodo nodo) {
		try {
			if (nodo == null) {
				codigoError = 400;
				return null;
			} else {
				// SE BUSCA EL VALOR A ELIMINAR
				if (nodo.getValor() < valor) {
					nodo.setHijoDerecho(eliminarAvl(valor, nodo.getHijoDerecho()));
				} else if (nodo.getValor() > valor) {
					nodo.setHijoIzquierdo(eliminarAvl(valor, nodo.getHijoIzquierdo()));
				} // si se encuentra el nodo que no tiene hijos lo elimina
				else if (nodo.getHijoIzquierdo() == null && nodo.getHijoDerecho() == null) {
					nodo = null;
				} else if (getFactorEquilibrio(nodo.getHijoIzquierdo()) > getFactorEquilibrio(nodo.getHijoDerecho())) {
					nodo = rotacionDerecha(nodo);
					nodo.setHijoIzquierdo(eliminarAvl(valor, nodo.getHijoIzquierdo()));
					System.out.println("Enctro a rotacion izquierda");
				} else if (getFactorEquilibrio(nodo.getHijoDerecho()) > getFactorEquilibrio(nodo.getHijoIzquierdo())) {
					System.out.println("Enctro a rotacion derecha");
					nodo = rotacionIzquierda(nodo);
					nodo.setHijoDerecho(eliminarAvl(valor, nodo.getHijoDerecho()));
				}
				// Se actualiza la informacion con el peso actual del arbol
				if (nodo != null) {
					nodo.factorEquilibrio = getFactorEquilibrio(nodo.getHijoIzquierdo())+ getFactorEquilibrio(nodo.getHijoDerecho());
				}
				return nodo;
			}

		} catch (Exception e) {

			// si hay error lanza 400
			codigoError = 409;
			return nodo;
		}

	}

	/***
	 * Recorridos posibles a realizar
	 */

	public void setRecorridos() {
		postOrder = "";
		preOrder = "";
		inOrder = "";
	}

	/***
	 * REcorrido in Order
	 * 
	 * @param raizTree
	 * @return
	 */
	public String inOrden(Nodo raizTree) {

		if (raizTree != null) {
			inOrden(raizTree.hijoIzquierdo);
			inOrder += raizTree.getNombre() + raizTree.getTipo() + " ";
			inOrden(raizTree.hijoDerecho);
		}
		return inOrder;
	}

	/***
	 * recorrido Preorder
	 * 
	 * @param raizTree
	 * @return
	 */
	public String preOrden(Nodo raizTree) {

		if (raizTree != null) {
			preOrder += raizTree.getNombre() + raizTree.getTipo() + " ";
			preOrden(raizTree.hijoIzquierdo);
			preOrden(raizTree.hijoDerecho);
		}
		return preOrder;
	}

	/***
	 * recorrido postOrden
	 * 
	 * @param raizTree
	 * @return
	 */
	public String postOrden(Nodo raizTree) {
		if (raizTree != null) {
			postOrden(raizTree.hijoIzquierdo);
			postOrden(raizTree.hijoDerecho);
			postOrder += raizTree.getNombre() + raizTree.getTipo() + " ";
		}
		return postOrder;
	}

	/***
	 * Se busca en el arbol
	 * 
	 * @param valorBuscar
	 * @param raizVariable
	 * @return
	 */
	public Nodo buscarEnArbolAVL(int valorBuscar, Nodo raizVariable) {

		try {
			if (raiz == null) {
				// Si no se encuentra la carta se retorna 404
				codigoError = 404;
				return null;
			} else if (raizVariable.getValor() == valorBuscar) {
				return raizVariable;
			} else if (raizVariable.getValor() < valorBuscar) {
				return buscarEnArbolAVL(valorBuscar, raizVariable.hijoDerecho);
			} else {
				return buscarEnArbolAVL(valorBuscar, raizVariable.hijoIzquierdo);
			}
		} catch (Exception e) {
			return null;
		}
	}

	/***
	 * Creacion del archivo para graficar
	 * 
	 * @return
	 */
	public String getCodeGraphviz() {
		// label nombre
		// shape forma
		// color color
		// edge flecha
		String datos = "digraph G\n" + "{\n" + "    node[shape = circle]\n" + "    node[style = filled]\n" + ""
				+ "    node[fillcolor = \"#FFFF00\"]\n" + "    node[color = \"#FF7F00\"]\n"
				+ "    edge[color = \"#000000\",arrowhead=none]\n";

		if (raiz != null) {
			datos += raiz.convertGraphviz();
		}

		datos += "\n label=\"\\n\\nEstado Gráfico Árbol AVL\\n\\nPedro Gordillo 202031686\"}";

		return datos;
	}

	private void escribirDot(String path, String data) {

		PrintWriter escribirtFile = null;
		FileWriter file = null;

		try {

			file = new FileWriter(path);
			escribirtFile = new PrintWriter(file);
			escribirtFile.write(data);
			escribirtFile.close();
			file.close();

		} catch (Exception pe) {
			// si hay error -> 400
			codigoError = 400;

		} finally {
			if (escribirtFile != null) {
				escribirtFile.close();
			}
		}
	}

	public void ejecutarCodigoGraphviz() {

		try {

			escribirDot("statusTree.dot", getCodeGraphviz());
			ProcessBuilder aux;

			// creacion de la imagen con los comandos por ProcessBuilder
			aux = new ProcessBuilder("dot", "-Tpng", "statusTree.dot", "-o", "EstadoArbolAVL.jpg");
			aux.redirectErrorStream(true);
			aux.start();

		} catch (Exception pe) {
			codigoError = 400;
		}
	}

	public ArrayList<datosNivel> niveles() {

		Nodo nodo = raiz;
		ArrayList<datosNivel> valores = new ArrayList<>();

		if (nodo != null) {

			Queue<Object> cola = new LinkedList<>();
			// se agrega el nodo raiz
			cola.add(nodo);

			// valor del nodo raiz
			int nivel1 = getNivel(nodo.getValor());

			while (!cola.isEmpty()) {
				// devolver elemento "cabeza" de la cola
				Nodo auxiliar = (Nodo) cola.element();
				// eliminar el elemento "cabeza" de la cola
				cola.remove();
				// valor del elemento actual
				int nivel2 = getNivel(auxiliar.getValor());

				// si el valor del nivel es distinto
				if (nivel1 != nivel2) {
					// System.out.print("\n");
					// Posicionar mismo nivel
					nivel1 = nivel2;
				}

				// System.out.print(auxiliar.getValor() + " ");
				valores.add(new datosNivel(nivel1, auxiliar));

				if (auxiliar.getHijoIzquierdo() != null) {

					// agregar lado izquierdro
					cola.add(auxiliar.getHijoIzquierdo());
				}

				if (auxiliar.getHijoDerecho() != null) {

					// agregar lado derecho
					cola.add(auxiliar.getHijoDerecho());
				}
			}
		} else {
			codigoError = 400;
		}

		return valores;
	}

	private int getNivel(int valor) {
		int nivel = 0;
		Nodo nodo = raiz;

		while (valor != nodo.getValor()) {

			if (valor > nodo.getValor()) {
				nodo = nodo.getHijoDerecho();
			} else {
				nodo = nodo.getHijoIzquierdo();
			}

			nivel += 1;
		}
		return nivel;
	}

	// si el arbol es null
	public boolean isEmpty() {
		return raiz == null;
	}

	public int getErroresArbol() {
		return codigoError;
	}

	public void setErroresArbol() {
		codigoError = 0;
	}

}
