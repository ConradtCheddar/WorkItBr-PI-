package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

import javax.swing.JOptionPane;

import model.Usuario;
import model.UsuarioDAO;
import view.Mensagem;
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
		
		initListeners();
	}
    Mensagem M = new Mensagem();
	private void initListeners() {
		this.view.login(e -> handleLoginAttempt());

		this.view.cadastro(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        telaCadastro.limparCampos();
		        navegador.navegarPara("CADASTRO");
		    }
		});
	}

	private void handleLoginAttempt() {
		String nome = view.getUsuario();
		char[] senha = view.getSenha();

		if (nome.trim().isEmpty() || senha.length == 0) {
			M.Erro("Usuário e senha não podem estar em branco.", "Erro de Validação");
			view.limparFormulario();
			Arrays.fill(senha, ' ');
			return;
		}

		try {
			Usuario u = model.login(nome, senha);
			
			if (u != null) {
				handleSuccessfulLogin(u);
			} else {
				handleFailedLogin();
			}
		} catch (Exception e) {
			handleLoginError(e);
		} finally {
			Arrays.fill(senha, ' ');
			this.view.limparFormulario();
		}
	}

	private void handleSuccessfulLogin(Usuario user) {
		model.decode64(user);
		this.navegador.setCurrentUser(user);
		popupMenuController.updateProfileAction();
		this.navegador.notifyHistoryChange();
		navigateToUserScreen(user);
	}

	private void navigateToUserScreen(Usuario user) {
		String destination;
		if (user.isAdmin()) {
			destination = "TEMP";
		} else if (user.isContratado()) {
			destination = "CONTRATADO";
		} else if (user.isContratante()) {
			destination = "SERV";
		} else {
			destination = "LOGIN";
		}
		navegador.navegarPara(destination, false);
	}

	private void handleFailedLogin() {
		M.Erro("Ocorreu um erro de comunicação com o sistema. Tente novamente mais tarde.", "Erro de Conexão");
	}

	private void handleLoginError(Exception e) {
		e.printStackTrace(); 
		M.Erro("Ocorreu um erro de comunicação com o sistema. Tente novamente mais tarde.", "Erro de Conexão");
	}
}