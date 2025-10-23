package controller;

import view.DrawerMenu;
import view.TelaConfigUser;
import controller.TelaConfigUserController;
import model.Usuario;
import model.UsuarioDAO;

public class PopupMenuController {
	private final DrawerMenu view;
	private final Navegador navegador;
	private final TelaFactory telaFactory;
	
	public PopupMenuController(DrawerMenu view, Navegador navegador, TelaFactory telaFactory){
		this.view = view;
		this.navegador = navegador;
		this.telaFactory = telaFactory;
		// Remover chamada a setConfigUserAction
	}
	
	public DrawerMenu getView(){
		return view;
	}
	
	// Atualiza o botão Profile do DrawerMenu para sempre abrir TelaConfigUser
	public void updateProfileAction() {
		view.setNavegador(navegador); // Garante que listeners estejam atualizados
	}

}