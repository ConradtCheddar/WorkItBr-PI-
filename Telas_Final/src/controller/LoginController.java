package controller;

import model.Usuario;
import model.UsuarioDAO;
import view.Primario;
import view.TelaLogin;

public class LoginController {
	private final TelaLogin view;
	private final UsuarioDAO model;
	private final TelaController navegador;
	
	public LoginController(TelaLogin view, UsuarioDAO model, TelaController navegador){
		this.view = view;
		this.model = model;
		this.navegador = navegador;
		
		this.view.login(e ->{
			String nome = view.getUsuario();
			String senha = view.getSenha();

			UsuarioDAO dao = new UsuarioDAO();
			Usuario u = dao.login(nome, senha);
	
		});
		
		this.view.login(e ->{
			Usuario u = new Usuario(u.getUsuario(),u.getCpfCnpj(),u.getEmail(),u.getTelefone(),u.getSenha(), u.isContratado(), u.isContratante());
			
			if (u != null) {
				if (u.isContratado()) {
					this.navegador.mostrarTela(Primario.TRABALHOS_PANEL);
				} else if (u.isContratante()) {
					prim.mostrarTela(Primario.CONTRATANTE_PANEL);
				} else {
					prim.mostrarTela(Primario.ADM_PANEL);
				}
			}
			txtUsuario.setText("");
			passwordField.setText("");
			
		});
	}
	

}
