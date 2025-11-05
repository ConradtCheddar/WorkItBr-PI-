// Define o pacote onde esta classe está localizada
package controller;

// Importa Cursor para alterar o cursor do mouse
import java.awt.Cursor;
// Importa MouseAdapter para criar listeners de eventos de mouse personalizados
import java.awt.event.MouseAdapter;
// Importa MouseEvent que contém informações sobre eventos de mouse
import java.awt.event.MouseEvent;
// Importa MouseListener, interface para listener de eventos de mouse
import java.awt.event.MouseListener;

// Importa JOptionPane para exibir diálogos de mensagem ao usuário
import javax.swing.JOptionPane;
// Importa SwingUtilities para operações na thread de eventos do Swing
import javax.swing.SwingUtilities;

// Importa UsuarioDAO, responsável por operações de banco de dados relacionadas a usuários
import model.UsuarioDAO;
// Importa a view da barra superior WorkIt
import view.wbBarra;

/**
 * Controller responsável pela barra superior (wbBarra) que contém botões
 * de menu e retorno.
 * <p>
 * Registra listeners para os elementos da barra, atualiza estados iniciais
 * (habilita/desabilita botões conforme o histórico e usuário logado) e
 * sincroniza a UI quando o histórico muda.
 * </p>
 */
public class WBController {
    // Referência à camada de visualização (interface gráfica) da barra superior
    private final wbBarra view;
    // Referência ao DAO que gerencia operações de banco de dados para usuários
    private final UsuarioDAO model;
    // Referência ao navegador que controla a navegação entre telas
    private final Navegador navegador;
    // Referência ao controller do popup (não utilizado neste controller)
    private final PopupController popup;
    // Referência ao controller do menu popup
    private final PopupMenuController popup2;

    /**
     * Construtor que inicializa o controller e configura todos os listeners.
     * 
     * @param view referência à barra superior
     * @param model referência ao DAO de usuários
     * @param navegador objeto que controla a navegação
     * @param popup controller do popup
     * @param popup2 controller do menu popup
     */
    public WBController(wbBarra view, UsuarioDAO model, Navegador navegador, PopupController popup, PopupMenuController popup2) {
        // Atribui a referência da view recebida ao atributo da classe
        this.view = view;
        // Atribui a referência do model (DAO) recebido ao atributo da classe
        this.model = model;
        // Atribui a referência do navegador recebido ao atributo da classe
        this.navegador = navegador;
        // Atribui a referência do popup controller recebido ao atributo da classe
        this.popup = popup;
        // Atribui a referência do popup menu controller recebido ao atributo da classe
        this.popup2 = popup2;

        // Ajusta estado inicial da UI com base no histórico e no usuário atual
        // Habilita/desabilita o botão voltar baseado na existência de histórico
        this.view.setBackEnabled(navegador.hasHistory());
        // Habilita/desabilita o botão de menu baseado na existência de usuário logado
        this.view.setMenuEnabled(navegador.getCurrentUser() != null);
        
        // Registra listener que atualiza a UI quando o histórico muda (executa no EDT)
        // Define um listener que será executado sempre que o histórico de navegação mudar
        this.navegador.setOnHistoryChange(() -> SwingUtilities.invokeLater(() -> {
            // Executa na thread de eventos do Swing (EDT) para segurança thread-safe
            // Atualiza o estado do botão voltar
            this.view.setBackEnabled(navegador.hasHistory());
            // Atualiza o estado do botão de menu
            this.view.setMenuEnabled(navegador.getCurrentUser() != null);
        }));
        
        // Remove listeners antigos do ícone de menu para evitar duplicação
        // Percorre todos os listeners de mouse existentes no ícone da barra (menu)
        for (MouseListener ml : this.view.getLblBarra().getMouseListeners()) {
            // Remove cada listener para evitar duplicação ao recriar o controller
            this.view.getLblBarra().removeMouseListener(ml);
        }
        
        // Ao clicar no ícone de menu (três linhas) abre o DrawerMenu apenas se houver usuário logado
        // Adiciona um novo listener de mouse ao ícone de menu
        this.view.getLblBarra().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Verifica se não há usuário logado
                if (navegador.getCurrentUser() == null) {
                    // Exibe aviso informando que é necessário estar logado
                    JOptionPane.showMessageDialog(
                        view,
                        "Você precisa estar logado para acessar o menu.",
                        "Acesso Negado",
                        JOptionPane.WARNING_MESSAGE
                    );
                    // Interrompe a execução do método
                    return;
                }
                // Alterna o menu lateral visível/escondido
                // Abre ou fecha o drawer menu (menu lateral)
                popup2.getView().toggleMenu();
            }
        });

        // Remove listeners antigos da seta de voltar (para evitar múltiplas inscrições)
        // Percorre todos os listeners de mouse do ícone de voltar
        for (MouseListener ml : this.view.getLblVoltar().getMouseListeners()) {
            // Verifica se o listener foi criado por esta classe
            if (ml.getClass().getName().contains("WBController")) {
                // Remove o listener para evitar duplicação
                this.view.getLblVoltar().removeMouseListener(ml);
            }
        }
        
        // Configura comportamento do botão "voltar"
        // Adiciona um novo listener de mouse ao ícone de voltar
        this.view.getLblVoltar().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Se não houver histórico, informa ao usuário
                // Verifica se não existe histórico de navegação
                if (!navegador.hasHistory()) {
                    // Exibe mensagem informativa
                    JOptionPane.showMessageDialog(
                        view,
                        "Não há tela anterior para retornar.",
                        "Aviso",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                    // Interrompe a execução do método
                    return;
                }
                // Navega para a tela anterior registrada no Navegador
                // Chama o método voltar do navegador para retornar à tela anterior
                navegador.voltar();
            }
            
            @Override
            public void mouseEntered(MouseEvent e) {
                // Quando o mouse entra na área do botão voltar
                // Verifica se existe histórico de navegação
                if (navegador.hasHistory()) {
                    // Muda o cursor para "mão" indicando que é clicável
                    view.getLblVoltar().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                }
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                // Quando o mouse sai da área do botão voltar
                // Restaura o cursor padrão
                view.getLblVoltar().setCursor(Cursor.getDefaultCursor());
            }
        });
    } // Fim do construtor WBController
} // Fim da classe WBController