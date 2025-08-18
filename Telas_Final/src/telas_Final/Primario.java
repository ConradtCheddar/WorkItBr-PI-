package telas_Final;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.FlowLayout;
import java.awt.CardLayout;

public class Primario extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel currentPanel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Primario frame = new Primario();
					JPanel panel_1 = new TelaLogin(frame);
					frame.atualizarPainel(panel_1);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Primario() {
		setTitle("WorkITBr");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setPreferredSize(new Dimension(1034, 771));
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(contentPane);
		contentPane.setLayout(new CardLayout(0, 0));

	}

	public void atualizarPainel(JPanel j) {

		if (currentPanel != null) {
			contentPane.remove(currentPanel);
		}

		currentPanel = j;
		contentPane.add(j, "cell 0 1,grow");

		pack();

	}

}
