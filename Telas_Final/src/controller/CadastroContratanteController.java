package controller;

import model.Servico;
import model.ServicoDAO;
import model.Usuario;
import model.UsuarioDAO;
import view.TelaCadastro;
import view.TelaCadastroContratante;

public class CadastroContratanteController {
	private final TelaCadastroContratante view;
	private final ServicoDAO model;
	private final Navegador navegador;

	public CadastroContratanteController(TelaCadastroContratante view, ServicoDAO model, Navegador navegador){
		this.view = view;
		this.model = model;
		this.navegador = navegador;
		
		this.view.cadastrar(e ->{
			if (navegador.getCurrentUser() == null) {
				javax.swing.JOptionPane.showMessageDialog(view, "Erro: Nenhum usuário logado. Por favor, faça login novamente.", "Erro", javax.swing.JOptionPane.ERROR_MESSAGE);
				navegador.navegarPara("LOGIN");
				return;
			}
			
			String nome_Servico = this.view.getTfNome().getText();
		    String modalidade = this.view.getTfModalidade().getText();
		    Double valor;
		    try {
		    	valor = Double.parseDouble(this.view.getTfValor().getText());
		    } catch (NumberFormatException ex) {
		    	javax.swing.JOptionPane.showMessageDialog(view, "Valor inválido. Por favor, insira um número.", "Erro de Formato", javax.swing.JOptionPane.ERROR_MESSAGE);
		    	return;
		    }
		    String descricao = this.view.getTfDescricao().getText();
		    
			Servico s = new Servico(nome_Servico,valor,modalidade,descricao, false, navegador.getCurrentUser());

			boolean sucesso = model.cadastrarS(s);
			if (sucesso) {
				this.view.limparCampos();
				navegador.navegarPara("SERVICOS", false);
			}
		});
	}
}