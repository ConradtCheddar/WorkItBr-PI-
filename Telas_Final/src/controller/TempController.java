package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import model.Usuario;
import model.UsuarioDAO;
import view.TelaCadastro;
import view.Temp;

public class TempController {
	private final Temp view;
	private final UsuarioDAO model;
	private final Navegador navegador;

	public TempController(Temp view, UsuarioDAO model, Navegador navegador){
		this.view = view;
		this.model = model;
		this.navegador = navegador;
		
		this.view.login(e ->{
			navegador.limparImagensPerfil();
			navegador.navegarPara("LOGIN");
		});
		this.view.adm(e ->{
			navegador.navegarPara("ADM");
		});
		this.view.contratante(e ->{
			navegador.navegarPara("SERVICOS");
		});
		this.view.contratado(e ->{
			navegador.navegarPara("CONTRATADO");
		});
		
	}
}