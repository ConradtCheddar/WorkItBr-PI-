package controller;

import model.Usuario;
import model.UsuarioDAO;
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

        // Exibe os dados do usuário na tela
        this.view.setUserData(usuario);

        // Listener para alterar dados
        this.view.addAlterarDadosListener(e -> {
            usuario.setUsuario(view.getNome());
            usuario.setSenha(view.getSenha());
            usuario.setEmail(view.getEmail());
            usuario.setTelefone(view.getTelefone());
            usuario.setCpfCnpj(view.getCpfCnpj());
            usuario.setGithub(view.getGithub());
            model.atualizarUsuario(usuario);
        });

        // Listener para alterar imagem
        this.view.addAlterarImagemListener(e -> {
            String caminho = view.selecionarImagem();
            if (caminho != null) {
                usuario.setCaminhoFoto(caminho);
                model.atualizarUsuario(usuario);
            }
        });
    }
}