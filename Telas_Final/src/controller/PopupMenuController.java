package controller;

import view.DrawerMenu;
import view.Mensagem;
import view.TelaConfigUser;
import controller.TelaConfigUserController;
import model.Usuario;
import model.UsuarioDAO;
import javax.swing.JOptionPane;

public class PopupMenuController {
	private final DrawerMenu view;
	private final Navegador navegador;
	private final TelaFactory telaFactory;
	private boolean listenersInitialized = false;
	
	public PopupMenuController(DrawerMenu view, Navegador navegador, TelaFactory telaFactory){
		this.view = view;
		this.navegador = navegador;
		this.telaFactory = telaFactory;
		updateMenuState();
	}
	
	Mensagem M = new Mensagem();
	
	public DrawerMenu getView(){
		return view;
	}

	private void initializeListeners() {
		if (listenersInitialized) {
			return;
		}

		view.getBtnHome().addActionListener(e -> {
			navigateToHome();
			view.toggleMenu();
		});

		view.getBtnProfile().addActionListener(e -> {
			navigateToProfile();
			view.toggleMenu();
		});

		view.getBtnTrabalhos().addActionListener(e -> {
			navigateToTrabalhos();
			view.toggleMenu();
		});
		
		view.getBtnSettings().addActionListener(e -> {
			navegador.navegarPara("TEMP");
			view.toggleMenu();
		});

		view.getBtnLogout().addActionListener(e -> {
			performLogout();
			view.toggleMenu();
		});

		listenersInitialized = true;
	}

	public void updateMenuState() {
		Usuario currentUser = navegador.getCurrentUser();
		boolean isLoggedIn = currentUser != null;

		view.getBtnProfile().setEnabled(isLoggedIn);
		view.getBtnLogout().setEnabled(isLoggedIn);
		view.getBtnTrabalhos().setEnabled(isLoggedIn);
		view.getBtnHome().setEnabled(isLoggedIn);
	}
	
	private void navigateToHome() {
		Usuario u = navegador.getCurrentUser();
		if (u != null) {
			if (u.isAdmin()) {
				navegador.navegarPara("ADM");
			} else if (u.isContratante()) {
				navegador.navegarPara("SERVICOS");
			} else if (u.isContratado()) {
				navegador.navegarPara("CONTRATADO");
			} else {
				navegador.navegarPara("LOGIN");
			}
		}
	}
	
	private void navigateToProfile() {
		Usuario usuario = navegador.getCurrentUser();
		if (usuario != null) {
			String panelName = telaFactory.criarTelaConfigUser(usuario);
			navegador.navegarPara(panelName);
		} else {
			M.Erro("Nenhum usuário logado.", "Erro");
		}
	}
	
	private void navigateToTrabalhos() {
		Usuario u = navegador.getCurrentUser();
		if (u != null) {
			if (u.isContratante()) {
				navegador.navegarPara("SERVICOS");
			} else if (u.isContratado()) {
				navegador.navegarPara("CONTRATADO");
			} else {
				navegador.navegarPara("TEMP");
			}
		}
	}
	
	private void performLogout() {
		navegador.clearCurrentUser();
		telaFactory.limparCache();
		navegador.removerPainel("CONFIG_USER");
		navegador.clearHistory();
		navegador.limparImagensPerfil();
		navegador.navegarPara("LOGIN", false);
		updateMenuState();
	}
	
	public void updateProfileAction() {
		initializeListeners();
		updateMenuState();
	}

}