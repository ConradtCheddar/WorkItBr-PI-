package controller;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import model.Usuario;
import model.UsuarioDAO;
import util.FieldValidator;
import view.Mensagem;
import view.TelaCadastro;

public class CadastroController {
	private final TelaCadastro view;
	private final UsuarioDAO model;
	private final Navegador navegador;

	public CadastroController(TelaCadastro view, UsuarioDAO model, Navegador navegador){
		this.view = view;
		this.model = model;
		this.navegador = navegador;
		
		this.view.cadastrar(e ->{
			/**
			 * salva os valores em variaveis
			 */
			Mensagem M = new Mensagem();
			String email = this.view.getTfEmail().getText().trim();
		    String usuario = this.view.getTfUsuario().getText();
		    String cpf = this.view.getTfCPF().getText().trim();
		    String telefone = this.view.getTfTelefone().getText().trim();
		    String senha1 = new String(this.view.getSenha().getPassword());
		    String senha2 = new String(this.view.getSenha2().getPassword());
		    
		    /**
		     * faz validação dos valores
		     */
		    if(email.trim().isEmpty()||usuario.trim().isEmpty()||cpf.trim().isEmpty()||telefone.trim().isEmpty()||senha1.trim().isEmpty()||senha2.trim().isEmpty()) {
		    	M.Erro("Preencha todos os campos", "Erro");
		    }else if(!senha1.equals(senha2)){
		    	M.Erro("Campos de senhas diferentes", "Erro de Validação");
		    }else {
		    	if (!FieldValidator.validarEmail(email)) {
			        M.Erro("Email inválido!", "Erro de Validação");
			        return;
			    }
			    
			    if (!FieldValidator.validarTelefone(telefone)) {
			        M.Erro("Telefone inválido! Deve ter 10 ou 11 dígitos.", "Erro de Validação");
			        return;
			    }
			    
			    String cpfLimpo = FieldValidator.removerFormatacao(cpf);
			    boolean cpfValido = false;
			    if (cpfLimpo.length() == 11) {
			        cpfValido = FieldValidator.validarCPF(cpf);
			        if (!cpfValido) {
			            M.Erro("CPF inválido!", "Erro de Validação");
			            return;
			        }
			    } else if (cpfLimpo.length() == 14) {
			        cpfValido = FieldValidator.validarCNPJ(cpf);
			        if (!cpfValido) {
			            M.Erro("CNPJ inválido!", "Erro de Validação");
			            return;
			        }
			    } else {
			        M.Erro("CPF/CNPJ inválido! Digite 11 dígitos para CPF ou 14 para CNPJ.", "Erro de Validação");
			        return;
			    }
			    
			    /**
			     * salva os valores
			     */
			    Usuario u = new Usuario(email, usuario, cpf, telefone, senha1, null, this.view.getRdbtnContratado().isSelected(), this.view.getRdbtnContratante().isSelected(), false);
			    
			    boolean sucesso = false;
				try {
					sucesso = model.cadastrarUsuario(u);
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			    if (sucesso) {
					M.Sucesso("Cadastro realizado com sucesso", "Sucesso");
					this.view.limparCampos();
					navegador.navegarPara("LOGIN", false);
				}else {
					M.Erro("Cadastro falhou", "Erro inesperado");
				}
		    	
		    }
		});
	}
}