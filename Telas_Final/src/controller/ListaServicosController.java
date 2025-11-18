package controller;

import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import model.Servico;
import model.ServicoDAO;
import model.Status;
import view.Mensagem;
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
	private final TelaFactory telaFactory;
	private static ListaServicosController instanciaAtual;

	public ListaServicosController(TelaListaServicos view, ServicoDAO model, Navegador navegador, TelaFactory telaFactory) {
		this.view = view;
		this.model = model;
		this.navegador = navegador;
		this.telaFactory = telaFactory;
		instanciaAtual = this;

		this.view.cadastrar(e -> {
			navegador.navegarPara("CADASTRO_CONTRATANTE");
		});

		Mensagem M = new Mensagem();
		this.view.visualizar(e -> {
			int selectedRow = view.getTableServicos().getSelectedRow();
			if (selectedRow >= 0) {
				try {
					int id = (int) view.getTableServicos().getValueAt(selectedRow, 0);
					Servico s = this.model.buscarServicoPorId(id);
					if (s == null) {
						M.Erro("Serviço não encontrado","Erro");
						return;
					}
					
					if(s.getStatus() == Status.CADASTRADO) {
						telaFactory.criarVisServicoCnte(s);
						navegador.navegarPara(telaFactory.criarVisServicoCnte(s));
					}else if(s.getStatus() == Status.ACEITO) {
						telaFactory.criarVisServicoCnteAceito(s);
						navegador.navegarPara(telaFactory.criarVisServicoCnteAceito(s));
					}else {
						telaFactory.criarVisServicoCnteFinalizado(s);
						navegador.navegarPara(telaFactory.criarVisServicoCnteFinalizado(s));
					} 
					

				} catch (Exception ex) {
					M.Erro("Erro ao carregar Serviço", "Erro");
				}
			} else {
				M.Aviso("Nenhum serviço selecionado.", "Aviso");
			}
		});

		this.view.deletar(e -> {
			JTable table = view.getTableServicos();
			DefaultTableModel modelTable = (DefaultTableModel) table.getModel();
			int selectedRow = table.getSelectedRow();

			if (selectedRow >= 0) {
				try {
					int id = (int) table.getValueAt(selectedRow, 0);
					Servico servicoTmp = this.model.buscarServicoPorId(id);

					if (servicoTmp != null && servicoTmp.getStatus() == Status.ACEITO) {
						M.Erro("Impossível deletar trabalhos que já foram aceitos.", "Erro");
					} else {
						int option = JOptionPane.showConfirmDialog(view, "Tem certeza que deseja deletar este trabalho?", "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
						if (option == JOptionPane.YES_OPTION) {
							if (this.model.deletarServico(id)) {
								modelTable.removeRow(selectedRow);
								M.Sucesso("Serviço deletado com sucesso.", "Sucesso");
							} else {
                              M.Erro("Falha ao deletar o serviço.", "Erro");
							}
						}
					}
				} catch (Exception ex) {
					M.Erro("Erro ao tentar deletar o serviço.", "Erro");
				}
			} else {
				M.Aviso("Selecione uma linha para excluir.", "Aviso");
			}
		});

		this.view.editar(e -> {
			JTable table = view.getTableServicos();
			if (table.isEditing()) {
				table.getCellEditor().stopCellEditing();
			}
			TableModel modelTable = table.getModel();
			int rowCount = modelTable.getRowCount();
			int colCount = modelTable.getColumnCount();

			int idCol = -1;
			for (int c = 0; c < colCount; c++) {
				String colName = modelTable.getColumnName(c);
				if (colName != null && colName.toLowerCase().contains("id")) {
					idCol = c;
					break;
				}
			}
			if (idCol == -1) {
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
					if (idObj instanceof Number) {
						idServico = ((Number) idObj).intValue();
					} else {
						idServico = Integer.parseInt(idObj.toString().trim());
					}
					model.Servico servTmp = this.model.buscarServicoPorId(idServico);
					if (servTmp != null && servTmp.getStatus() == Status.ACEITO) {
					M.Erro("Imposivel modificar trabalhos aceitos", "Erro");
						failed++;
					} else {
						String nome = null;
						Double valor = null;
						String modalidade = null;
						String descricao = null;
						Status status = null;

						for (int c = 0; c < colCount; c++) {
							String colName = modelTable.getColumnName(c).toLowerCase().replace("ç", "c").replace("ã", "a")
								.replace("á", "a").replace("é", "e").replace("í", "i").replace("ó", "o")
								.replace("ú", "u").replace(" ", "").replace("?", "");
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
							} else if (colName.contains("status") || colName.contains("aceit")) {
								if (cell != null) {
									try {
										String statusStr = cell.toString().toUpperCase().trim();
										status = Status.valueOf(statusStr);
									} catch (Exception ex) {
										status = null;
									}
								}
							}
						}

						Servico s = new Servico(nome, valor, modalidade, descricao, status, null);
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

			JOptionPane.showMessageDialog(null, "Atualizados: " + updated + "\nFalhas: " + failed,
					"Resultado da atualização", JOptionPane.INFORMATION_MESSAGE);
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
			if (lista == null) lista = new ArrayList<>();
			this.view.atualizarTable(lista);
		}
	}

}