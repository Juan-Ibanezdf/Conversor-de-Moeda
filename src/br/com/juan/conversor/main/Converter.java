package br.com.juan.conversor.main;

import br.com.juan.conversor.view.ConverterView;

/**
 * Classe principal para iniciar o programa de conversão.
 * Este programa converte valores entre diferentes moedas ou temperaturas.
 *
 * @author Juan
 * @version 2.0
 */
public class Converter {
	public static void main(String[] args) {
		ConverterView frame = new ConverterView();
		frame.exibirMenuPrincipal();
	}
}
