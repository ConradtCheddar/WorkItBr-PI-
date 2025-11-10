// Pacote (NOME SUGERIDO: renomear para algo neutro como "fileviewer" ou "visualizador")
// ATENÇÃO: o nome atual é ofensivo e deve ser alterado.
package fileViewer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

// Imports adicionais para prevenção de cópia / detecção de screenshot e threads de vigilância
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import java.awt.event.MouseMotionAdapter;
import javax.swing.text.DefaultCaret;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import javax.swing.Timer;
import java.awt.AWTEvent;
import java.awt.event.AWTEventListener;
import java.awt.KeyboardFocusManager;
import java.awt.KeyEventDispatcher;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.event.WindowStateListener;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.StringSelection;

/**
 * FileViewer
 *
 * Aplicação Swing simples que exibe arquivos de texto (ex.: código fonte) em um RSyntaxTextArea
 * com várias camadas de proteção para evitar seleção, cópia e tentativa de captura de tela.
 *
 * Observações de design:
 *  - Há múltiplas "layers" (camadas) de defesa: KeyEventDispatcher, AWTEventListener, listeners de janela,
 *    watchdog de clipboard, timers, e um overlay preto (glass pane) que é mostrado quando a proteção está ativa.
 *  - O comportamento é agressivo: limpa a área de transferência muitas vezes e despacha painting imediato.
 *  - Algumas técnicas não são garantidas em todos os SOs — por exemplo, é impossível garantir 100% que captura
 *    de tela/hardware será bloqueada (o SO pode fornecer APIs especiais ou o usuário pode usar câmera física).
 */
public class FileViewer {
    // Variáveis de instância principais
    private Robot robot;                          // Robot usado para simular/reagir a teclas (tentar anular PrintScreen)
    private Timer clipboardMonitor;               // Timer Swing para checagem periódica do clipboard
    private JPanel protectionOverlay;             // Painel que serve como overlay preto com mensagem "CAPTURA BLOQUEADA"
    private volatile boolean isProtectionActive = false; // flag indicando se a proteção está ativa
    private Thread clipboardWatchdog;             // thread dedicada à verificação rápida do clipboard (imagem)
    private volatile boolean running = true;      // flag para encerrar threads no shutdown
    private JFrame mainFrame;                     // frame principal da aplicação
    private NonSelectableRSyntaxTextArea mainTextArea; // área de texto customizada (não selecionável)
    private Thread focusMonitor;                  // thread que monitora foco/atividade da janela

    /**
     * Inner class NonSelectableRSyntaxTextArea
     *
     * Subclasse de RSyntaxTextArea que sobrescreve métodos relacionados à seleção e cópia
     * para impedir que texto seja selecionado ou copiado via API do componente.
     *
     * Nota: Isso torna a seleção pelo componente praticamente impossível, mas não impede
     * que usuários capturem a tela por métodos externos.
     */
    private static class NonSelectableRSyntaxTextArea extends RSyntaxTextArea {
        public NonSelectableRSyntaxTextArea() {
            super();
        }
        @Override
        public void select(int selectionStart, int selectionEnd) {
            // Ignora tentativas de seleção programática
        }
        @Override
        public void selectAll() {
            // Ignora selectAll
        }
        @Override
        public String getSelectedText() {
            return null; // sempre retorna null, fingindo que não há seleção
        }
        @Override
        public void copy() {
            // Ignora tentativas de cópia via componente
        }
        @Override
        public void cut() {
            // Ignora cortes (cut)
        }
        @Override
        public void replaceSelection(String content) {
            // Ignora tentativas de substituir seleção
        }
        @Override
        public int getSelectionStart() {
            // devolve a posição do caret em vez de início da seleção
            return getCaretPosition();
        }
        @Override
        public int getSelectionEnd() {
            // devolve a posição do caret em vez do fim da seleção
            return getCaretPosition();
        }
    }

