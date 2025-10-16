package controller;

import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import model.Servico;
import model.ServicoDAO;
import view.TelaListaServicos;
import view.VisServicoAndamento;
import view.VisServico;
import view.VisServicoCnte;
import view.VisServicoCnteAceito;
import controller.VisServicoController;
import controller.VisServicoCnteController;
import controller.VisServicoCnteAceitoController;

public class ListaServicosController {
	private final TelaListaServicos view;
	private final ServicoDAO model;
	private final Navegador navegador;
	private static ListaServicosController instanciaAtual;

	public ListaServicosController(TelaListaServicos view, ServicoDAO model, Navegador navegador) {
		this.view = view;
		this.model = model;
		this.navegador = navegador;
		instanciaAtual = this;

		this.view.cadastrar(e -> {
			navegador.navegarPara("CADASTRO_CONTRATANTE");
		});

		this.view.visualizar(e -> {
			JTable table = view.getTableServicos();
			DefaultTableModel modelTable = (DefaultTableModel) table.getModel();

			int selectedRow = table.getSelectedRow();
			if (selectedRow >= 0) {
				// Pega o ID da linha selecionada (coluna 0, que é a coluna oculta)
				Object idValue = table.getValueAt(selectedRow, 0);
				if (idValue != null) {
					int id = (int) idValue;
					
					Servico s = this.model.buscarServicoPorId(id);
					if (s == null) {
						JOptionPane.showMessageDialog(null, "Serviço não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
						return;
					}
					// Se não aceito -> VisServico
					if (Boolean.FALSE.equals(s.getAceito())) {
						VisServicoCnte vis = new VisServicoCnte(s);
						VisServicoCnteController visController = new VisServicoCnteController(vis, model, navegador, s);
						String panelName = "VIS_Servico_Cnte" + id;
						navegador.adicionarPainel(panelName, vis);
						navegador.navegarPara(panelName);
					} else {
						// Aceito -> VisServicoCnteAceito
						VisServicoCnteAceito visAceito = new VisServicoCnteAceito(s);
						VisServicoCnteAceitoController aceitoController = new VisServicoCnteAceitoController(visAceito,
								model, navegador, s);
						String panelName = "VIS_Servico_Cnte_Aceito_" + id;
						navegador.adicionarPainel(panelName, visAceito);
						navegador.navegarPara(panelName);
					}
					
				} else {
					JOptionPane.showMessageDialog(null, "Nenhum servico selecionado", "Erro",
							JOptionPane.ERROR_MESSAGE);
				}

			}

		});

		this.view.deletar(e -> {
			JTable table = view.getTableServicos();
			DefaultTableModel modelTable = (DefaultTableModel) table.getModel();

			int selectedRow = table.getSelectedRow();
			if (selectedRow >= 0) {
				// Pega o ID da linha selecionada (coluna 0, que é a coluna oculta)
				Object idValue = table.getValueAt(selectedRow, 0);
				if (idValue != null) {
					int id = (int) idValue;

					if (this.model.buscarServicoPorId(id).getAceito().equals(true)) {
						JOptionPane.showMessageDialog(null, "Imposivel deletar trabalhos aceitos", "Erro",
							JOptionPane.ERROR_MESSAGE);
					} else {
						// Confirmar exclusão
						int option = JOptionPane.showConfirmDialog(null,
								"Tem certeza que deseja deletar este trabalho?", "Confirmar Exclusão",
								JOptionPane.YES_NO_OPTION);

						if (option == JOptionPane.YES_OPTION) {
							// Deletar a linha do banco de dados e da JTable
							this.model.deletarServico(id);
							modelTable.removeRow(selectedRow); // Remove da tabela visual
						}
					}
				}
			} else {
				JOptionPane.showMessageDialog(null, "Selecione uma linha para excluir.");
			}
		});

		this.view.editar(e -> {
			JTable table = view.getTableServicos();
			// Força o JTable a salvar a edição ativa antes de ler os valores
			if (table.isEditing()) {
				table.getCellEditor().stopCellEditing();
			}
			TableModel modelTable = table.getModel();
			int rowCount = modelTable.getRowCount();
			int colCount = modelTable.getColumnCount();

			// encontrar indice da coluna id (procura por "id" no nome da coluna)
			int idCol = -1;
			for (int c = 0; c < colCount; c++) {
				String colName = modelTable.getColumnName(c);
				if (colName != null && colName.toLowerCase().contains("id")) {
					idCol = c;
					break;
				}
			}
			if (idCol == -1) {
				// se não encontrou id, tenta usar primeira coluna como fallback
				idCol = 0;
			}

			ServicoDAO dao = new ServicoDAO();
			int updated = 0;
			int failed = 0;

			for (int r = 0; r < rowCount; r++) {
				try {
					Object idObj = modelTable.getValueAt(r, idCol);
					if (idObj == null) {
						failed++;
						continue;
					}
					int idServico;
					// aceitar Integer, Long, String numerica
					if (idObj instanceof Number) {
						idServico = ((Number) idObj).intValue();
					} else {
						idServico = Integer.parseInt(idObj.toString().trim());
					}
					if(this.model.buscarServicoPorId(idServico).getAceito().equals(true)) {
						JOptionPane.showMessageDialog(null, "Imposivel modificar trabalhos aceitos", "Erro",
							JOptionPane.ERROR_MESSAGE);
						failed++;
					}else {
						// Agora ler as colunas conhecidas:
						// ajuste os índices abaixo conforme sua ordem de colunas.
						// Aqui assumimos (exemplo): [id, Nome_servico, Valor, Modalidade, Descricao,
						// Aceito]
						// Se a sua tabela tiver ordem diferente, altere os índices.
						String nome = null;
						Double valor = null;
						String modalidade = null;
						String descricao = null;
						Boolean aceito = null;

						// tenta encontrar colunas por nome ao invés de índice fixo (mais robusto)
						for (int c = 0; c < colCount; c++) {
							String colName = modelTable.getColumnName(c).toLowerCase().replace("ç", "c").replace("ã", "a")
								.replace("á", "a").replace("é", "e").replace("í", "i").replace("ó", "o")
								.replace("ú", "u").replace(" ", "");
							Object cell = modelTable.getValueAt(r, c);
							if (colName.contains("nome")) {
								nome = cell != null ? cell.toString() : null;
							} else if (colName.contains("valor")) {
								if (cell != null && !cell.toString().isEmpty()) {
									try {
										if (cell instanceof Number) {
											valor = ((Number) cell).doubleValue();
										} else {
											valor = Double.parseDouble(cell.toString().replace(",", "."));
										}
									} catch (Exception ex) {
										valor = null;
									}
								}
							} else if (colName.contains("modalidade")) {
								modalidade = cell != null ? cell.toString() : null;
							} else if (colName.contains("descricao")) {
								descricao = cell != null ? cell.toString() : null;
							} else if (colName.contains("aceit") || colName.contains("aceito")) {
								if (cell instanceof Boolean)
									aceito = (Boolean) cell;
								else if (cell != null) {
									String s = cell.toString().toLowerCase();
									aceito = s.equals("true") || s.equals("1") || s.equals("sim") || s.equals("yes");
								}
							}
						}

						// Se algum campo obrigatório for necessário, aqui você pode validar.
						Servico s = new Servico(nome, valor, modalidade, descricao, aceito != null ? aceito : false, null);
						// o construtor do seu Servico pode exigir um Usuario contratante — se assim
						// for, adapte:
						// por enquanto passamos null para contratante. Se você tiver contratanteId em
						// alguma coluna,
						// busque e atribua.

						boolean ok = dao.atualizarServicoPorId(idServico, s);
						if (ok)
							updated++;
						else
							failed++;
					}
				} catch (Exception ex) {
					ex.printStackTrace();
					failed++;
				}
			}

			javax.swing.JOptionPane.showMessageDialog(null, "Atualizados: " + updated + "\nFalhas: " + failed,
					"Resultado da atualização", javax.swing.JOptionPane.INFORMATION_MESSAGE);
			// opcional: recarregar tabela da fonte de dados
			atualizarTabelaServicosDoUsuario();

		});

	}

	public static void atualizarTabelaSeExistir() {
		if (instanciaAtual != null) {
			instanciaAtual.atualizarTabelaServicosDoUsuario();
		}
	}

	public void atualizarTabelaServicosDoUsuario() {
		if (navegador.getCurrentUser() != null) {
			ServicoDAO dao = new ServicoDAO();
			ArrayList<Servico> lista = dao.buscarTodosServicosPorUsuario(navegador.getCurrentUser());
			this.view.atualizarTable(lista);
		}
	}

}