package controller;

import model.Servico;
import model.ServicoDAO;
import model.Status;
import view.Mensagem;
import view.TelaCadastroContratante;

public class CadastroContratanteController {
	private final TelaCadastroContratante view;
	private final ServicoDAO model;
	private final Navegador navegador;

	public CadastroContratanteController(TelaCadastroContratante view, ServicoDAO model, Navegador navegador) {
		this.view = view;
		this.model = model;
		this.navegador = navegador;

		this.view.cadastrar(e -> {
			/**
			 * salva os valores em variaveis
			 */
			Mensagem M = new Mensagem();
			String nome_Servico = this.view.getTfNome().getText();
			String modalidade = this.view.getTfModalidade().getText();
			String valorS = this.view.getTfValor().getText();
			String descricao = this.view.getTfDescricao().getText();

			/**
			 * faz validação dos valores
			 */
			if (nome_Servico.trim().isEmpty() || modalidade.trim().isEmpty() || valorS.trim().isEmpty()
					|| nome_Servico.trim().isEmpty()) {
				M.Erro("Preencha todos os campos", "Erro");
			} else {
				Double valorD;
				try {
					valorD = Double.parseDouble(this.view.getTfValor().getText());
				} catch (NumberFormatException ex) {
					M.Erro("Campo valor com dado invalido, insira um valor numerico", "Valor invalido");
					return;
				}

				/**
				 * salva os valores
				 */
				Servico s = new Servico(nome_Servico, valorD, modalidade, descricao, null, Status.CADASTRADO,
						navegador.getCurrentUser());

				boolean sucesso = model.cadastrarS(s);
				if (sucesso) {
					M.Sucesso("Cadastro realizado com sucesso", "Sucesso");
					this.view.limparCampos();
					navegador.navegarPara("SERVICOS", false);
				} else {
					M.Erro("Cadastro falhou", "Erro inesperado");
				}
			}

		});
	}
}