    /**
     * main: inicializa a GUI na Event Dispatch Thread.
     * Usa SwingUtilities.invokeLater para garantir execução segura com Swing.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new FileViewer_comSeguranca().createAndShowGUI();
        });
    }

    /**
     * createAndShowGUI: monta toda a interface, configura listeners e inicia threads/timers
     *
     * Estrutura:
     *  - Cria JFrame e RSyntaxTextArea (não selecionável)
     *  - Cria glass pane (protectionOverlay)
     *  - Configura múltiplas camadas de defesa (event dispatchers, AWT listener, window listeners)
     *  - Inicializa watchdogs (clipboard Watchdog, Timer de clipboard, focus monitor thread)
     */
    private void createAndShowGUI() {
        mainFrame = new JFrame("Visualizador de Arquivos");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(800, 600);
        mainFrame.setLayout(new BorderLayout());

        // Inicializa Robot para tentar neutralizar PrintScreen (pode falhar dependendo do SO / permissões)
        try {
            robot = new Robot();
        } catch (AWTException e) {
            // Loga erro; aplicação continua sem Robot (algumas medidas de proteção não funcionarão)
            System.err.println("Não foi possível criar Robot para bloquear PrintScreen: " + e.getMessage());
        }

        // Cria o painel de overlay que irá cobrir a janela com preto e mensagem de bloqueio.
        // É usado como glass pane (painel sobreposto).
        protectionOverlay = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Desenha fundo preto cobrindo tudo
                g.setColor(Color.BLACK);
                g.fillRect(0, 0, getWidth(), getHeight());
                // Texto vermelho grande informando que a captura está bloqueada
                g.setColor(Color.RED);
                g.setFont(new Font("Arial", Font.BOLD, 48));
                String msg = "CAPTURA BLOQUEADA";
                int msgWidth = g.getFontMetrics().stringWidth(msg);
                g.drawString(msg, (getWidth() - msgWidth) / 2, getHeight() / 2);
            }
        };
        protectionOverlay.setVisible(false);
        protectionOverlay.setOpaque(true);

        // Inicia com proteção ativa — conteúdo será visível apenas quando a janela tiver foco
        isProtectionActive = true;

        // Cria e configura área de texto (RSyntaxTextArea) com várias proteções contra seleção/copiar
        mainTextArea = new NonSelectableRSyntaxTextArea();
        mainTextArea.setEditable(false);  // impede edição pelo usuário
        mainTextArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA); // realce para Java
        // Define seleção invisível (se por algum motivo houver seleção, ela ficará invisível)
        mainTextArea.setSelectionColor(new Color(255, 255, 255, 0));
        mainTextArea.setSelectedTextColor(new Color(255, 255, 255, 0));
        mainTextArea.setCaretPosition(0); // posiciona caret no início

        // --- Medidas para prevenir seleção / cópia por teclado mouse/drag ---
        // Torna componente não focável — evita atalhos de teclado diretos, porém reduz acessibilidade
        mainTextArea.setFocusable(false);
        // Remove TransferHandler para bloquear operações via drag & drop / clipboard do Swing
        mainTextArea.setTransferHandler(null);
        // Remove menu de contexto (botão direito)
        mainTextArea.setComponentPopupMenu(null);
        // Remove highlighter para evitar destaque visual de seleção
        mainTextArea.setHighlighter(null);

        // Substitui caret por DefaultCaret customizado para evitar criação de seleção ao mover o caret
        mainTextArea.setCaret(new DefaultCaret() {
            @Override
            public void moveDot(int dot) {
                // Usa setDot para mover caret sem criar seleção — override evita seleção por movimento
                setDot(dot);
            }
            @Override
            public void setSelectionVisible(boolean v) {
                // Ignora tentativas de mostrar seleção
            }
        });

        // Remove bindings de teclas comuns de copiar/recortar/selecionar para os três contextos de InputMap
        KeyStroke[] keys = new KeyStroke[] {
            KeyStroke.getKeyStroke("control C"),
            KeyStroke.getKeyStroke("control X"),
            KeyStroke.getKeyStroke("control A"),
            KeyStroke.getKeyStroke("shift INSERT"),
            KeyStroke.getKeyStroke("ctrl INSERT")
        };
        int[] inputMapTypes = new int[] {
            JComponent.WHEN_FOCUSED,
            JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT,
            JComponent.WHEN_IN_FOCUSED_WINDOW
        };
        for (int type : inputMapTypes) {
            InputMap im2 = mainTextArea.getInputMap(type);
            if (im2 != null) {
                for (KeyStroke ks : keys) {
                    if (ks != null) im2.put(ks, "none"); // mapeia o keystroke para "none" (sem ação)
                }
            }
        }

        // Consome eventos do mouse que poderiam levar a seleção ou ao menu de contexto
        mainTextArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                e.consume();
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                e.consume();
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                e.consume();
            }
        });
        mainTextArea.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                e.consume();
            }
        });
        // --- fim das medidas de prevenção de seleção/cópia ---

        // Envolve a área de texto em um scroll pane (do RSyntax)
        RTextScrollPane scrollPane = new RTextScrollPane(mainTextArea);

        // Adiciona o scrollPane diretamente ao frame (centro)
        mainFrame.add(scrollPane, BorderLayout.CENTER);

        // Define o glass pane para o overlay de proteção
        mainFrame.setGlassPane(protectionOverlay);
        protectionOverlay.setOpaque(false); // inicialmente transparente (quando não ativo)

        // Monta menu "Arquivo" com item "Abrir"
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Arquivo");
        JMenuItem openItem = new JMenuItem("Abrir");

        // Ao clicar em "Abrir", chama método openFile para escolher arquivo
        openItem.addActionListener(e -> openFile(mainTextArea));

        fileMenu.add(openItem);
        menuBar.add(fileMenu);
        mainFrame.setJMenuBar(menuBar);

        // Deixa frame visível (após adicionar todos componentes)
        mainFrame.setVisible(true);

        // Outra proteção contra o menu de contexto por clique direito — consome o evento
        mainTextArea.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    e.consume();
                }
            }
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    e.consume();
                }
            }
        });

        // -------------------------
        // LAYERS de proteção (várias defesas sobrepostas)
        // -------------------------

        // LAYER 1: KeyEventDispatcher global - intercepta eventos de teclado antes dos componentes
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
            @Override
            public boolean dispatchKeyEvent(KeyEvent e) {
                // PRE-EMPTIVE PROTECTION: detecta tecla Windows / META e ativa proteção preventivamente
                if (e.getKeyCode() == KeyEvent.VK_WINDOWS || e.getKeyCode() == KeyEvent.VK_META) {
                    activateProtectionImmediately();
                    System.out.println("[ALERTA] Tecla Windows detectada - proteção ativada preventivamente!");
                    // Nota: não consumimos necessariamente para não atrapalhar o uso do SO
                }

                // Detecta Shift enquanto Windows/Meta está pressionada (tentativa de combo Windows+Shift+S)
                if (e.getKeyCode() == KeyEvent.VK_SHIFT && e.isMetaDown()) {
                    activateProtectionImmediately();
                    System.out.println("[ALERTA] Windows+Shift detectado - bloqueando captura!");
                }

                // Detecta PrintScreen — ativa proteção e consome evento
                if (e.getKeyCode() == KeyEvent.VK_PRINTSCREEN) {
                    activateProtectionImmediately();
                    e.consume();
                    clearClipboardAggressively();
                    System.out.println("[BLOQUEADO] PrintScreen detectado!");
                    return true; // retorna true para indicar que evento foi tratado
                }

                // Em KEY_PRESSED, tenta bloquear explicitamente Windows+Shift+S (captura recortada no Windows)
                if (e.getID() == KeyEvent.KEY_PRESSED) {
                    if (e.getKeyCode() == KeyEvent.VK_S && e.isShiftDown() && e.isMetaDown()) {
                        activateProtectionImmediately();
                        e.consume();
                        clearClipboardAggressively();
                        System.out.println("[BLOQUEADO] Ferramenta de captura detectada!");
                        return true;
                    }
                }
                // Não consome outros eventos - retorna false para continuar o fluxo normal
                return false;
            }
        });

        // LAYER 2: AWTEventListener — ouve eventos AWT (backup de escuta de teclas)
        Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {
            @Override
            public void eventDispatched(AWTEvent event) {
                if (event instanceof KeyEvent) {
                    KeyEvent keyEvent = (KeyEvent) event;
                    // Detecta PrintScreen independentemente do dispatcher acima
                    if (keyEvent.getKeyCode() == KeyEvent.VK_PRINTSCREEN) {
                        activateProtectionImmediately();
                        keyEvent.consume();
                        clearClipboardAggressively();
                        // Tenta forçar release da tecla com Robot (pode falhar)
                        if (robot != null) {
                            try {
                                robot.keyRelease(KeyEvent.VK_PRINTSCREEN);
                            } catch (Exception ex) {
                                // Ignora falhas
                            }
                        }
                        System.out.println("[BLOQUEADO] PrintScreen (AWTListener)!");
                    }
                }
            }
        }, AWTEvent.KEY_EVENT_MASK);

        // LAYER 3: Window state listener — detecta quando janela é minimizada (iconified)
        mainFrame.addWindowStateListener(new WindowStateListener() {
            @Override
            public void windowStateChanged(WindowEvent e) {
                if ((e.getNewState() & java.awt.Frame.ICONIFIED) != 0) {
                    // Ao minimizar, limpa clipboard (não mostra overlay neste caso)
                    clearClipboard();
                    System.out.println("[ALERTA] Janela minimizada - clipboard limpo");
                }
            }
        });

        // LAYER 4: Window focus monitoring — modo agressivo: conteúdo visível somente com foco
        mainFrame.addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                // Quando recupera o foco, desativa proteção (torna conteúdo visível)
                SwingUtilities.invokeLater(() -> {
                    deactivateProtection();
                    System.out.println("[INFO] Janela recuperou foco - conteúdo visível");
                });
            }

            @Override
            public void windowLostFocus(WindowEvent e) {
                // Ao perder foco, ativa proteção IMEDIATAMENTE, limpa clipboard
                activateProtectionImmediately();
                clearClipboard();
                System.out.println("[ALERTA] Janela perdeu foco - conteúdo escondido!");
            }
        });

        // LAYER 4b: Window listener para eventos de ativação / desativação
        mainFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowDeactivated(WindowEvent e) {
                // Janela ficou inativa — oculta conteúdo
                activateProtectionImmediately();
                System.out.println("[ALERTA] Janela desativada - conteúdo escondido!");
            }

            @Override
            public void windowActivated(WindowEvent e) {
                // Janela ativa — mostra conteúdo (via deactivateProtection que faz uma checagem adicional)
                SwingUtilities.invokeLater(() -> {
                    deactivateProtection();
                    System.out.println("[INFO] Janela ativada - conteúdo visível");
                });
            }
        });

        // LAYER 5: watchdog dedicado para clipboard (thread que checa a cada 50ms)
        startClipboardWatchdog();

        // LAYER 6: Timer Swing como backup para monitorar clipboard
        startClipboardMonitoring();

        // LAYER 7: focus monitor thread (verifica constantemente se janela está ativa/focused)
        startFocusMonitor();

        // Garante que o frame pode receber eventos e solicita foco
        mainFrame.setFocusable(true);
        mainFrame.requestFocus();

        // Adiciona shutdown hook para encerrar threads limpas ao finalizar a JVM
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            running = false;
            if (clipboardWatchdog != null) {
                clipboardWatchdog.interrupt();
            }
            if (focusMonitor != null) {
                focusMonitor.interrupt();
            }
        }));
    }

    /**
     * startFocusMonitor
     *
     * Thread que checa constantemente estado de foco/atividade do mainFrame. Se a janela não estiver ativa
     * ou focada, ativa proteção imediatamente. Usa sleep muito curto (10ms) para resposta instantânea.
     *
     * Observações:
     *  - Verificação com intervalo de 10ms é muito agressiva e consome CPU (dependendo da máquina).
     *    Em máquinas fracas pode causar overhead. Considere aumentar para 50-100ms.
     *  - A thread é iniciada como daemon e com prioridade máxima para reduzir latência, porém isso pode
     *    afetar o scheduler do SO.
     */
    private void startFocusMonitor() {
        focusMonitor = new Thread(() -> {
            while (running) {
                try {
                    if (mainFrame != null) {
                        boolean isActive = mainFrame.isActive();
                        boolean isFocused = mainFrame.isFocused();

                        if (!isActive || !isFocused) {
                            if (!isProtectionActive) {
                                activateProtectionImmediately();
                            }
                        }
                    }
                    Thread.sleep(10); // checagem ultra-frequente (10ms) -> alta responsividade, alto custo
                } catch (InterruptedException ex) {
                    break;
                } catch (Exception ex) {
                    // ignora erros e continua
                }
            }
        });
        focusMonitor.setDaemon(true);
        focusMonitor.setPriority(Thread.MAX_PRIORITY);
        focusMonitor.start();
    }

    /**
     * activateProtectionImmediately
     *
     * Ativa o overlay de proteção sem atraso, limpa clipboard agressivamente e força repaint imediato.
     * Agenda a desativação automática após 3 segundos (Timer).
     *
     * Observações:
     *  - Esse método força render via paintImmediately para reduzir janela de tempo em que conteúdo visível
     *    pode ser capturado por ferramentas que leem o framebuffer.
     *  - Mesmo assim, não há garantia absoluta contra todas as formas de captura.
     */
    private void activateProtectionImmediately() {
        if (!isProtectionActive) {
            isProtectionActive = true;

            // Limpa a área de transferência múltiplas vezes
            clearClipboardAggressively();
            System.out.println("[PROTEÇÃO] Área de transferência limpa ao ativar proteção");

            // Mostra o overlay imediatamente
            protectionOverlay.setOpaque(true);
            protectionOverlay.setVisible(true);

            // Força repaint imediato do overlay (bypassa fila do Event Dispatch)
            if (protectionOverlay.getWidth() > 0 && protectionOverlay.getHeight() > 0) {
                protectionOverlay.paintImmediately(0, 0, protectionOverlay.getWidth(), protectionOverlay.getHeight());
            }

            // Força repaint do glass pane se existir
            if (mainFrame != null && mainFrame.getGlassPane() != null) {
                mainFrame.getGlassPane().repaint();
            }

            // Agenda desativação automática após 3s (Timer Swing)
            Timer deactivateTimer = new Timer(3000, e -> deactivateProtection());
            deactivateTimer.setRepeats(false);
            deactivateTimer.start();
        }
    }

    // Alias mantido para compatibilidade
    private void activateProtection() {
        activateProtectionImmediately();
    }

    /**
     * deactivateProtection
     *
     * Desativa overlay somente se a janela estiver ativa e focada. Usa pequeno atraso (500ms)
     * para evitar ciclos rápidos de show/hide.
     */
    private void deactivateProtection() {
        if (mainFrame != null && mainFrame.isActive() && mainFrame.isFocused()) {
            Timer showTimer = new Timer(500, e -> {
                if (mainFrame.isActive() && mainFrame.isFocused()) {
                    isProtectionActive = false;
                    SwingUtilities.invokeLater(() -> {
                        protectionOverlay.setVisible(false);
                        protectionOverlay.setOpaque(false);
                    });
                }
            });
            showTimer.setRepeats(false);
            showTimer.start();
        }
    }

    /**
     * clearClipboardAggressively
     *
     * Cria uma thread que chama clearClipboard repetidamente (10 vezes, com 50ms entre)
     * para aumentar chance de sucesso contra operações concorrentes que escrevem na área de transferência.
     */
    private void clearClipboardAggressively() {
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                clearClipboard();
                try {
                    Thread.sleep(50);
                } catch (InterruptedException ex) {
                    break;
                }
            }
        }).start();
    }

    /**
     * startClipboardWatchdog
     *
     * Inicia thread daemon que checa CLIPBOARD a cada 50ms. Se detectar imagem (DataFlavor.imageFlavor),
     * limpa o clipboard automaticamente. Usa prioridade máxima e é muito agressivo.
     *
     * Observações:
     *  - A checagem a cada 50ms pode consumir CPU; ajustar conforme necessidade.
     *  - Alguns ambientes/tray apps escrevem periodicamente no clipboard; esta thread pode causar "luta" entre apps.
     */
    private void startClipboardWatchdog() {
        clipboardWatchdog = new Thread(() -> {
            while (running) {
                try {
                    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                    if (clipboard.isDataFlavorAvailable(DataFlavor.imageFlavor)) {
                        // Remove imagens detectadas
                        clearClipboard();
                        System.out.println("[BLOQUEADO] Imagem detectada no clipboard e removida!");
                    }
                    Thread.sleep(50); // 50ms -> alta frequência
                } catch (Exception ex) {
                    // Ignora erros e continua
                }
            }
        });
        clipboardWatchdog.setDaemon(true);
        clipboardWatchdog.setPriority(Thread.MAX_PRIORITY);
        clipboardWatchdog.start();
    }

    /**
     * clearClipboard
     *
     * Substitui o conteúdo do clipboard por uma StringSelection vazia. Trata exceções silenciosamente.
     */
    private void clearClipboard() {
        try {
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(
                new java.awt.datatransfer.StringSelection(""), null);
        } catch (Exception ex) {
            // Ignora erros de acesso ao clipboard (ex.: SecurityException)
        }
    }

    /**
     * startClipboardMonitoring
     *
     * Inicia um Timer Swing (100ms) como backup para checagem do clipboard — semelhante ao watchdog,
     * porém executando no contexto do Event Dispatch Thread (mais seguro para operações Swing).
     */
    private void startClipboardMonitoring() {
        if (clipboardMonitor == null) {
            clipboardMonitor = new Timer(100, e -> {
                try {
                    java.awt.datatransfer.Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                    if (clipboard.isDataFlavorAvailable(java.awt.datatransfer.DataFlavor.imageFlavor)) {
                        clearClipboard();
                        System.out.println("Imagem detectada na área de transferência e removida.");
                    }
                } catch (Exception ex) {
                    // Ignora erros de acesso
                }
            });
            clipboardMonitor.start();
        }
    }

    // Para parar o timer do clipboard se necessário
    private void stopClipboardMonitoring() {
        if (clipboardMonitor != null) {
            clipboardMonitor.stop();
        }
    }

    /**
     * openFile
     *
     * Mostra JFileChooser para o usuário selecionar um arquivo; filtra por extensões de texto/código.
     * Se aprovado, chama loadFile para leitura do arquivo.
     */
    private void openFile(RSyntaxTextArea textArea) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Arquivos de Texto", "txt", "java", "py", "cpp", "html", "js"));

        int returnValue = fileChooser.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            loadFile(file, textArea);
        }
    }

    /**
     * loadFile
     *
     * Lê o arquivo linha a linha e seta o texto no RSyntaxTextArea.
     * Registra algumas informações de debug (linhas lidas, tamanho etc).
     *
     * Observações:
     *  - Usa FileReader/BufferedReader simples; não considera encoding (UTF-8 etc).
     *    Para arquivos com acentos/UTF-8, preferir `new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8)`.
     *  - Seta caretPosition(0) e faz revalidate/repaint para forçar atualização visual.
     */
    private void loadFile(File file, RSyntaxTextArea textArea) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder content = new StringBuilder();
            String line;
            int lineCount = 0;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
                lineCount++;
            }

            System.out.println("[DEBUG] Arquivo carregado: " + file.getName());
            System.out.println("[DEBUG] Linhas lidas: " + lineCount);
            System.out.println("[DEBUG] Total de caracteres: " + content.length());

            textArea.setText(content.toString());
            textArea.setCaretPosition(0);

            // Força re-layout e repaint do componente
            textArea.revalidate();
            textArea.repaint();

            System.out.println("[DEBUG] Texto definido na área de texto");
            System.out.println("[DEBUG] TextArea é visível: " + textArea.isVisible());
            System.out.println("[DEBUG] TextArea texto length: " + textArea.getText().length());

        } catch (IOException e) {
            // Mostra diálogo de erro ao usuário em caso de falha na leitura
            JOptionPane.showMessageDialog(null, "Erro ao abrir o arquivo: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}