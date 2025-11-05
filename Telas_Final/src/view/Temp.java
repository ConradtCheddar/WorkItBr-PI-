// Define o pacote onde esta classe está localizada (agrupamento lógico de classes relacionadas)
package view;

// Importa Color para definição de cores personalizadas
import java.awt.Color;
// Importa Cursor para definir aparência do cursor do mouse
import java.awt.Cursor;
// Importa Dimension para definir dimensões de componentes
import java.awt.Dimension;
// Importa Font para trabalhar com fontes de texto
import java.awt.Font;
// Importa Image para manipulação de imagens
import java.awt.Image;
// Importa ActionEvent que contém informações sobre eventos de ação
import java.awt.event.ActionEvent;
// Importa ActionListener para capturar eventos de clique em botões
import java.awt.event.ActionListener;
// Importa ComponentAdapter para responder a eventos de redimensionamento
import java.awt.event.ComponentAdapter;
// Importa ComponentEvent que contém informações sobre eventos de componentes
import java.awt.event.ComponentEvent;
// Importa MouseAdapter para responder a eventos de mouse de forma simplificada
import java.awt.event.MouseAdapter;
// Importa MouseEvent que contém informações sobre eventos de mouse
import java.awt.event.MouseEvent;

// Importa ImageIcon para trabalhar com ícones de imagem
import javax.swing.ImageIcon;
// Importa JButton, componente de botão clicável
import javax.swing.JButton;
// Importa JLabel para exibição de textos e imagens
import javax.swing.JLabel;
// Importa JPanel, container básico para agrupar componentes
import javax.swing.JPanel;
// Importa SwingConstants para constantes de alinhamento
import javax.swing.SwingConstants;
// Importa EmptyBorder para criar bordas vazias (espaçamento)
import javax.swing.border.EmptyBorder;

// Importa o Navegador responsável por controlar navegação entre telas
import controller.Navegador;
// Importa MigLayout, gerenciador de layout avançado e flexível
import net.miginfocom.swing.MigLayout;

/**
 * Tela temporária de desenvolvimento/teste para navegação rápida entre telas.
 * <p>
 * Responsável por: fornecer botões de acesso rápido a todas as telas principais
 * do sistema durante desenvolvimento, permitir testes de navegação sem passar
 * pelo fluxo completo de login, facilitar debug e desenvolvimento de novas
 * funcionalidades. Esta tela deve ser removida ou ocultada em produção.
 * </p>
 */
public class Temp extends JPanel {

	// Identificador de versão para serialização (compatibilidade entre versões)
	private static final long serialVersionUID = 1L;

	// Referência à janela principal da aplicação
	Primario prim;

	// Navegador para controlar transições entre telas
	Navegador navegador = new Navegador(prim);

	// Botão para navegar à tela de Login
	JButton btnLogin;
	// Botão para navegar à tela de Administração
	JButton btnADM;
	// Botão para navegar à tela do Contratado
	JButton btnContratado;
	// Botão para navegar à tela do Contratante/Serviços
	JButton btnContratante;
	// Botão para navegar à tela de Cadastro de Contratante
	JButton btnCadastroContratante;

	/**
	 * Construtor que cria e configura a tela temporária de navegação.
	 * <p>
	 * Adiciona botões para todas as principais telas do sistema em um layout
	 * centralizado.
	 * </p>
	 */
	public Temp() {
		// Remove bordas para maximizar área útil
		setBorder(new EmptyBorder(0, 0, 0, 0));
		// Configura MigLayout com grid extenso (21 colunas x 18 linhas) para
		// posicionamento preciso
		setLayout(new MigLayout("fill, insets 0",
				"[20px][grow][grow][grow][grow][][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][][grow][grow][20px]",
				"[35px][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][35px]"));

		// Cria e posiciona botão de navegação para Login
		btnLogin = new JButton("Login");
		add(btnLogin, "cell 11 9,grow");

		// Cria e posiciona botão de navegação para tela ADM
		btnADM = new JButton("ADM");
		add(btnADM, "cell 11 10,grow");

		// Cria e posiciona botão de navegação para tela Contratado
		btnContratado = new JButton("Contratado");
		add(btnContratado, "cell 11 11,grow");

		btnContratante = new JButton("Contratante");
		add(btnContratante, "cell 11 12,grow");

		btnCadastroContratante = new JButton("Cadastro Contratante");
		add(btnCadastroContratante, "cell 11 13,grow");

	}

	public void login(ActionListener actionListener) {
		this.btnLogin.addActionListener(actionListener);
	}

	public void adm(ActionListener actionListener) {
		this.btnADM.addActionListener(actionListener);
	}

	public void contratado(ActionListener actionListener) {
		this.btnContratado.addActionListener(actionListener);
	}

	public void contratante(ActionListener actionListener) {
		this.btnContratante.addActionListener(actionListener);
	}

	public void cadastroContratante(ActionListener actionListener) {
		this.btnCadastroContratante.addActionListener(actionListener);
	}

}