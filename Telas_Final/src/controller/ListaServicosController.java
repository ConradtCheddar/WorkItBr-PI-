// Define o pacote onde esta classe está localizada (agrupamento lógico de classes relacionadas)
package controller;

// Importa a classe ArrayList para trabalhar com listas dinâmicas de objetos
import java.util.ArrayList;

// Importa JOptionPane para exibir diálogos de mensagem, confirmação e erro ao usuário
import javax.swing.JOptionPane;
// Importa JTable, componente Swing que exibe dados em formato de tabela
import javax.swing.JTable;
// Importa DefaultTableModel, o modelo padrão de dados para JTable que permite edição
import javax.swing.table.DefaultTableModel;
// Importa TableModel, interface que define como os dados são estruturados na tabela
import javax.swing.table.TableModel;

// Importa a classe de modelo Servico que representa um serviço no sistema
import model.Servico;
// Importa ServicoDAO, classe responsável por operações de banco de dados relacionadas a serviços
import model.ServicoDAO;
// Importa a view (interface gráfica) da tela de listagem de serviços
import view.TelaListaServicos;
// Importa a view de visualização de serviço em andamento
import view.VisServicoAndamento;
// Importa a view de visualização padrão de serviço
import view.VisServico;
// Importa a view de visualização de serviço para o contratante
import view.VisServicoCnte;
// Importa a view de visualização de serviço aceito para o contratante
import view.VisServicoCnteAceito;
// Importa o controller da visualização de serviço
import controller.VisServicoController;
// Importa o controller da visualização de serviço do contratante
import controller.VisServicoCnteController;
// Importa o controller da visualização de serviço aceito do contratante
import controller.VisServicoCnteAceitoController;

/**
 * Controller que gerencia as ações na tela de listagem de serviços.
 * <p>
 * Responsável por: navegar para cadastro, visualizar detalhes de um serviço
 * selecionado (criando a visualização adequada conforme o estado do serviço),
 * editar linhas da tabela (aplicando as alterações ao DAO) e excluir serviços
 * (com confirmação e validações).
 * </p>
 */
public class ListaServicosController {
	// Referência à camada de visualização (interface gráfica) da tela de listagem de serviços
	private final TelaListaServicos view;
	// Referência ao DAO (Data Access Object) que gerencia operações de banco de dados para serviços
	private final ServicoDAO model;
	// Referência ao objeto Navegador responsável por controlar a navegação entre telas
	private final Navegador navegador;
	// Referência à fábrica de telas que cria instâncias de visualizações dinamicamente
	private final TelaFactory telaFactory;
	// Instância estática que mantém referência ao controller atualmente ativo (padrão Singleton parcial)
	// Usada para permitir atualização da tabela de forma estática de outras partes do sistema
	private static ListaServicosController instanciaAtual;

