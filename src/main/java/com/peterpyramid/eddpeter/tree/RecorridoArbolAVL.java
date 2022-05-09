package com.peterpyramid.eddpeter.tree;

import java.util.Map;
import java.util.LinkedHashMap;

public class RecorridoArbolAVL {

	public RecorridoArbolAVL() {

	}
	/***
	 * Recorrido en orden del arbol
	 * @param raiz
	 * @return recorrido realizado
	 */

	public Map<String, String> inOrder(ArbolAVL raiz) {


		String recorridoRealizar = raiz.inOrden(raiz.getRaizArbol());
		
		Map<String, String> ordenInOrder = new LinkedHashMap<String, String>();
		
		String[] unitario = recorridoRealizar.split(" ");

		for (int i = 0; i < unitario.length; i++) {
			ordenInOrder.put("" + i + "", unitario[i]);
		}

		// Iterator<Integer> it = treeInOrder.keySet().iterator();
		// while (it.hasNext()) {
		// Integer key = it.next();
		// enOrden += "" + key + ": " + treeInOrder.get(key) + "\n";
		// }

		return ordenInOrder;
	}
	
	/***
	 * Recorrido pre orden del arbol
	 * @param raiz
	 * @return recorrido pre orden realizado
	 */
	
	public Map<String, String> preOrder(ArbolAVL raiz) {

		Map<String, String> ordenPreOrder = new LinkedHashMap<String, String>();
		String recorridoRealizar = raiz.preOrden(raiz.getRaizArbol());

		String[] unitario = recorridoRealizar.split(" ");

		for (int i = 0; i < unitario.length; i++) {
			ordenPreOrder.put(""+i+""  ,unitario[i]);
		}

		// Iterator<Integer> it = treePreOrder.keySet().iterator();

		// while (it.hasNext()) {
		// Integer key = it.next();
		// preOrder += "" + key + ": " + treePreOrder.get(key);
		// }

		return ordenPreOrder;
	}
	
	/***
	 * Recorrido post orden del arbol
	 * @param raiz
	 * @return recorrido  post orden realizado
	 */

	public Map<String, String> postOrder(ArbolAVL raiz) {

		
		String recorridoRealizar = raiz.postOrden(raiz.getRaizArbol());
		
		Map<String, String> ordenPostOrder = new LinkedHashMap<String, String>();

		String[] unitario = recorridoRealizar.split(" ");

		for (int i = 0; i < unitario.length; i++) {
			ordenPostOrder.put("" +i+"", unitario[i]);
		}

		// Iterator<Integer> it = treePostOrder.keySet().iterator();

		// while (it.hasNext()) {
		// Integer key = it.next();
		// postOrden += "" + key + ": " + treePostOrder.get(key);
		// }

		return ordenPostOrder;
	}

	
}
