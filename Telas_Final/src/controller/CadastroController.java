// Define o pacote onde esta classe está localizada
package controller;

// Importa MouseAdapter para criar listeners de eventos de mouse personalizados
import java.awt.event.MouseAdapter;
// Importa MouseEvent que contém informações sobre eventos de mouse
import java.awt.event.MouseEvent;

// Importa JOptionPane para exibir diálogos de mensagem ao usuário
import javax.swing.JOptionPane;

// Importa a classe Servico que representa um serviço no sistema
import model.Servico;
// Importa ServicoDAO, responsável por operações de banco de dados relacionadas a serviços
import model.ServicoDAO;
// Importa a classe Usuario que representa um usuário no sistema
import model.Usuario;
// Importa UsuarioDAO, responsável por operações de banco de dados relacionadas a usuários
import model.UsuarioDAO;
// Importa a classe utilitária para validação de campos de formulário
import util.FieldValidator;
// Importa a view da tela de cadastro de usuário
import view.TelaCadastro;
// Importa a view da tela de cadastro de contratante
import view.TelaCadastroContratante;

/**
 * Controller responsável pelo fluxo de cadastro de usuário (tela de registro).
 * <p>
 * Valida campos usando {@link FieldValidator}, cria uma instância de
 * {@link Usuario} e delega a persistência ao {@link UsuarioDAO}. Caso o
 * cadastro seja bem-sucedido, direciona o usuário de volta à tela de login.
 * </p>
 */
public class CadastroController {
	// Referência à camada de visualização (interface gráfica) da tela de cadastro
	private final TelaCadastro view;
	// Referência ao DAO que gerencia operações de banco de dados para usuários
	private final UsuarioDAO model;
	// Referência ao objeto que controla a navegação entre telas
	private final Navegador navegador;

	/**
	 * Construtor que inicializa o controller e configura os listeners.
	 * 
	 * @param view referência à tela de cadastro
	 * @param model referência ao DAO de usuários
	 * @param navegador objeto que controla a navegação entre telas
	 */
	public CadastroController(TelaCadastro view, UsuarioDAO model, Navegador navegador){
		// Atribui a referência da view recebida ao atributo da classe
		this.view = view;
		// Atribui a referência do model (DAO) recebido ao atributo da classe
		this.model = model;
		// Atribui a referência do navegador recebido ao atributo da classe
		this.navegador = navegador;
		
		// Ao clicar em cadastrar: validação de campos e chamada ao DAO
		// Configura o listener do botão "cadastrar" na view
		this.view.cadastrar(e ->{
			// Obtém o email do campo de texto e remove espaços em branco nas extremidades
			String email = this.view.getTfEmail().getText().trim();
			// Obtém o nome de usuário do campo de texto e remove espaços
		    String usuario = this.view.getTfUsuario().getText().trim();
		    // Obtém o CPF/CNPJ do campo de texto e remove espaços
		    String cpf = this.view.getTfCPF().getText().trim();
		    // Obtém o telefone do campo de texto e remove espaços
		    String telefone = this.view.getTfTelefone().getText().trim();
		    // Obtém a senha do campo de senha e converte o array de char para String
		    String senha1 = new String(this.view.getSenha().getPassword());
		    // Obtém a confirmação de senha do campo de senha e converte para String
		    String senha2Text = new String(this.view.getSenha2().getPassword());
			
		    // Validar email
		    // Usa o validador para verificar se o formato do email é válido
		    if (!FieldValidator.validarEmail(email)) {
		        // Se o email for inválido, exibe mensagem de erro e interrompe o cadastro
		        JOptionPane.showMessageDialog(null, "Email inválido!", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
		        return;
		    }
		    
		    // Validar telefone
		    // Usa o validador para verificar se o telefone tem 10 ou 11 dígitos
		    if (!FieldValidator.validarTelefone(telefone)) {
		        // Se o telefone for inválido, exibe mensagem de erro e interrompe o cadastro
		        JOptionPane.showMessageDialog(null, "Telefone inválido! Deve ter 10 ou 11 dígitos.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
		        return;
		    }
		    
		    // Validar CPF ou CNPJ
		    // Remove caracteres de formatação (pontos, hífens, barras) para validar apenas os números
		    String cpfLimpo = FieldValidator.removerFormatacao(cpf);
		    // Variável que armazenará o resultado da validação
		    boolean cpfValido = false;
		    // Verifica se tem 11 dígitos (CPF)
		    if (cpfLimpo.length() == 11) {
		        // Valida usando o algoritmo de CPF
		        cpfValido = FieldValidator.validarCPF(cpf);
		        // Se o CPF for inválido, exibe mensagem de erro e interrompe
		        if (!cpfValido) {
		            JOptionPane.showMessageDialog(null, "CPF inválido!", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
		            return;
		        }
		    } else if (cpfLimpo.length() == 14) {
		        // Verifica se tem 14 dígitos (CNPJ)
		        // Valida usando o algoritmo de CNPJ
		        cpfValido = FieldValidator.validarCNPJ(cpf);
		        // Se o CNPJ for inválido, exibe mensagem de erro e interrompe
		        if (!cpfValido) {
		            JOptionPane.showMessageDialog(null, "CNPJ inválido!", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
		            return;
		        }
		    } else {
		        // Se não tem 11 nem 14 dígitos, exibe mensagem de erro
		        JOptionPane.showMessageDialog(null, "CPF/CNPJ inválido! Digite 11 dígitos para CPF ou 14 para CNPJ.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
		        return;
		    }
		    
		    // Cria um novo objeto Usuario com os dados validados do formulário
		    // Parâmetros: email, nome de usuário, cpf, telefone, senha, imagem (null), 
		    // se é contratado (boolean do radio button), se é contratante (boolean), se é admin (false)
			Usuario u = new Usuario(email, usuario, cpf, telefone, senha1, null, this.view.getRdbtnContratado().isSelected(), this.view.getRdbtnContratante().isSelected(), false);

			// Tenta cadastrar o usuário no banco de dados, passando também a confirmação de senha
			boolean sucesso = model.cadastrarU(u, senha2Text);
			// Se o cadastro foi bem-sucedido
			if (sucesso) {
				// Navega para a tela de login sem adicionar ao histórico (false)
				navegador.navegarPara("LOGIN", false);
				// Limpa todos os campos do formulário
				this.view.limparCampos();
			}
			
		});
	} // Fim do construtor CadastroController
} // Fim da classe CadastroController