package com.peterpyramid.eddpeter.tree;

public class datosCarta {
	int codigoError=0;
	public datosCarta(){
		
	}
	
	/***
	 * NOMBRE DE LA CARTA
	 * 
	 * @param data
	 * @return
	 */
	public String getNombreCarta(String data) {

		if (data.length() == 3) {
			//System.out.println("Tamaño 3s" );
			return data.substring(0, data.length() - 1);
		} else if (data.length() == 2) {
			//System.out.println("Tamaño 2" );
			return data.substring(data.length() - 2, data.length() - 1);
		} else {
			return null;
		}
	}
	
	/***
	 * VALOR DE LA CARTA
	 * 
	 * @param tipo
	 * @param carta
	 * @return
	 */

	public int getValorCarta(String carta, String tipo) {

		int cartaValue = 0;
		if (carta.equals("K")) {
			cartaValue = 13;
			//System.out.println("Valor carta "+ cartaValue );
		} else if (carta.equals("Q")) {
			cartaValue = 12;
			//System.out.println("Valor carta "+ cartaValue );
		} else if (carta.equals("J")) {
			cartaValue = 11;
			//System.out.println("Valor carta "+ cartaValue );
		} else if (carta.equals("A")) {
			cartaValue = 1;
			//System.out.println("Valor carta "+ cartaValue );
		} else {
			try {
				cartaValue = Integer.parseInt(carta);		
				if (!(cartaValue >= 2 && cartaValue <= 10)) {
					//System.out.println("Valor carta "+ cartaValue );
					//System.out.println("Lanzo error 400" );
					codigoError = 400;
				}

			} catch (Exception e) {
				//System.out.println("error 400" );
				codigoError = 400;
			}
		}
		return cartaValue;
	}
	
	/****
	 * TIPO DE CARTA
	 * 
	 * @param carta
	 * @return
	 */
	public String obtenerTipoCarta(String carta) {
		if (carta.length() == 2) {
			//System.out.println("Tamaño 2" );
			return carta.substring(carta.length() - 1);
		} else if (carta.length() == 3) {
			//System.out.println("Tamaño 3" );
			return carta.substring(carta.length() - 1);
		} else {
			return null;
		}
	}
	
	/***
	 * Calcula el valor segun el tipo de la carta
	 * @param valor
	 * @param tipo
	 * @return
	 */
public int valorTipo(int valor, String tipo) {
		
		int valorPica = 60,valorDiamante = 20, valorCorazon = 40,valorTrebol = 0;
		
		switch (tipo) {
		case "♠":
			valor += valorPica;
			break;
		/*case "PICA":
			valor += valor;
			break; */
		case "♦":
			valor += valorDiamante;
			break;
		/*case "DIAMANTE":
			valor += valor;
			break; */
		case "♣":
			valor += valorTrebol;
			break;
		/*case "TREBOL":
			valor += valor;
			break; */
		case "♥":
			valor += valorCorazon;
			break;
		/*case "CORAZON":
			valor += valor;
			break; */
		}
		
		return valor;
	}
		
	public int getErroresDatos() {
		return codigoError;
	}
}
