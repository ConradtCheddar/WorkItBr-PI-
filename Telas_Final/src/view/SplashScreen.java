// Define o pacote onde esta classe está localizada (agrupamento lógico de classes relacionadas)
package view;

// Importa componentes Swing para construção da interface gráfica
import javax.swing.*;
// Importa classes AWT para gerenciamento de layout, cores, dimensões e imagens
import java.awt.*;

/**
 * Tela de abertura (splash screen) exibida durante o carregamento da aplicação.
 * <p>
 * Responsável por: exibir o logo e nome da aplicação, mostrar uma barra de progresso
 * visual durante a inicialização, exibir mensagens de status das etapas de carga,
 * fornecer feedback visual profissional ao usuário durante o startup, e fechar-se
 * automaticamente quando a inicialização é concluída.
 * </p>
 */
public class SplashScreen extends JWindow {
    // Identificador de versão para serialização (compatibilidade entre versões)
    private static final long serialVersionUID = 1L;
    // Barra de progresso que indica visualmente o andamento da inicialização
    private JProgressBar progressBar;
    // Label que exibe mensagens textuais sobre o status atual ("Inicializando...", etc.)
    private JLabel lblStatus;
    // Label que exibe o logo/ícone da aplicação
    private JLabel lblLogo;

    /**
     * Construtor que cria e configura a tela de splash.
     * <p>
     * Inicializa todos os componentes visuais, define cores e estilos,
     * organiza o layout e centraliza a janela na tela.
     * </p>
     */
    public SplashScreen() {
        // Cria o painel de conteúdo principal com BorderLayout
        JPanel content = new JPanel(new BorderLayout());
        // Define cor de fundo escura (cinza muito escuro)
        content.setBackground(new Color(30, 30, 30));
        // Adiciona borda azul de 3 pixels ao redor da tela
        content.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 204), 3));
        
        // Cria painel central para organizar elementos verticalmente
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        // Usa a mesma cor de fundo escura
        centerPanel.setBackground(new Color(30, 30, 30));
        // Adiciona margem interna de 40 pixels (topo/baixo) e 60 pixels (esquerda/direita)
        centerPanel.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));
        
        // Cria label com o nome da aplicação em fonte grande
        lblLogo = new JLabel("WorkITBr");
        // Define fonte grande e negrito (48 pixels)
        lblLogo.setFont(new Font("Segoe UI", Font.BOLD, 48));
        // Define cor azul característica da aplicação
        lblLogo.setForeground(new Color(0, 102, 204));
        // Centraliza o texto horizontalmente
        lblLogo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Tenta carregar e exibir o ícone da aplicação
        try {
            // Carrega o ícone do arquivo de recursos
            ImageIcon icon = new ImageIcon(getClass().getResource("/imagens/w.png"));
            // Redimensiona a imagem para 80x80 pixels com interpolação suave
            Image img = icon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
            // Cria label com o ícone redimensionado
            JLabel lblIcon = new JLabel(new ImageIcon(img));
            // Centraliza o ícone horizontalmente
            lblIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
            // Adiciona o ícone ao painel central
            centerPanel.add(lblIcon);
            // Adiciona espaçamento vertical de 20 pixels
            centerPanel.add(Box.createVerticalStrut(20));
        } catch (Exception e) {
            // Se não encontrar o ícone, ignora silenciosamente e continua sem ele
        }
        
        // Adiciona o logo de texto ao painel central
        centerPanel.add(lblLogo);
        // Adiciona espaçamento vertical de 30 pixels
        centerPanel.add(Box.createVerticalStrut(30));
        
        // Cria barra de progresso de 0 a 100
        progressBar = new JProgressBar(0, 100);
        // Define tamanho preferencial da barra (400x25 pixels)
        progressBar.setPreferredSize(new Dimension(400, 25));
        // Define tamanho máximo para evitar que cresça demais
        progressBar.setMaximumSize(new Dimension(400, 25));
        // Exibe o percentual como texto sobre a barra
        progressBar.setStringPainted(true);
        // Define cor azul para a parte preenchida da barra
        progressBar.setForeground(new Color(0, 102, 204));
        // Define cor de fundo escura para a parte não preenchida
        progressBar.setBackground(new Color(50, 50, 50));
        // Remove a borda padrão da barra
        progressBar.setBorderPainted(false);
        // Centraliza a barra horizontalmente
        progressBar.setAlignmentX(Component.CENTER_ALIGNMENT);
        // Adiciona a barra ao painel central
        centerPanel.add(progressBar);
        
        // Adiciona espaçamento vertical de 15 pixels
        centerPanel.add(Box.createVerticalStrut(15));
        
        // Cria label para exibir mensagens de status
        lblStatus = new JLabel("Inicializando...");
        // Define fonte normal de tamanho 14
        lblStatus.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        // Define cor branca para o texto
        lblStatus.setForeground(Color.WHITE);
        // Centraliza o texto horizontalmente
        lblStatus.setAlignmentX(Component.CENTER_ALIGNMENT);
        // Adiciona o status ao painel central
        centerPanel.add(lblStatus);
        
        // Adiciona o painel central ao conteúdo principal
        content.add(centerPanel, BorderLayout.CENTER);
        
        // Cria label de rodapé com informação de versão
        JLabel lblVersion = new JLabel("Versão 1.0");
        // Define fonte pequena (11 pixels)
        lblVersion.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        // Define cor cinza para texto discreto
        lblVersion.setForeground(Color.GRAY);
        // Centraliza o texto horizontalmente
        lblVersion.setHorizontalAlignment(SwingConstants.CENTER);
        // Adiciona margem interna de 10 pixels (topo/baixo)
        lblVersion.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        // Adiciona versão ao rodapé (parte inferior)
        content.add(lblVersion, BorderLayout.SOUTH);
        
        // Define o painel de conteúdo como conteúdo da janela
        setContentPane(content);
        // Ajusta o tamanho da janela para caber todos os componentes
        pack();
        // Centraliza a janela na tela
        setLocationRelativeTo(null);
    }
    
    /**
     * Atualiza o progresso da inicialização e a mensagem de status.
     * <p>
     * Executa a atualização de forma segura na thread de interface (Event Dispatch Thread)
     * para evitar problemas de concorrência.
     * </p>
     * 
     * @param progress valor entre 0 e 100 indicando o percentual de conclusão
     * @param status mensagem descritiva da etapa atual ("Carregando banco...", etc.)
     */
    public void setProgress(int progress, String status) {
        // Executa a atualização na thread de interface Swing de forma segura
        SwingUtilities.invokeLater(() -> {
            // Atualiza o valor da barra de progresso
            progressBar.setValue(progress);
            // Atualiza o texto da mensagem de status
            lblStatus.setText(status);
        });
    }
    
    /**
     * Fecha a tela de splash com um delay padrão de 300ms.
     * <p>
     * Aguarda um pequeno tempo antes de fechar para suavizar a transição
     * visual entre a splash screen e a janela principal.
     * </p>
     */
    public void closeSplash() {
        // Chama o método sobrecarregado com delay padrão de 300 milissegundos
        closeSplash(300);
    }
    
    /**
     * Fecha a tela de splash com delay customizado.
     * <p>
     * Permite controlar o tempo de espera antes de fechar, útil para
     * dar ao usuário tempo de ver a conclusão da barra de progresso.
     * </p>
     * 
     * @param delayMs tempo de espera em milissegundos antes de fechar a janela
     */
    public void closeSplash(int delayMs) {
        // Executa o fechamento na thread de interface de forma segura
        SwingUtilities.invokeLater(() -> {
            try {
                // Aguarda o tempo especificado antes de fechar
                Thread.sleep(delayMs);
            } catch (InterruptedException e) {
                // Se a thread for interrompida, imprime o stack trace para debug
                e.printStackTrace();
            }
            // Torna a janela invisível
            setVisible(false);
            // Libera recursos da janela
            dispose();
        });
    }
}