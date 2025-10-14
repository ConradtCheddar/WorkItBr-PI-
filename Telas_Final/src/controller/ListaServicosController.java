package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import model.Servico;
import model.ServicoDAO;
import view.TelaListaServicos;

public class ListaServicosController {
	private final TelaListaServicos view;
	private final ServicoDAO model;
	private final Navegador navegador;
	private static ListaServicosController instanciaAtual;
	//private final Servico s;

	public ListaServicosController(TelaListaServicos view, ServicoDAO model, Navegador navegador){
		this.view = view;
		this.model = model;
		this.navegador = navegador;
		instanciaAtual = this;
		ligarEditarSalvar();
		//this.s = s;
		}
		
		 // Listener para alterar dados
//        this.view.editar(e -> {
//        	this.view.getItems();
//        	int rowCount = view.getTableModel().getRowCount();
//            int colCount = view.getTableModel().getColumnCount();
//            
//            Object table = view.getTableData();
//        	
//        	for (int row = 0; row < rowCount; row++) {
//                for (int col = 0; col < colCount; col++) {
//                   if(col == 0) {
//                	   s.setNome_Servico(((DefaultTableModel) table).getValueAt(row,col).toString());
//                	   System.out.println(s);
//                   }
//                   if(col == 1) {
//                	   s.setValor(Double.parseDouble(((DefaultTableModel) table).getValueAt(row,col).toString()));
//                   }
//                   if(col == 2) {
//                	   s.setModalidade(((DefaultTableModel) table).getValueAt(row,col).toString());
//                   }
//                }
//            }
//        });
//	}
	

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
	
	public void ligarEditarSalvar() {
	    // se sua view já tem um metodo editar(ActionListener) use esse
	    view.getBtnEditar().addActionListener(new ActionListener() {
	    	
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	
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

	                    // Agora ler as colunas conhecidas:
	                    // ajuste os índices abaixo conforme sua ordem de colunas.
	                    // Aqui assumimos (exemplo): [id, Nome_servico, Valor, Modalidade, Descricao, Aceito]
	                    // Se a sua tabela tiver ordem diferente, altere os índices.
	                    String nome = null;
	                    Double valor = null;
	                    String modalidade = null;
	                    String descricao = null;
	                    Boolean aceito = null;

	                    // tenta encontrar colunas por nome ao invés de índice fixo (mais robusto)
	                    for (int c = 0; c < colCount; c++) {
	                        String colName = modelTable.getColumnName(c).toLowerCase().replace("ç", "c").replace("ã", "a").replace("á", "a").replace("é", "e").replace("í", "i").replace("ó", "o").replace("ú", "u").replace(" ", "");
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
	                            if (cell instanceof Boolean) aceito = (Boolean) cell;
	                            else if (cell != null) {
	                                String s = cell.toString().toLowerCase();
	                                aceito = s.equals("true") || s.equals("1") || s.equals("sim") || s.equals("yes");
	                            }
	                        }
	                    }

	                    // Se algum campo obrigatório for necessário, aqui você pode validar.
	                    Servico s = new Servico(nome, valor, modalidade, descricao, aceito != null ? aceito : false, null);
	                    // o construtor do seu Servico pode exigir um Usuario contratante — se assim for, adapte:
	                    // por enquanto passamos null para contratante. Se você tiver contratanteId em alguma coluna,
	                    // busque e atribua.

	                    boolean ok = dao.atualizarServicoPorId(idServico, s);
	                    if (ok) updated++; else failed++;
	                } catch (Exception ex) {
	                    ex.printStackTrace();
	                    failed++;
	                }
	            }

	            javax.swing.JOptionPane.showMessageDialog(null,
	                    "Atualizados: " + updated + "\nFalhas: " + failed,
	                    "Resultado da atualização",
	                    javax.swing.JOptionPane.INFORMATION_MESSAGE);
	            // opcional: recarregar tabela da fonte de dados
	            atualizarTabelaServicosDoUsuario();
	        }
	    });
	
	
}}