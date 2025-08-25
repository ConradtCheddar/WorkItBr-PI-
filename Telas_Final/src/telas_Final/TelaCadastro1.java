package telas_Final;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import org.jdesktop.swingx.prompt.PromptSupport;

import net.miginfocom.swing.MigLayout;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TelaCadastro1 extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField txtEmail;
	private JTextField txtTelefone;
	private JTextField txtCPF;
	private JButton btnNewButton;

	/**
	 * Create the panel.
	 */
	public TelaCadastro1(Primario prim) {
		setPreferredSize(new Dimension(700, 500));
		setBorder(new EmptyBorder(0, 0, 0, 0));
		setLayout(new MigLayout("fill, insets 0", "[20px][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][20px]", "[35px][grow][grow][grow][grow][grow][grow][grow][grow][][grow][grow][grow][grow][][grow][grow][][grow][grow][][][][][grow][grow][grow][grow][grow][][grow][][][grow][][][grow][grow][grow][grow][grow][grow][35px]"));

		JPanel panel = new JPanel();
		panel.setBackground(Color.BLUE);
		add(panel, "flowx,cell 0 0 26 1,grow");
		panel.setLayout(new MigLayout("fill", "[center]", "[]"));

		JLabel lblNewLabel = new JLabel("WorkITBr");
		panel.add(lblNewLabel, "cell 0 0");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblNewLabel.setForeground(Color.WHITE);
		
		txtEmail = new JTextField();
		add(txtEmail, "cell 4 14 18 1,growx");
		txtEmail.setColumns(10);
		PromptSupport.setPrompt("Email", txtEmail);
		PromptSupport.setForeground(new Color(100, 100, 100), txtEmail);
		
		txtTelefone = new JTextField();
		add(txtTelefone, "cell 4 17 18 1,growx");
		txtTelefone.setColumns(10);
		PromptSupport.setPrompt("Telefone", txtTelefone);
		PromptSupport.setForeground(new Color(100, 100, 100), txtTelefone);
		
		txtCPF = new JTextField();
		add(txtCPF, "cell 4 20 18 1,growx");
		txtCPF.setColumns(10);
		PromptSupport.setPrompt("CPF", txtCPF);
		PromptSupport.setForeground(new Color(100, 100, 100), txtCPF);
		
		btnNewButton = new JButton("Continuar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				prim.mostrarTela(prim.CAD2_PANEL);
			}
		});
		add(btnNewButton, "cell 10 32 5 1,grow");
		
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
				int fontSize = Math.max(15, panelHeight / 25);
				txtEmail.setFont(new Font("Tahoma", Font.PLAIN, fontSize));
				txtTelefone.setFont(new Font("Tahoma", Font.PLAIN, fontSize));
				txtCPF.setFont(new Font("Tahoma", Font.PLAIN, fontSize));
				btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, fontSize));
			}
		});
	}

}
