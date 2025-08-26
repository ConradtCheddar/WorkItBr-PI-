package telas_Final;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;

public class TelaAdm extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public TelaAdm(Primario prim) {
		setPreferredSize(new Dimension(700, 500));
		setBorder(new EmptyBorder(0, 0, 0, 0));
		setLayout(new MigLayout("fill, insets 0", "[20px][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][][][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][20px]", "[35px][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][][grow][grow][][grow][grow][][grow][grow][][grow][grow][][][][][][][grow][grow][grow][grow][grow][][grow][][][grow][][][grow][grow][grow][grow][grow][grow][35px]"));

		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 102, 204));
		add(panel, "flowx,cell 0 0 29 1,grow");
		panel.setLayout(new MigLayout("fill", "[center]", "[]"));

		JLabel lblNewLabel = new JLabel("WorkITBr");
		panel.add(lblNewLabel, "cell 0 0");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblNewLabel.setForeground(Color.WHITE);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.LIGHT_GRAY);
		add(panel_1, "cell 2 6 8 39,grow");
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.LIGHT_GRAY);
		add(panel_2, "cell 19 6 8 39,grow");
		
		JButton btnBanir = new JButton("Banir Usuário");
		btnBanir.setBackground(new Color(0, 102, 204));
		btnBanir.setForeground(Color.WHITE);
		btnBanir.putClientProperty("JComponent.roundRect", true);
		add(btnBanir, "cell 11 23 7 1,grow");
		
		JButton btnAnalisar = new JButton("Analisar Denúncia");
		btnAnalisar.setForeground(Color.WHITE);
		btnAnalisar.setBackground(new Color(0, 102, 204));
		btnAnalisar.putClientProperty("JComponent.roundRect", true);
		add(btnAnalisar, "cell 11 28 7 1,grow");
		
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				int panelHeight = getHeight();
				int fontSize = Math.max(15, panelHeight / 17);
				lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, fontSize));
			}
		});
		
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				int panelHeight = getHeight();
				int fontSize = Math.max(15, panelHeight / 35);
				btnAnalisar.setFont(new Font("Tahoma", Font.PLAIN, fontSize));
				btnBanir.setFont(new Font("Tahoma", Font.PLAIN, fontSize));
			}
		});

	}

}
