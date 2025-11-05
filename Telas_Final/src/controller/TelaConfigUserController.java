// Define o pacote onde esta classe está localizada
package controller;

// Importa JOptionPane para exibir diálogos de mensagem ao usuário
import javax.swing.JOptionPane;

// Importa a classe Usuario que representa um usuário no sistema
import model.Usuario;
// Importa UsuarioDAO, responsável por operações de banco de dados relacionadas a usuários
import model.UsuarioDAO;
// Importa a classe utilitária para validação de campos de formulário
import util.FieldValidator;
// Importa a view da tela de configuração de usuário
import view.TelaConfigUser;

/**
 * Controller da tela de configuração/edição de dados do usuário.
 * <p>
 * Carrega os dados iniciais do usuário (decodificando a imagem base64),
 * valida campos editados (email/telefone/CPF-CNPJ) e delega atualizações ao
 * {@link UsuarioDAO}. Também trata a seleção e envio de imagem do usuário.
 * </p>
 */
public class TelaConfigUserController {
    // Referência à camada de visualização (interface gráfica) da tela de configuração
    private final TelaConfigUser view;
    // Referência ao DAO que gerencia operações de banco de dados para usuários
    private final UsuarioDAO model;
    // Referência ao navegador que controla a navegação entre telas
    private final Navegador navegador;
    // Referência ao usuário que está sendo editado
    private final Usuario usuario;

    /**
     * Construtor que inicializa o controller e configura os listeners.
     * 
     * @param view referência à tela de configuração de usuário
     * @param model referência ao DAO de usuários
     * @param navegador objeto que controla a navegação
     * @param usuario objeto do usuário a ser editado
     */
    public TelaConfigUserController(TelaConfigUser view, UsuarioDAO model, Navegador navegador, Usuario usuario) {
        // Atribui a referência da view recebida ao atributo da classe
        this.view = view;
        // Atribui a referência do model (DAO) recebido ao atributo da classe
        this.model = model;
        // Atribui a referência do navegador recebido ao atributo da classe
        this.navegador = navegador;
        // Atribui a referência do usuário recebido ao atributo da classe
        this.usuario = usuario;

        // Carrega a foto do banco (base64 -> imagem) e preenche campos da view
        try {
            // Decodifica a imagem em Base64 armazenada no banco e salva como arquivo temporário
            model.decode64(usuario);
        } catch (Exception ex) {
            // Em caso de erro ao decodificar imagem, imprime stack trace
            ex.printStackTrace();
        }
        // Preenche os campos da view com os dados do usuário
        this.view.setUserData(usuario);

        // Listener para alterar dados: valida entrada e atualiza via DAO
        // Configura o listener do botão "alterar dados" na view
        this.view.addAlterarDadosListener(e -> {
            // Obtém o email do campo de texto e remove espaços em branco nas extremidades
            String email = view.getEmail().trim();
            // Obtém o telefone do campo de texto e remove espaços
            String telefone = view.getTelefone().trim();
            // Obtém o CPF/CNPJ do campo de texto e remove espaços
            String cpfCnpj = view.getCpfCnpj().trim();
            
            // Validar email
            // Usa o validador para verificar se o formato do email é válido
            if (!FieldValidator.validarEmail(email)) {
                // Se o email for inválido, exibe mensagem de erro e interrompe
                JOptionPane.showMessageDialog(null, "Email inválido!", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Validar telefone
            // Usa o validador para verificar se o telefone tem 10 ou 11 dígitos
            if (!FieldValidator.validarTelefone(telefone)) {
                // Se o telefone for inválido, exibe mensagem de erro e interrompe
                JOptionPane.showMessageDialog(null, "Telefone inválido! Deve ter 10 ou 11 dígitos.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Validar CPF ou CNPJ
            // Remove caracteres de formatação (pontos, hífens, barras) para validar apenas os números
            String cpfLimpo = FieldValidator.removerFormatacao(cpfCnpj);
            // Verifica se tem 11 dígitos (CPF)
            if (cpfLimpo.length() == 11) {
                // Valida usando o algoritmo de CPF
                if (!FieldValidator.validarCPF(cpfCnpj)) {
                    // Se o CPF for inválido, exibe mensagem de erro e interrompe
                    JOptionPane.showMessageDialog(null, "CPF inválido!", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } else if (cpfLimpo.length() == 14) {
                // Verifica se tem 14 dígitos (CNPJ)
                // Valida usando o algoritmo de CNPJ
                if (!FieldValidator.validarCNPJ(cpfCnpj)) {
                    // Se o CNPJ for inválido, exibe mensagem de erro e interrompe
                    JOptionPane.showMessageDialog(null, "CNPJ inválido!", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } else {
                // Se não tem 11 nem 14 dígitos, exibe mensagem de erro
                JOptionPane.showMessageDialog(null, "CPF/CNPJ inválido! Digite 11 dígitos para CPF ou 14 para CNPJ.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Atualiza objeto usuário com os valores da view e persiste
            // Atualiza o nome de usuário com o valor do campo
            usuario.setUsuario(view.getNome());
            // Atualiza a senha com o valor do campo
            usuario.setSenha(view.getSenha());
            // Atualiza o email com o valor validado
            usuario.setEmail(email);
            // Atualiza o telefone com o valor validado
            usuario.setTelefone(telefone);
            // Atualiza o CPF/CNPJ com o valor validado
            usuario.setCpfCnpj(cpfCnpj);
            // Atualiza o GitHub com o valor do campo
            usuario.setGithub(view.getGithub());
            // Salva as alterações no banco de dados através do DAO
            model.atualizarUsuario(usuario);
        });

        // Listener para alterar imagem de perfil: seleciona arquivo, converte e persiste
        // Configura o listener do botão "alterar imagem" na view
        this.view.addAlterarImagemListener(e -> {
            // Abre um diálogo de seleção de arquivo e retorna o caminho selecionado
            String caminho = view.selecionarImagem();
            // Verifica se um arquivo foi selecionado (não cancelou o diálogo)
            if (caminho != null) {
                // Define o caminho da foto no objeto usuário
                usuario.setCaminhoFoto(caminho);
                try {
                    // Converte e salva a imagem como base64 no banco
                    // Lê o arquivo de imagem, converte para Base64 e armazena no objeto usuário
                    model.code64(usuario);
                    // Atualiza os dados do usuário no banco de dados (incluindo a nova imagem)
                    model.atualizarUsuario(usuario);
                    // Atualiza a imagem exibida na view a partir do banco
                    // Decodifica o Base64 de volta para arquivo temporário
                    model.decode64(usuario);
                    // Atualiza a view com os dados atualizados (incluindo nova imagem)
                    this.view.setUserData(usuario);
                } catch (java.io.FileNotFoundException ex) {
                    // Em caso de arquivo não encontrado:
                    // Imprime stack trace
                    ex.printStackTrace();
                    // Exibe mensagem de erro ao usuário
                    JOptionPane.showMessageDialog(null, "Erro: Arquivo de imagem não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
                } catch (java.io.IOException ex) {
                    // Em caso de erro de I/O (leitura/escrita):
                    // Imprime stack trace
                    ex.printStackTrace();
                    // Exibe mensagem de erro ao usuário com detalhes
                    JOptionPane.showMessageDialog(null, "Erro ao processar imagem: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    // Em caso de qualquer outra exceção:
                    // Imprime stack trace
                    ex.printStackTrace();
                    // Exibe mensagem de erro genérica
                    JOptionPane.showMessageDialog(null, "Erro inesperado ao alterar imagem.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    } // Fim do construtor TelaConfigUserController
} // Fim da classe TelaConfigUserController