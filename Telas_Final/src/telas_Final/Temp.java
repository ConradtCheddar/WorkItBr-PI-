package telas_Final;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Temp extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public Temp(Primario prim) {
		setPreferredSize(new Dimension(700, 500));
		setBorder(new EmptyBorder(0, 0, 0, 0));
		setLayout(new MigLayout("fill, insets 0", "[20px][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][20px]", "[35px][grow][grow][grow][grow][grow][grow][grow][grow][][grow][grow][grow][grow][][grow][grow][][grow][grow][][][][][][grow][grow][grow][][grow][grow][][grow][][][grow][][][grow][grow][grow][grow][grow][grow][35px]"));

		JPanel panel = new JPanel();
		panel.setBackground(Color.BLUE);
		add(panel, "flowx,cell 0 0 27 1,grow");
		panel.setLayout(new MigLayout("fill", "[center]", "[]"));

		JLabel lblNewLabel = new JLabel("WorkITBr");
		panel.add(lblNewLabel, "cell 0 0");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblNewLabel.setForeground(Color.WHITE);
		
		JButton btnNewButton = new JButton("Login");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				prim.mostrarTela(prim.LOGIN_PANEL);
			}
		});
		add(btnNewButton, "cell 13 22,grow");
		
		JButton btnADM = new JButton("ADM");
		btnADM.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				prim.mostrarTela(prim.ADM_PANEL);
			}
		});
		add(btnADM, "cell 13 28,grow");
		
		
		

	}

}