	/**
	 * Construtor do controller que inicializa os componentes e configura os listeners dos botões.
	 * 
	 * @param view referência à tela de listagem de serviços (interface gráfica)
	 * @param model referência ao DAO que acessa a base de dados de serviços
	 * @param navegador objeto que controla a navegação entre diferentes telas do sistema
	 * @param telaFactory fábrica que cria instâncias dinâmicas de telas de visualização
	 */
	public ListaServicosController(TelaListaServicos view, ServicoDAO model, Navegador navegador, TelaFactory telaFactory) {
		// Atribui a referência da view recebida ao atributo da classe
		this.view = view;
		// Atribui a referência do model (DAO) recebido ao atributo da classe
		this.model = model;
		// Atribui a referência do navegador recebido ao atributo da classe
		this.navegador = navegador;
		// Atribui a referência da fábrica de telas recebida ao atributo da classe
		this.telaFactory = telaFactory;
		// Armazena a instância atual deste controller na variável estática para acesso global
		instanciaAtual = this;

		// Configura o listener do botão "cadastrar" na view
		// Botão "cadastrar": navegação para a tela de cadastro de contratante
		this.view.cadastrar(e -> {
			// Chama o navegador para mudar para a tela de cadastro de contratante
			navegador.navegarPara("CADASTRO_CONTRATANTE");
		});

		// Configura o listener do botão "visualizar" na view
		// Botão "visualizar": abre a visualização detalhada do serviço selecionado
		this.view.visualizar(e -> {
			int selectedRow = view.getTableServicos().getSelectedRow();
			if (selectedRow >= 0) {
				try {
					int id = (int) view.getTableServicos().getValueAt(selectedRow, 0);
					Servico s = this.model.buscarServicoPorId(id);
					if (s == null) {
						JOptionPane.showMessageDialog(view, "Serviço não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					String panelName = Boolean.TRUE.equals(s.getAceito())
						? telaFactory.criarVisServicoCnteAceito(s)
						: telaFactory.criarVisServicoCnte(s);
					navegador.navegarPara(panelName);

				} catch (Exception ex) {
					JOptionPane.showMessageDialog(view, "Erro ao carregar o serviço.", "Erro", JOptionPane.ERROR_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(view, "Nenhum serviço selecionado.", "Aviso", JOptionPane.WARNING_MESSAGE);
			}
		});

		// Configura o listener do botão "deletar" na view
		// Botão "deletar": confirma e deleta serviço selecionado, protegendo serviços aceitos
		this.view.deletar(e -> {
			JTable table = view.getTableServicos();
			DefaultTableModel modelTable = (DefaultTableModel) table.getModel();
			int selectedRow = table.getSelectedRow();

			if (selectedRow >= 0) {
				try {
					int id = (int) table.getValueAt(selectedRow, 0);
					Servico servicoTmp = this.model.buscarServicoPorId(id);

					if (servicoTmp != null && Boolean.TRUE.equals(servicoTmp.getAceito())) {
						JOptionPane.showMessageDialog(view, "Impossível deletar trabalhos que já foram aceitos.", "Erro", JOptionPane.ERROR_MESSAGE);
					} else {
						int option = JOptionPane.showConfirmDialog(view, "Tem certeza que deseja deletar este trabalho?", "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
						if (option == JOptionPane.YES_OPTION) {
							if (this.model.deletarServico(id)) {
								modelTable.removeRow(selectedRow);
								JOptionPane.showMessageDialog(view, "Serviço deletado com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
							} else {
								JOptionPane.showMessageDialog(view, "Falha ao deletar o serviço.", "Erro", JOptionPane.ERROR_MESSAGE);
							}
						}
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(view, "Erro ao tentar deletar o serviço.", "Erro", JOptionPane.ERROR_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(view, "Selecione uma linha para excluir.", "Aviso", JOptionPane.WARNING_MESSAGE);
			}
		});

		// Configura o listener do botão "editar" na view
		// Botão "editar": lê conteúdo da JTable (possivelmente editada inline) e aplica no DAO
		this.view.editar(e -> {
			// Obtém a referência da tabela de serviços da view
			JTable table = view.getTableServicos();
			// Garante que uma edição em andamento seja finalizada
			// Verifica se alguma célula está sendo editada neste momento
			if (table.isEditing()) {
				// Para a edição da célula para salvar o valor digitado
				table.getCellEditor().stopCellEditing();
			}
			// Obtém o modelo de dados da tabela
			TableModel modelTable = table.getModel();
			// Obtém o número total de linhas na tabela
			int rowCount = modelTable.getRowCount();
			// Obtém o número total de colunas na tabela
			int colCount = modelTable.getColumnCount();

			// Tenta localizar a coluna que contém o ID por nome; se não encontrar, assume coluna 0
			// Inicializa a variável que armazenará o índice da coluna de ID (-1 = não encontrado)
			int idCol = -1;
			// Percorre todas as colunas da tabela
			for (int c = 0; c < colCount; c++) {
				// Obtém o nome da coluna atual
				String colName = modelTable.getColumnName(c);
				// Verifica se o nome da coluna não é nulo E contém a palavra "id" (case-insensitive)
				if (colName != null && colName.toLowerCase().contains("id")) {
					// Armazena o índice desta coluna como a coluna de ID
					idCol = c;
					// Interrompe o loop pois já encontrou a coluna
					break;
				}
			}
			// Se não encontrou coluna com "id" no nome, assume que é a primeira coluna (índice 0)
			if (idCol == -1) {
				idCol = 0;
			}

			// Cria uma nova instância do DAO para realizar operações de atualização
			ServicoDAO dao = new ServicoDAO();
			// Contador de registros atualizados com sucesso
			int updated = 0;
			// Contador de registros que falharam na atualização
			int failed = 0;

			// Percorre todas as linhas da tabela para processar as edições
			for (int r = 0; r < rowCount; r++) {
				try {
					// Obtém o valor do ID da linha atual usando a coluna identificada anteriormente
					Object idObj = modelTable.getValueAt(r, idCol);
					// Se o ID for nulo, incrementa contador de falhas e pula para próxima linha
					if (idObj == null) {
						failed++;
						continue;
					}
					// Variável que armazenará o ID convertido para inteiro
					int idServico;
					// Verifica se o objeto ID já é um número (Integer, Long, etc.)
					if (idObj instanceof Number) {
						// Converte diretamente para inteiro
						idServico = ((Number) idObj).intValue();
					} else {
						// Se não for Number, converte a representação String para inteiro (removendo espaços)
						idServico = Integer.parseInt(idObj.toString().trim());
					}
					// Busca o serviço no banco de dados para verificar seu estado atual
					model.Servico servTmp = this.model.buscarServicoPorId(idServico);
					// Verifica se o serviço existe E se ele já foi aceito
					if (servTmp != null && Boolean.TRUE.equals(servTmp.getAceito())) {
						// Serviços aceitos não podem ser modificados - exibe erro e incrementa contador de falhas
						JOptionPane.showMessageDialog(null, "Imposivel modificar trabalhos aceitos", "Erro",
							JOptionPane.ERROR_MESSAGE);
						failed++;
					} else {
						// Lê colunas por nome (mais robusto do que índices fixos)
						// Inicializa variáveis que armazenarão os dados extraídos da tabela
						String nome = null;
						Double valor = null;
						String modalidade = null;
						String descricao = null;
						Boolean aceito = null;

						// Percorre todas as colunas da linha atual para extrair os dados
						for (int c = 0; c < colCount; c++) {
							// Obtém o nome da coluna e normaliza: converte para minúsculas e remove acentuação
							String colName = modelTable.getColumnName(c).toLowerCase().replace("ç", "c").replace("ã", "a")
								.replace("á", "a").replace("é", "e").replace("í", "i").replace("ó", "o")
								.replace("ú", "u").replace(" ", "");
							// Obtém o valor da célula atual
							Object cell = modelTable.getValueAt(r, c);
							// Identifica qual campo é baseado no nome da coluna e armazena o valor
							if (colName.contains("nome")) {
								// Se a coluna contém "nome", armazena como nome do serviço
								nome = cell != null ? cell.toString() : null;
							} else if (colName.contains("valor")) {
								// Se a coluna contém "valor", tenta converter para Double
								if (cell != null && !cell.toString().isEmpty()) {
									try {
										// Se já for um Number, converte diretamente
										if (cell instanceof Number) {
											valor = ((Number) cell).doubleValue();
										} else {
											// Se for String, substitui vírgula por ponto e converte
											valor = Double.parseDouble(cell.toString().replace(",", "."));
										}
									} catch (Exception ex) {
										// Em caso de erro na conversão, mantém valor como null
										valor = null;
									}
								}
							} else if (colName.contains("modalidade")) {
								// Se a coluna contém "modalidade", armazena como string
								modalidade = cell != null ? cell.toString() : null;
							} else if (colName.contains("descricao")) {
								// Se a coluna contém "descricao", armazena como string
								descricao = cell != null ? cell.toString() : null;
							} else if (colName.contains("aceit") || colName.contains("aceito")) {
								// Se a coluna contém "aceito" ou "aceit", processa como booleano
								if (cell instanceof Boolean)
									// Se já for Boolean, usa diretamente
									aceito = (Boolean) cell;
								else if (cell != null) {
									// Se for String, verifica se o texto representa verdadeiro
									String s = cell.toString().toLowerCase();
									aceito = s.equals("true") || s.equals("1") || s.equals("sim") || s.equals("yes");
								}
							}
						}

						// Cria um novo objeto Servico com os dados extraídos da tabela
						// Se aceito for null, assume false como padrão
						Servico s = new Servico(nome, valor, modalidade, descricao, aceito != null ? aceito : false, null);
						// Tenta atualizar o serviço no banco de dados usando o DAO
						boolean ok = dao.atualizarServicoPorId(idServico, s);
						// Se a atualização foi bem-sucedida, incrementa contador de sucesso
						if (ok)
							updated++;
						else
							// Se falhou, incrementa contador de falhas
							failed++;
					}
				} catch (Exception ex) {
					// Em caso de qualquer exceção durante o processamento da linha:
					// Imprime o stack trace no console para debug
					ex.printStackTrace();
					// Incrementa o contador de falhas
					failed++;
				}
			}

			// Exibe um diálogo informativo mostrando quantos registros foram atualizados e quantos falharam
			JOptionPane.showMessageDialog(null, "Atualizados: " + updated + "\nFalhas: " + failed,
					"Resultado da atualização", JOptionPane.INFORMATION_MESSAGE);
			// Recarrega a tabela a partir da fonte de dados
			// Atualiza a tabela buscando os dados mais recentes do banco de dados
			atualizarTabelaServicosDoUsuario();

		});

	} // Fim do construtor ListaServicosController

	/**
	 * Método estático que atualiza a tabela se existir uma instância ativa do controller.
	 * <p>
	 * Permite que outras partes do sistema atualizem a tabela de serviços sem precisar
	 * de uma referência direta ao controller. Útil para callbacks e eventos externos.
	 * </p>
	 */
	public static void atualizarTabelaSeExistir() {
		// Verifica se existe uma instância ativa do controller
		if (instanciaAtual != null) {
			// Se existir, chama o método de instância para atualizar a tabela
			instanciaAtual.atualizarTabelaServicosDoUsuario();
		}
	}

	/**
	 * Atualiza a tabela de serviços carregando os dados do usuário logado.
	 * <p>
	 * Busca todos os serviços associados ao usuário atual no banco de dados
	 * e atualiza a visualização da tabela na interface gráfica.
	 * </p>
	 */
	public void atualizarTabelaServicosDoUsuario() {
		// Verifica se existe um usuário logado no sistema
		if (navegador.getCurrentUser() != null) {
			// Cria uma nova instância do DAO para buscar os serviços
			ServicoDAO dao = new ServicoDAO();
			// Busca todos os serviços do usuário logado no banco de dados
			ArrayList<Servico> lista = dao.buscarTodosServicosPorUsuario(navegador.getCurrentUser());
			// Evita NullPointerException caso o DAO retorne null - cria lista vazia
			if (lista == null) lista = new ArrayList<>();
			// Atualiza a tabela na view com a lista de serviços obtida
			this.view.atualizarTable(lista);
		}
	}

} // Fim da classe ListaServicosController