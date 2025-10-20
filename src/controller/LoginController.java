package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

import model.Usuario;
import model.UsuarioDAO;
import view.Primario;
import view.TelaLogin;
import view.TelaCadastro;

public class LoginController {
	private final TelaLogin view;
	private final UsuarioDAO model;
	private final Navegador navegador;
	private final TelaCadastro telaCadastro;
	private final PopupMenuController popupMenuController;
	
	public LoginController(TelaLogin view, UsuarioDAO model, Navegador navegador, TelaCadastro telaCadastro, PopupMenuController popupMenuController){
		this.view = view;
		this.model = model;
		this.navegador = navegador;
		this.telaCadastro = telaCadastro;
		this.popupMenuController = popupMenuController;
		
		this.view.login(e ->{
			String nome = view.getUsuario();
			String senha = view.getSenha();

			UsuarioDAO dao = new UsuarioDAO();
			Usuario u = dao.login(nome, senha);
			
			if (u != null) {
				this.navegador.setCurrentUser(u);
				popupMenuController.updateProfileAction();
				if (u.isAdmin()) {
					navegador.navegarPara("ADM");
				} else if (u.isContratado()) {
					navegador.navegarPara("CONTRATADO");
				} else if (u.isContratante()) {
					navegador.navegarPara("SERVICOS");
				} else {
					navegador.navegarPara("LOGIN");
				}
			}else {
				JOptionPane.showMessageDialog(null, "Usuario ou senha incorretos", "Erro", JOptionPane.ERROR_MESSAGE);
			}
			
			this.view.limparFormulario();
	
		});

		this.view.cadastro(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        telaCadastro.limparCampos();
		        navegador.navegarPara("CADASTRO");
		    }
		});
	}
	

}