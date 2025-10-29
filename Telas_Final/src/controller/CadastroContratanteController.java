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
			// Verifica se há um usuário logado
			if (navegador.getCurrentUser() == null) {
				javax.swing.JOptionPane.showMessageDialog(null, "Erro: Nenhum usuário logado. Por favor, faça login novamente.", "Erro", javax.swing.JOptionPane.ERROR_MESSAGE);
				navegador.navegarPara("LOGIN");
				return;
			}
			
			String nome_Servico = this.view.getTfNome().getText();
		    String modalidade = this.view.getTfModalidade().getText();
		    Double valor = Double.parseDouble(this.view.getTfValor().getText());
		    String descricao = this.view.getTfDescricao().getText();
		    
			ServicoDAO dao = new ServicoDAO();
			Servico s = new Servico(nome_Servico,valor,modalidade,descricao, false, navegador.getCurrentUser());

			boolean sucesso = dao.cadastrarS(s);
			if (sucesso) {
				this.view.limparCampos();
				// Navigate to SERVICOS but do not push the cadastro_contratante screen onto history
				navegador.navegarPara("SERVICOS", false);
				
			}
		});
		
	}
}