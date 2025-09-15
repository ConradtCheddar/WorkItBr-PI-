package controller;

import model.Usuario;
import model.UsuarioDAO;
import view.TelaCadastro;
import view.TelaLogin;

public class CadastroController {
	private final TelaCadastro view;
	private final UsuarioDAO model;
	private final Navegador navegador;
	
	public CadastroController(TelaCadastro view, UsuarioDAO model, Navegador navegador){
		this.view = view;
		this.model = model;
		this.navegador = navegador;
		
		this.view.cadastrar(e ->{
			String email = this.view.getTfEmail().getText();
		    String usuario = this.view.getTfUsuario().getText();
		    String cpf = this.view.getTfCPF().getText();
		    String telefone = this.view.getTfTelefone().getText();
		    String senha1 = new String(this.view.getSenha().getPassword());
		    String senha2Text = new String(this.view.getSenha2().getPassword());
			
			UsuarioDAO dao = new UsuarioDAO();
			Usuario u = new Usuario(email, usuario, cpf, telefone, senha1, this.view.getRdbtnContratado().isSelected(), this.view.getRdbtnContratante().isSelected());


			dao.cadastrar(u, senha2Text);
			navegador.navegarPara("LOGIN");
		});

}}
