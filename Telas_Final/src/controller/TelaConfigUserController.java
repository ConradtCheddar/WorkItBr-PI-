package controller;

import javax.swing.JOptionPane;

import model.Usuario;
import model.UsuarioDAO;
import util.FieldValidator;
import view.Mensagem;
import view.TelaConfigUser;

public class TelaConfigUserController {
    private final TelaConfigUser view;
    private final UsuarioDAO model;
    private final Navegador navegador;
    private final Usuario usuario;

    public TelaConfigUserController(TelaConfigUser view, UsuarioDAO model, Navegador navegador, Usuario usuario) {
        this.view = view;
        this.model = model;
        this.navegador = navegador;
        this.usuario = usuario;

        try {
            model.decode64(usuario);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        this.view.setUserData(usuario);

        Mensagem M = new Mensagem();
        
        this.view.addAlterarDadosListener(e -> {
        	
            String email = view.getEmail().trim();
            String telefone = view.getTelefone().trim();
            String cpfCnpj = view.getCpfCnpj().trim();
            String novaSenha = view.getSenha();
            
            // Validate name (must not be empty or whitespace-only)
            if (!view.isNomeValido()) {
               M.Erro("Nome inválido! Não pode ser vazio ou apenas espaços.","Erro de Validação");
                return;
            }

            // If a new password was entered, ensure it's not only whitespace
            if (novaSenha != null && !novaSenha.isEmpty() && !view.isSenhaValida()) {
             M.Erro("Senha inválida! Não pode ser apenas espaços.","Erro de Validação");
                return;
            }
            
            if (!FieldValidator.validarEmail(email)) {
                M.Erro("Email inválido!", "Erro de Validação");
                return;
            }

            if (!FieldValidator.validarTelefone(telefone)) {
              M.Erro("Telefone inválido! Deve ter 10 ou 11 dígitos.", "Erro de Validação");
                return;
            }

            // Unified validation for CPF or CNPJ
            if (!FieldValidator.validarCpfCnpj(cpfCnpj)) {
               M.Erro("CPF/CNPJ inválido! Digite 11 dígitos para CPF ou 14 para CNPJ.", "Erro de Validação");
                return;
            }
            
            // If a new password is provided, update it (no confirmation field present)
            if (novaSenha != null && !novaSenha.isEmpty()) {
                // optional: add complexity/length checks here
                usuario.setSenha(novaSenha);
                return;
            }
            M.Sucesso("Dados salvos com sucesso", "Sucesso");
            
            usuario.setUsuario(view.getNome());
            
            usuario.setEmail(email);
            usuario.setTelefone(telefone);
            usuario.setCpfCnpj(cpfCnpj);
            usuario.setGithub(view.getGithub());
            model.atualizarUsuario(usuario);
        });

        this.view.addAlterarImagemListener(e -> {
            String caminho = view.selecionarImagem();
            if (caminho != null) {
                usuario.setCaminhoFoto(caminho);
                try {
                    model.code64(usuario);
                    model.atualizarUsuario(usuario);
                    model.decode64(usuario);
                    this.view.setUserData(usuario);
                } catch (java.io.FileNotFoundException ex) {
                    ex.printStackTrace();
                  M.Erro("Erro: Arquivo de imagem não encontrado.", "Erro");
                } catch (java.io.IOException ex) {
                    ex.printStackTrace();
                  M.Erro("Erro ao processar a imagem.", "Erro");
                } catch (Exception ex) {
                    ex.printStackTrace();
                  M.Erro("Erro inesperado ao alterar imagem.", "Erro");
                }
            }
        });
    }
}