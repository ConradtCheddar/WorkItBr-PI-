package controller;

import view.DrawerMenu;
import view.TelaConfigUser;
import controller.TelaConfigUserController;
import model.Usuario;
import model.UsuarioDAO;

public class PopupMenuController {
	private final DrawerMenu view;
	private final Navegador navegador;
	
	public PopupMenuController(DrawerMenu view, Navegador navegador){
		this.view = view;
		this.navegador = navegador;
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