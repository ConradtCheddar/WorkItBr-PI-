package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

import model.Servico;
import model.ServicoDAO;
import model.Usuario;
import model.UsuarioDAO;
import util.FieldValidator;
import view.TelaCadastro;
import view.TelaCadastroContratante;

public class CadastroController {
	private final TelaCadastro view;
	private final UsuarioDAO model;
	private final Navegador navegador;

	public CadastroController(TelaCadastro view, UsuarioDAO model, Navegador navegador){
		this.view = view;
		this.model = model;
		this.navegador = navegador;
		
		this.view.cadastrar(e ->{
			String email = this.view.getTfEmail().getText().trim();
		    String usuario = this.view.getTfUsuario().getText().trim();
		    String cpf = this.view.getTfCPF().getText().trim();
		    String telefone = this.view.getTfTelefone().getText().trim();
		    String senha1 = new String(this.view.getSenha().getPassword());
		    String senha2Text = new String(this.view.getSenha2().getPassword());
			
		    if (!FieldValidator.validarEmail(email)) {
		        JOptionPane.showMessageDialog(view, "Email inválido!", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
		        return;
		    }
		    
		    if (!FieldValidator.validarTelefone(telefone)) {
		        JOptionPane.showMessageDialog(view, "Telefone inválido! Deve ter 10 ou 11 dígitos.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
		        return;
		    }
		    
			String cpfLimpo = FieldValidator.removerFormatacao(cpf);
			if (!FieldValidator.validarCpfCnpj(cpf)) {
				JOptionPane.showMessageDialog(view, "CPF/CNPJ inválido! Digite 11 dígitos para CPF ou 14 para CNPJ.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			Usuario u = new Usuario(email, usuario, cpf, telefone, senha1, null, this.view.getRdbtnContratado().isSelected(), this.view.getRdbtnContratante().isSelected(), false);

			boolean sucesso = model.cadastrarU(u, senha2Text);
			if (sucesso) {
				navegador.navegarPara("LOGIN", false);
				this.view.limparCampos();
			}
		});
	}
}