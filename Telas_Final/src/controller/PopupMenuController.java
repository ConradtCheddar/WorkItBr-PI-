package controller;

import view.DrawerMenu;

public class PopupMenuController {
	private final DrawerMenu view;
	private final Navegador navegador;
	
	public PopupMenuController(DrawerMenu view, Navegador navegador){
		this.view = view;
		this.navegador = navegador;
		
//		this.view.login(e ->{
//			
//		});

//		this.view.cadastro(new MouseAdapter() {
//		    @Override
//		    public void mouseClicked(MouseEvent e) {
//		        navegador.navegarPara("CADASTRO");
//		    }
//		});
	}
	
	public DrawerMenu getView(){
		return view;
	}
	
	
	
	
}
