package telas_Final;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;

public class TelaTrabalhos extends JPanel {

	private static final long serialVersionUID = 1L;
	private JScrollPane scrollPane;

	/**
	 * Create the panel.
	 */
	public TelaTrabalhos(Primario prim) {
		setPreferredSize(new Dimension(900, 700));
		setBorder(new EmptyBorder(0, 0, 0, 0));
		setLayout(new MigLayout("fill, insets 0", "[20px][grow][grow][grow][grow][][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][][grow][grow][20px]", "[35px][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][35px]"));

		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 102, 204));
		add(panel, "flowx,cell 0 0 41 1,grow");
		panel.setLayout(new MigLayout("fill", "[center]", "[]"));
		URL imageUrl = getClass().getResource("/imagens/clickable_icon.png");
		System.out.println("URL do ícone: " + imageUrl);

		if (imageUrl == null) {
		    System.err.println("Imagem não encontrada!");
		} else {
		    JLabel iconLabel = new JLabel(new ImageIcon(imageUrl));
		    // Adicione no layout
		}

		JLabel lblNewLabel = new JLabel("WorkITBr");
		panel.add(lblNewLabel, "cell 0 0");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblNewLabel.setForeground(Color.WHITE);
		
		JLabel lblNewLabel_1 = new JLabel("Trabalhos em andamento");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblNewLabel_1, "cell 2 3 3 1,grow");
		
		scrollPane = new JScrollPane();
		scrollPane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		scrollPane.setBackground(Color.LIGHT_GRAY);
		scrollPane.putClientProperty("FlatLaf.style", "arc: 20; background: #BFBFBF;");
		add(scrollPane, "cell 2 4 3 11,grow");
		
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.LIGHT_GRAY);
		add(panel_1, "cell 7 4 11 2,grow");
		panel_1.putClientProperty("FlatLaf.style", "arc: 20; background: #BFBFBF;");
		
		
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.LIGHT_GRAY);
		add(panel_2, "cell 7 7 11 2,grow");
		panel_2.putClientProperty("FlatLaf.style", "arc: 20; background: #BFBFBF;");
		
		
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(Color.LIGHT_GRAY);
		add(panel_3, "cell 7 10 11 2,grow");
		panel_3.putClientProperty("FlatLaf.style", "arc: 20; background: #BFBFBF;");
		
		
		
		JPanel panel_4 = new JPanel();
		panel_4.setBackground(Color.LIGHT_GRAY);
		add(panel_4, "cell 7 13 11 2,grow");
		panel_4.putClientProperty("FlatLaf.style", "arc: 20; background: #BFBFBF;");
		
		ImageIcon originalIcon = new ImageIcon(getClass().getResource("/imagens/clickable_icon.png"));
		Image scaledImage = originalIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
		ImageIcon resizedIcon = new ImageIcon(scaledImage);

		JLabel iconLabel = new JLabel(resizedIcon);
		add(iconLabel, "cell 18 5");
		iconLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		JLabel lblNewLabel_3 = new JLabel(resizedIcon);
		add(lblNewLabel_3, "cell 18 8");
		lblNewLabel_3.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		JLabel lblNewLabel_4 = new JLabel(resizedIcon);
		add(lblNewLabel_4, "cell 18 11");
		lblNewLabel_4.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		JLabel lblNewLabel_5 = new JLabel(resizedIcon);
		add(lblNewLabel_5, "cell 18 14");
		lblNewLabel_5.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		


	}

}
