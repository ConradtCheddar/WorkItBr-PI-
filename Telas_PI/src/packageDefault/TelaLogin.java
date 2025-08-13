package packageDefault;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;
import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TelaLogin extends JPanel {

	private static final long serialVersionUID = 1L;
	private Seila seila;

	/**
	 * Create the panel.
	 */
	public TelaLogin(Seila janela) {
		
		seila = janela;
		
		setBorder(new EmptyBorder(0, 0, 0, 0));
		
		setLayout(new MigLayout("", "[grow][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][grow]", "[grow][][][][][][][][][][][][][][][][][][][][][grow]"));
		
		JLabel lblNewLabel = new JLabel("Teste");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblNewLabel, "cell 8 6 16 2,grow");
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Container contentPane = seila.getContentPane();
				contentPane.remove(TelaLogin.this);
				
						
			}
		});
		add(btnNewButton, "cell 8 11 16 1,grow");
		
		
		
		
		
		

	}

}
