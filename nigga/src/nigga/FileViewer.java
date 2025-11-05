package nigga;
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

// Added imports
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

public class FileViewer {
    private Robot robot;
    private Timer clipboardMonitor;
    private JPanel protectionOverlay;
    private volatile boolean isProtectionActive = false;
    private Thread clipboardWatchdog;
    private volatile boolean running = true;
    private JFrame mainFrame;
    private NonSelectableRSyntaxTextArea mainTextArea;
    private Thread focusMonitor;
    
    // Non-selectable RSyntaxTextArea subclass: overrides selection/copy methods so selection/copy always do nothing
    private static class NonSelectableRSyntaxTextArea extends RSyntaxTextArea {
        public NonSelectableRSyntaxTextArea() {
            super();
        }
        @Override
        public void select(int selectionStart, int selectionEnd) {
            // ignore attempts to programmatically select
        }
        @Override
        public void selectAll() {
            // ignore
        }
        @Override
        public String getSelectedText() {
            return null; // pretend there's no selection
        }
        @Override
        public void copy() {
            // ignore copy attempts
        }
        @Override
        public void cut() {
            // ignore cut attempts
        }
        @Override
        public void replaceSelection(String content) {
            // ignore
        }
        @Override
        public int getSelectionStart() {
            return getCaretPosition();
        }
        @Override
        public int getSelectionEnd() {
            return getCaretPosition();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new FileViewer().createAndShowGUI();
        });
    }

    private void createAndShowGUI() {
        mainFrame = new JFrame("Visualizador de Arquivos");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(800, 600);
        mainFrame.setLayout(new BorderLayout());
        
        // Initialize Robot for blocking print screen
        try {
            robot = new Robot();
        } catch (AWTException e) {
            System.err.println("Não foi possível criar Robot para bloquear PrintScreen: " + e.getMessage());
        }
        
        // Create protection overlay panel (shows when screenshot attempt detected)
        protectionOverlay = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.BLACK);
                g.fillRect(0, 0, getWidth(), getHeight());
                g.setColor(Color.RED);
                g.setFont(new Font("Arial", Font.BOLD, 48));
                String msg = "CAPTURA BLOQUEADA";
                int msgWidth = g.getFontMetrics().stringWidth(msg);
                g.drawString(msg, (getWidth() - msgWidth) / 2, getHeight() / 2);
            }
        };
        protectionOverlay.setVisible(false);
        protectionOverlay.setOpaque(true);
        
        // Start with protection active - content only visible when window is focused
        isProtectionActive = true;
        
        // Create a RSyntaxTextArea instance for displaying code
        mainTextArea = new NonSelectableRSyntaxTextArea();
         mainTextArea.setEditable(false);  // Don't allow editing
         mainTextArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA); // Set syntax style for Java code
         mainTextArea.setSelectionColor(new Color(255, 255, 255, 0));  // Torna a seleção invisível
         mainTextArea.setSelectedTextColor(new Color(255, 255, 255, 0)); // Não exibe a cor do texto selecionado
         mainTextArea.setCaretPosition(0); // Define a posição do cursor no início para evitar a seleção
         
         // --- Prevent selection and copying ---
         // Make non-focusable so keyboard shortcuts won't target the area
         mainTextArea.setFocusable(false);
         // Disable drag & drop / clipboard transfers
         mainTextArea.setTransferHandler(null);
         // Disable any component popup menu (extra safety)
         mainTextArea.setComponentPopupMenu(null);
         // Remove highlighter to avoid visual selection highlights
         mainTextArea.setHighlighter(null);

         // Replace caret so moving the caret doesn't create a selection
         mainTextArea.setCaret(new DefaultCaret() {
             @Override
             public void moveDot(int dot) {
                 // Prevent creation of a selection by moving the caret without extending a selection
                 setDot(dot);
             }
             @Override
             public void setSelectionVisible(boolean v) {
                 // Ignore attempts to show selection
             }
         });

         // Remove common copy/select keyboard bindings
         KeyStroke[] keys = new KeyStroke[] {
            KeyStroke.getKeyStroke("control C"),
            KeyStroke.getKeyStroke("control X"),
            KeyStroke.getKeyStroke("control A"),
            KeyStroke.getKeyStroke("shift INSERT"),
            KeyStroke.getKeyStroke("ctrl INSERT")
        };
        int[] inputMapTypes = new int[] { JComponent.WHEN_FOCUSED, JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT, JComponent.WHEN_IN_FOCUSED_WINDOW };
        for (int type : inputMapTypes) {
            InputMap im2 = mainTextArea.getInputMap(type);
            if (im2 != null) {
                for (KeyStroke ks : keys) {
                    if (ks != null) im2.put(ks, "none");
                }
            }
        }

         // Consume mouse events that could cause selection or popup
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
         // --- end prevention changes ---
         
         
         // Add the text area to a scroll pane
         RTextScrollPane scrollPane = new RTextScrollPane(mainTextArea);
         
         // Simply add scrollPane directly to frame - forget complex layering
         mainFrame.add(scrollPane, BorderLayout.CENTER);
         
         // Add overlay on top using Glass Pane
         mainFrame.setGlassPane(protectionOverlay);
         protectionOverlay.setOpaque(false); // Make transparent when not active
         
         // Create a menu for opening files
         JMenuBar menuBar = new JMenuBar();
         JMenu fileMenu = new JMenu("Arquivo");
         JMenuItem openItem = new JMenuItem("Abrir");

         // Action to open a file
         openItem.addActionListener(e -> openFile(mainTextArea));
         
         fileMenu.add(openItem);
         menuBar.add(fileMenu);
         mainFrame.setJMenuBar(menuBar);
         

         // Show the frame
         mainFrame.setVisible(true);
         
         mainTextArea.addMouseListener(new MouseAdapter() {
             public void mousePressed(MouseEvent e) {
                 // Impede o menu de contexto ao clicar com o botão direito
                 if (e.isPopupTrigger()) {
                     e.consume(); // Ignora o clique do botão direito
                 }
             }

             public void mouseReleased(MouseEvent e) {
                 if (e.isPopupTrigger()) {
                     e.consume(); // Ignora o clique do botão direito
                 }
             }
         });
         
         // --- Block PrintScreen and screenshot attempts ---
         // LAYER 1: Global KeyEventDispatcher (intercepts before any component)
         KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
             @Override
             public boolean dispatchKeyEvent(KeyEvent e) {
                 // PRE-EMPTIVE PROTECTION: Hide content immediately when Windows key is pressed
                 // This gives us time BEFORE user presses Shift+S
                 if (e.getKeyCode() == KeyEvent.VK_WINDOWS || e.getKeyCode() == KeyEvent.VK_META) {
                     activateProtectionImmediately();
                     System.out.println("[ALERTA] Tecla Windows detectada - proteção ativada preventivamente!");
                     // Don't consume - let Windows menu open if needed
                 }
                 
                 // Also hide on Shift key when Windows is already pressed
                 if (e.getKeyCode() == KeyEvent.VK_SHIFT && e.isMetaDown()) {
                     activateProtectionImmediately();
                     System.out.println("[ALERTA] Windows+Shift detectado - bloqueando captura!");
                 }
                 
                 // Activate protection IMMEDIATELY on ANY PrintScreen related key event
                 if (e.getKeyCode() == KeyEvent.VK_PRINTSCREEN) {
                     activateProtectionImmediately();
                     e.consume();
                     clearClipboardAggressively();
                     System.out.println("[BLOQUEADO] PrintScreen detectado!");
                     return true;
                 }
                 
                 if (e.getID() == KeyEvent.KEY_PRESSED) {
                     // Block Windows+Shift+S
                     if (e.getKeyCode() == KeyEvent.VK_S && e.isShiftDown() && e.isMetaDown()) {
                         activateProtectionImmediately();
                         e.consume();
                         clearClipboardAggressively();
                         System.out.println("[BLOQUEADO] Ferramenta de captura detectada!");
                         return true;
                     }
                 }
                 return false;
             }
         });
         
         // LAYER 2: AWTEventListener (system-level backup)
         Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {
             @Override
             public void eventDispatched(AWTEvent event) {
                 if (event instanceof KeyEvent) {
                     KeyEvent keyEvent = (KeyEvent) event;
                     // Trigger on ANY key event type for PrintScreen
                     if (keyEvent.getKeyCode() == KeyEvent.VK_PRINTSCREEN) {
                         activateProtectionImmediately();
                         keyEvent.consume();
                         clearClipboardAggressively();
                         // Force release the key using Robot
                         if (robot != null) {
                             try {
                                 robot.keyRelease(KeyEvent.VK_PRINTSCREEN);
                             } catch (Exception ex) {
                                 // Ignore
                             }
                         }
                         System.out.println("[BLOQUEADO] PrintScreen (AWTListener)!");
                     }
                 }
             }
         }, AWTEvent.KEY_EVENT_MASK);
         
         // LAYER 3: Window state monitoring (detects minimize/iconify that might indicate screenshot tool)
         mainFrame.addWindowStateListener(new WindowStateListener() {
             @Override
             public void windowStateChanged(WindowEvent e) {
                 if ((e.getNewState() & java.awt.Frame.ICONIFIED) != 0) {
                     // Window minimized - only clear clipboard, don't show overlay
                     clearClipboard();
                     System.out.println("[ALERTA] Janela minimizada - clipboard limpo");
                 }
             }
         });
         
         // LAYER 4: Window focus monitoring - AGGRESSIVE MODE
         // Content is ONLY visible when window has focus
         // This prevents screenshot tools from capturing content
         mainFrame.addWindowFocusListener(new WindowFocusListener() {
             @Override
             public void windowGainedFocus(WindowEvent e) {
                 // Only show content when window has focus
                 SwingUtilities.invokeLater(() -> {
                     deactivateProtection();
                     System.out.println("[INFO] Janela recuperou foco - conteúdo visível");
                 });
             }
             
             @Override
             public void windowLostFocus(WindowEvent e) {
                 // IMMEDIATELY hide content when losing focus
                 // This ensures screenshot tools only see black screen
                 activateProtectionImmediately();
                 clearClipboard();
                 System.out.println("[ALERTA] Janela perdeu foco - conteúdo escondido!");
             }
         });
         
         // LAYER 4b: Window state listener - hide content when inactive
         mainFrame.addWindowListener(new java.awt.event.WindowAdapter() {
             @Override
             public void windowDeactivated(WindowEvent e) {
                 // Window became inactive - hide content immediately
                 activateProtectionImmediately();
                 System.out.println("[ALERTA] Janela desativada - conteúdo escondido!");
             }
             
             @Override
             public void windowActivated(WindowEvent e) {
                 // Window became active - show content
                 SwingUtilities.invokeLater(() -> {
                     deactivateProtection();
                     System.out.println("[INFO] Janela ativada - conteúdo visível");
                 });
             }
         });
         
         // LAYER 5: Aggressive clipboard watchdog thread (monitors every 50ms)
         startClipboardWatchdog();
         
         // LAYER 6: Timer-based clipboard monitor (backup)
         startClipboardMonitoring();
         
         // LAYER 7: Focus monitor thread - constantly checks if window is active
         startFocusMonitor();
         
         // Ensure frame can receive events
         mainFrame.setFocusable(true);
         mainFrame.requestFocus();
         
         // Add shutdown hook to stop threads
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
     
     // Start focus monitoring thread that constantly checks window state
     private void startFocusMonitor() {
         focusMonitor = new Thread(() -> {
             while (running) {
                 try {
                     // Check if window is focused/active
                     if (mainFrame != null) {
                         boolean isActive = mainFrame.isActive();
                         boolean isFocused = mainFrame.isFocused();
                         
                         if (!isActive || !isFocused) {
                             // Window is not active - hide content immediately
                             if (!isProtectionActive) {
                                 activateProtectionImmediately();
                             }
                         }
                     }
                     Thread.sleep(10); // Check every 10ms for instant response
                 } catch (InterruptedException ex) {
                     break;
                 } catch (Exception ex) {
                     // Ignore errors and continue monitoring
                 }
             }
         });
         focusMonitor.setDaemon(true);
         focusMonitor.setPriority(Thread.MAX_PRIORITY);
         focusMonitor.start();
     }
     
     // Activate protection overlay IMMEDIATELY (synchronous, no delay)
     private void activateProtectionImmediately() {
         if (!isProtectionActive) {
             isProtectionActive = true;
             
             // CRITICAL: Clear clipboard immediately when protection activates
             clearClipboardAggressively();
             System.out.println("[PROTEÇÃO] Área de transferência limpa ao ativar proteção");
             
             // Execute directly - no threading delays
             protectionOverlay.setOpaque(true);
             protectionOverlay.setVisible(true);
             
             // CRITICAL: Force immediate painting to video buffer
             // This bypasses the normal paint queue and renders NOW
             if (protectionOverlay.getWidth() > 0 && protectionOverlay.getHeight() > 0) {
                 protectionOverlay.paintImmediately(0, 0, protectionOverlay.getWidth(), protectionOverlay.getHeight());
             }
             
             // Also force the glass pane to repaint immediately
             if (mainFrame != null && mainFrame.getGlassPane() != null) {
                 mainFrame.getGlassPane().repaint();
             }
             
             // Schedule auto-deactivate after 3 seconds
             Timer deactivateTimer = new Timer(3000, e -> deactivateProtection());
             deactivateTimer.setRepeats(false);
             deactivateTimer.start();
         }
     }
     
     // Activate protection overlay (kept for compatibility)
     private void activateProtection() {
         activateProtectionImmediately();
     }
     
     // Deactivate protection overlay
     private void deactivateProtection() {
         // Only deactivate if window is actually focused/active
         if (mainFrame != null && mainFrame.isActive() && mainFrame.isFocused()) {
             // Add a small delay before showing content again
             // This prevents rapid show/hide cycles
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
     
     // Aggressive clipboard clearing (clears multiple times)
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
     
     // Start dedicated watchdog thread for clipboard monitoring
     private void startClipboardWatchdog() {
         clipboardWatchdog = new Thread(() -> {
             while (running) {
                 try {
                     Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                     if (clipboard.isDataFlavorAvailable(DataFlavor.imageFlavor)) {
                         // Image detected in clipboard - clear it silently (don't show overlay to avoid false positives)
                         clearClipboard();
                         System.out.println("[BLOQUEADO] Imagem detectada no clipboard e removida!");
                     }
                     Thread.sleep(50); // Check every 50ms
                 } catch (Exception ex) {
                     // Ignore errors and continue monitoring
                 }
             }
         });
         clipboardWatchdog.setDaemon(true);
         clipboardWatchdog.setPriority(Thread.MAX_PRIORITY);
         clipboardWatchdog.start();
     }
     
     // Clear system clipboard to prevent screenshot tools from saving to clipboard
     private void clearClipboard() {
         try {
             Toolkit.getDefaultToolkit().getSystemClipboard().setContents(
                 new java.awt.datatransfer.StringSelection(""), null);
         } catch (Exception ex) {
             // Ignore clipboard access errors
         }
     }
     
     // Monitor clipboard for image content and clear it
     private void startClipboardMonitoring() {
         if (clipboardMonitor == null) {
             clipboardMonitor = new Timer(100, e -> {
                 try {
                     // Check if clipboard contains image data
                     java.awt.datatransfer.Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                     if (clipboard.isDataFlavorAvailable(java.awt.datatransfer.DataFlavor.imageFlavor)) {
                         // Clear clipboard if it contains an image
                         clearClipboard();
                         System.out.println("Imagem detectada na área de transferência e removida.");
                     }
                 } catch (Exception ex) {
                     // Ignore clipboard access errors
                 }
             });
             clipboardMonitor.start();
         }
     }
     
     private void stopClipboardMonitoring() {
         if (clipboardMonitor != null) {
             clipboardMonitor.stop();
         }
     }



     private void openFile(RSyntaxTextArea textArea) {
         JFileChooser fileChooser = new JFileChooser();
         fileChooser.setFileFilter(new FileNameExtensionFilter("Arquivos de Texto", "txt", "java", "py", "cpp", "html", "js"));
         
         int returnValue = fileChooser.showOpenDialog(null);
         
         if (returnValue == JFileChooser.APPROVE_OPTION) {
             File file = fileChooser.getSelectedFile();
             loadFile(file, textArea);
         }
     }

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
             
             // Force repaint
             textArea.revalidate();
             textArea.repaint();
             
             System.out.println("[DEBUG] Texto definido na área de texto");
             System.out.println("[DEBUG] TextArea é visível: " + textArea.isVisible());
             System.out.println("[DEBUG] TextArea texto length: " + textArea.getText().length());
             
         } catch (IOException e) {
             JOptionPane.showMessageDialog(null, "Erro ao abrir o arquivo: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
         }
     }
 }