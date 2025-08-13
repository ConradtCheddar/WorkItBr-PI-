package packageDefault;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.CardLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;

public class Seila extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private TelaLogin tl;
	
	private JPanel currentPanel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Seila frame = new Seila();
					JPanel panel_1 = new p1(frame);
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
	public Seila() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 639, 612);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[grow]", "[grow][grow]"));
		
		JPanel panel = new JPanel();
		contentPane.add(panel, "cell 0 0,grow");
		panel.setLayout(new MigLayout("", "[46px]", "[14px]"));
		
		JLabel lblNewLabel_1 = new JLabel("barra sup");
		panel.add(lblNewLabel_1, "cell 0 0,alignx left,aligny top");
		
		
			}
	
	
	
	public void atualizarPainel(JPanel j) {
		
		if(currentPanel != null) {
			contentPane.remove(currentPanel);
		}
		
		currentPanel = j;
contentPane.add(j, "cell 0 1,grow");
		
		
		
		//contentPane.add(tl);
		pack();

		
		
	}

	
	

}
