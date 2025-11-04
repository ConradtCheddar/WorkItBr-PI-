package controller;

import javax.swing.JOptionPane;

import model.Usuario;
import model.UsuarioDAO;
import util.FieldValidator;
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

        // Carrega a foto do banco (base64 -> imagem)
        try {
            model.decode64(usuario);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        this.view.setUserData(usuario);

        // Listener para alterar dados
        this.view.addAlterarDadosListener(e -> {
            String email = view.getEmail().trim();
            String telefone = view.getTelefone().trim();
            String cpfCnpj = view.getCpfCnpj().trim();
            
            // Validar email
            if (!FieldValidator.validarEmail(email)) {
                JOptionPane.showMessageDialog(null, "Email inválido!", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Validar telefone
            if (!FieldValidator.validarTelefone(telefone)) {
                JOptionPane.showMessageDialog(null, "Telefone inválido! Deve ter 10 ou 11 dígitos.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Validar CPF ou CNPJ
            String cpfLimpo = FieldValidator.removerFormatacao(cpfCnpj);
            if (cpfLimpo.length() == 11) {
                if (!FieldValidator.validarCPF(cpfCnpj)) {
                    JOptionPane.showMessageDialog(null, "CPF inválido!", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } else if (cpfLimpo.length() == 14) {
                if (!FieldValidator.validarCNPJ(cpfCnpj)) {
                    JOptionPane.showMessageDialog(null, "CNPJ inválido!", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } else {
                JOptionPane.showMessageDialog(null, "CPF/CNPJ inválido! Digite 11 dígitos para CPF ou 14 para CNPJ.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            usuario.setUsuario(view.getNome());
            usuario.setSenha(view.getSenha());
            usuario.setEmail(email);
            usuario.setTelefone(telefone);
            usuario.setCpfCnpj(cpfCnpj);
            usuario.setGithub(view.getGithub());
            model.atualizarUsuario(usuario);
        });

        // Listener para alterar imagem
        this.view.addAlterarImagemListener(e -> {
            String caminho = view.selecionarImagem();
            if (caminho != null) {
                usuario.setCaminhoFoto(caminho);
                try {
                    // Converte e salva a imagem como base64
                    model.code64(usuario);
                    model.atualizarUsuario(usuario);
                    // Atualiza a imagem exibida
                    model.decode64(usuario);
                    this.view.setUserData(usuario);
                } catch (java.io.FileNotFoundException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Erro: Arquivo de imagem não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
                } catch (java.io.IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Erro ao processar imagem: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Erro inesperado ao alterar imagem.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}