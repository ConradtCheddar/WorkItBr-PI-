package controller;

import view.DrawerMenu;

public class PopupMenuController {
	private final DrawerMenu view;
	private final Navegador navegador;
	
	public PopupMenuController(DrawerMenu view, Navegador navegador){
		this.view = view;
		this.navegador = navegador;
	}
	
	public DrawerMenu getView(){
		return view;
	}
	
	
	
	
}
