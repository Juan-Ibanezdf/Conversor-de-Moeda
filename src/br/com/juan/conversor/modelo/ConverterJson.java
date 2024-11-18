package br.com.juan.conversor.modelo;

import org.json.JSONObject;
import org.json.JSONException;

public class ConverterJson {
	private double result;

	public double pegandoDadosJson(JSONObject json) {
		try {
			this.result = json.getDouble("result");
		} catch (JSONException e) {
			System.err.println("Erro ao tentar obter o valor 'result' do JSON: " + e.getMessage());
			throw new IllegalArgumentException("JSON inválido ou valor 'result' ausente.");
		}
		return result;
	}
}
