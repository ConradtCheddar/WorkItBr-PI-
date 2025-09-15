package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import model.Usuario;
import model.UsuarioDAO;
import view.Primario;
import view.TelaLogin;

public class LoginController {
	private final TelaLogin view;
	private final UsuarioDAO model;
	private final Navegador navegador;
	
	public LoginController(TelaLogin view, UsuarioDAO model, Navegador navegador){
		this.view = view;
		this.model = model;
		this.navegador = navegador;
		
		this.view.login(e ->{
			String nome = view.getUsuario();
			String senha = view.getSenha();

			UsuarioDAO dao = new UsuarioDAO();
			Usuario u = dao.login(nome, senha);
			
			if (u != null) {
				if (u.isContratado()) {
						navegador.navegarPara("LOGIN");
				} else if (u.isContratante()) {
						navegador.navegarPara("LOGIN");
					} else {
						navegador.navegarPara("LOGIN");
					}
				}
				this.view.limparFormulario();
	
		});

		this.view.cadastro(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        navegador.navegarPara("CADASTRO");
		    }
		});
	}
	

}
