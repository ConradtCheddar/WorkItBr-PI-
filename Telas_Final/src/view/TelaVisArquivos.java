package view;

import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

import model.Servico;
import net.miginfocom.swing.MigLayout;
import util.FontScaler;

public class TelaVisArquivos extends JPanel {

	private static final long serialVersionUID = 1L;
	private RSyntaxTextArea mainTextArea;
	
    Mensagem M = new Mensagem();
    
	public TelaVisArquivos(Servico s) {
		setBorder(new EmptyBorder(0, 0, 0, 0));
		setLayout(new MigLayout("fill, insets 0", "[20px][98.00,grow][grow][81.00][-56.00][65.00,grow][][]",
				"[35px][66.00,grow][grow][][][66]"));

		mainTextArea = new RSyntaxTextArea();
		mainTextArea.setEditable(false);
		mainTextArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
		mainTextArea.setCodeFoldingEnabled(true);
		mainTextArea.setBackground(Color.BLACK);
		mainTextArea.setForeground(Color.WHITE);
		mainTextArea.setCurrentLineHighlightColor(new Color(240, 240, 240));
		mainTextArea.setCaretPosition(0);

		RTextScrollPane scrollPane = new RTextScrollPane(mainTextArea);
		add(scrollPane, "cell 1 1 4 4,grow");
		
		FontScaler.addResizeCallback(this, () -> {
			int panelHeight = getHeight();
			int fontSize = Math.max(12, panelHeight / 40);
			mainTextArea.setFont(new Font("Monospaced", Font.PLAIN, fontSize));
		});
	}

	private void loadFile(File file, RSyntaxTextArea textArea) {
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			StringBuilder content = new StringBuilder();
			String line;

			while ((line = reader.readLine()) != null) {
				content.append(line).append("\n");
			}

			textArea.setText(content.toString());
			textArea.setCaretPosition(0);
			textArea.revalidate();
			textArea.repaint();

			System.out.println("[INFO] Arquivo carregado: " + file.getName());
		} catch (IOException e) {
          M.Erro("Erro ao carregar o Arquivo","Erro");
		}
	}
}
