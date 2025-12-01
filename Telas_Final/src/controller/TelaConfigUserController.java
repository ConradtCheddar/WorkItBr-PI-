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
                // DO NOT return here — continue to update other fields and persist to DB
            }

            // Update other user fields and save to DB
            usuario.setUsuario(view.getNome());
            usuario.setEmail(email);
            usuario.setTelefone(telefone);
            usuario.setCpfCnpj(cpfCnpj);
            usuario.setGithub(view.getGithub());

            // Ensure we have a valid user id to update
            if (usuario.getIdUsuario() <= 0) {
                // try fallback: use current user from navegador if available
                Usuario navUser = navegador.getCurrentUser();
                if (navUser != null && navUser.getIdUsuario() > 0) {
                    System.out.println("[ConfigUser] using navegador.currentUser id=" + navUser.getIdUsuario() + " as fallback");
                    usuario.setIdUsuario(navUser.getIdUsuario());
                }
            }

            if (usuario.getIdUsuario() <= 0) {
                M.Erro("Erro: id do usuário inválido. Não foi possível atualizar.", "Erro");
                return;
            }

            try {
                System.out.println("[ConfigUser] Atualizando usuário id=" + usuario.getIdUsuario() + ", nome=" + usuario.getUsuario());
                System.out.println("[ConfigUser] Dados enviados -> nome='" + usuario.getUsuario() + "', email='" + usuario.getEmail() + "', telefone='" + usuario.getTelefone() + "', cpfCnpj='" + usuario.getCpfCnpj() + "', github='" + usuario.getGithub() + "', senha='" + (usuario.getSenha() != null ? "***" : null) + "'");
                int rows = model.atualizarUsuario(usuario);
                System.out.println("[ConfigUser] linhas atualizadas: " + rows);
                if (rows <= 0) {
                    M.Erro("Nenhuma linha atualizada. Verifique o id do usuário e a conexão com o banco.", "Erro");
                    return;
                }
                // reload user from DB to ensure updated state (and imagem64 handling)
                Usuario atualizado = model.getUsuarioById(usuario.getIdUsuario());
                if (atualizado != null) {
                    // copy back id (getUsuarioById sets it) and replace local reference
                    this.usuario.setEmail(atualizado.getEmail());
                    this.usuario.setUsuario(atualizado.getUsuario());
                    this.usuario.setCpfCnpj(atualizado.getCpfCnpj());
                    this.usuario.setTelefone(atualizado.getTelefone());
                    this.usuario.setSenha(atualizado.getSenha());
                    this.usuario.setGithub(atualizado.getGithub());
                    this.usuario.setImagem64(atualizado.getImagem64());
                    this.usuario.setCaminhoFoto(atualizado.getCaminhoFoto());

                    // update view with fresh data
                    this.view.setUserData(this.usuario);
                }

                M.Sucesso("Dados salvos com sucesso", "Sucesso");
            } catch (Exception ex) {
                ex.printStackTrace();
                M.Erro("Erro ao atualizar usuário: " + ex.getMessage(), "Erro");
            }
        });

        this.view.addAlterarImagemListener(e -> {
            String caminho = view.selecionarImagem();
            if (caminho != null) {
                usuario.setCaminhoFoto(caminho);
                try {
                    model.code64(usuario);
                    int rowsImg = model.atualizarUsuario(usuario);
                    System.out.println("[ConfigUser] atualizarImagem - linhas atualizadas: " + rowsImg);
                    if (rowsImg <= 0) {
                        M.Erro("Falha ao salvar imagem. Nenhuma linha atualizada.", "Erro");
                        return;
                    }
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