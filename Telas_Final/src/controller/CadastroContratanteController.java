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
			
			
			String nome_Servico = this.view.getTfNome().getText();
		    String modalidade = this.view.getTfModalidade().getText();
		    String valor = this.view.getTfValor().getText();
		    String descricao = this.view.getTfDescricao().getText();
		    
			ServicoDAO dao = new ServicoDAO();
			Servico s = new Servico(nome_Servico,Double.parseDouble(valor),modalidade,descricao, false, navegador.getCurrentUser());

			boolean sucesso = dao.cadastrarS(s);
			if (sucesso) {
				navegador.navegarPara("CONTRATANTE");
			}
		});
		
	}
}