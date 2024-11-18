package br.com.juan.conversor.api;

import java.io.IOException;

import org.json.JSONObject;
import org.json.JSONException;

import br.com.juan.conversor.calculadora.TipoMoedaEnum;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Classe que realiza conversão de moedas utilizando a API ExchangeRate-API.
 *
 * @author Juan Daniel Ferreira Ibanez
 */
public class ConversorDeMoedasApi {

	private static final String API_URL = "https://v6.exchangerate-api.com/v6/";
	private static final String API_KEY = "e970ab21c569fedfec6d88e7";

	/**
	 * Realiza a conversão de moeda utilizando uma API externa.
	 *
	 * @param moedaInicial Moeda de origem.
	 * @param moedaFinal   Moeda de destino.
	 * @param valor        Valor a ser convertido.
	 * @return Valor convertido ou 0.0 em caso de erro.
	 */
	public double converterMoeda(TipoMoedaEnum moedaInicial, TipoMoedaEnum moedaFinal, double valor) {
		OkHttpClient cliente = new OkHttpClient();

		String url = API_URL + API_KEY + "/pair/" + moedaInicial.getTipo() + "/" + moedaFinal.getTipo() + "/" + valor;

		Request request = new Request.Builder()
				.url(url)
				.method("GET", null)
				.build();

		try (Response response = cliente.newCall(request).execute()) {
			if (!response.isSuccessful()) {
				System.err.println("Erro na requisição: Código HTTP " + response.code());
				return 0.0;
			}

			if (response.body() == null) {
				System.err.println("Resposta da API está vazia.");
				return 0.0;
			}

			String jsonString = response.body().string();
			JSONObject json = new JSONObject(jsonString);

			if (!json.getString("result").equalsIgnoreCase("success")) {
				System.err.println("Erro na API: " + jsonString);
				return 0.0;
			}

			// Retorna o valor convertido
			return json.getDouble("conversion_result");

		} catch (IOException e) {
			System.err.println("Erro de comunicação com a API: " + e.getMessage());
		} catch (JSONException e) {
			System.err.println("Erro ao processar o JSON retornado: " + e.getMessage());
		}

		return 0.0;
	}
}
