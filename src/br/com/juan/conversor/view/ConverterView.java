package br.com.juan.conversor.view;

import javax.swing.JOptionPane;

import br.com.juan.conversor.calculadora.Conversor;
import br.com.juan.conversor.calculadora.TipoConversaoEnum;
import br.com.juan.conversor.calculadora.TipoConversaoTemperatura;
import br.com.juan.conversor.validador.Valida;

/**
 * Classe responsável pela interface com o usuário.
 * Gere interações com o usuário por meio de janelas de diálogo.
 *
 * @author Juan
 * @version 2.0
 */
public class ConverterView {

	// Mensagens fixas
	private static final String MSG_VALOR_CONVERSAO = "Insira o valor que deseja converter:";
	private static final String MSG_DESEJA_CONTINUAR = "Deseja continuar?";
	private static final String MSG_VALOR_INVALIDO = "Valor inválido. Utilize '.' para definir casas decimais.";
	private static final String MSG_RESULTADO_CONVERSAO = "O valor da conversão é: ";
	private static final String MSG_PROGRAMA_FINALIZADO = "Programa finalizado.";
	private static final String MSG_PROGRAMA_CONCLUIDO = "Programa concluído.";

	private static final Object[] OPCOES_MENU = { "Conversor de Moedas", "Conversor de Temperatura" };

	/**
	 * Exibe o menu principal do programa.
	 */
	public void exibirMenuPrincipal() {
		Object opcaoSelecionada = exibirInputDialog("Escolha uma opção", "Menu", OPCOES_MENU);

		if (opcaoSelecionada == null) {
			encerrarPrograma(MSG_PROGRAMA_FINALIZADO);
		}

		capturarValorParaConversao(opcaoSelecionada);
	}

	/**
	 * Captura o valor para conversão com base na escolha do usuário.
	 */
	private void capturarValorParaConversao(Object tipoConversao) {
		String valorEntrada = exibirInput(MSG_VALOR_CONVERSAO);

		if (valorEntrada == null) {
			exibirMenuPrincipal();
			return;
		}

		Valida validador = new Valida();

		if (validador.validaNumeroDigitado(valorEntrada)) {
			double valorConvertido = validador.getValorConvertido();
			processarConversao(valorConvertido, tipoConversao);
		} else {
			exibirMensagem(MSG_VALOR_INVALIDO);
			capturarValorParaConversao(tipoConversao);
		}
	}

	/**
	 * Processa a conversão com base na escolha do usuário.
	 */
	private void processarConversao(double valor, Object tipoConversao) {
		if (tipoConversao.equals(OPCOES_MENU[0])) {
			selecionarConversaoMoeda(valor);
		} else if (tipoConversao.equals(OPCOES_MENU[1])) {
			selecionarConversaoTemperatura(valor);
		}
	}

	private void selecionarConversaoMoeda(double valor) {
		TipoConversaoEnum[] opcoes = TipoConversaoEnum.values();
		TipoConversaoEnum conversao = (TipoConversaoEnum) exibirInputDialog("Escolha o tipo de conversão", "Moedas", opcoes);

		if (conversao != null) {
			Conversor conversor = new Conversor();
			String resultado = conversor.converterMoeda(conversao, valor);
			exibirResultadoEContinuar(resultado);
		}
	}

	private void selecionarConversaoTemperatura(double valor) {
		TipoConversaoTemperatura[] opcoes = TipoConversaoTemperatura.values();
		TipoConversaoTemperatura conversao = (TipoConversaoTemperatura) exibirInputDialog(
				"Escolha o tipo de conversão", "Temperatura", opcoes);

		if (conversao != null) {
			Conversor conversor = new Conversor();
			String resultado = conversor.ConverteTemperatura(conversao, valor);
			exibirResultadoEContinuar(resultado);
		}
	}

	private void exibirResultadoEContinuar(String resultado) {
		exibirMensagem(MSG_RESULTADO_CONVERSAO + resultado);
		verificarContinuacao();
	}

	private void verificarContinuacao() {
		int opcao = exibirConfirmDialog();
		if (opcao == JOptionPane.YES_OPTION) {
			exibirMenuPrincipal();
		} else {
			encerrarPrograma(MSG_PROGRAMA_CONCLUIDO);
		}
	}

	// Métodos utilitários para simplificar interações com o usuário.
	private String exibirInput(String mensagem) {
		return JOptionPane.showInputDialog(null, mensagem);
	}

	private Object exibirInputDialog(String mensagem, String titulo, Object[] opcoes) {
		return JOptionPane.showInputDialog(null, mensagem, titulo, JOptionPane.INFORMATION_MESSAGE, null, opcoes, opcoes[0]);
	}

	private void exibirMensagem(String mensagem) {
		JOptionPane.showMessageDialog(null, mensagem);
	}

	private int exibirConfirmDialog() {
		return JOptionPane.showConfirmDialog(null, MSG_DESEJA_CONTINUAR, "Confirmação", JOptionPane.YES_NO_OPTION);
	}

	private void encerrarPrograma(String mensagem) {
		exibirMensagem(mensagem);
		System.exit(0);
	}
}
