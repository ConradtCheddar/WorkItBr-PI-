package packageDefault;

import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class p1 extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField textField;

	/**
	 * Create the panel.
	 */
	public p1(Seila j) {
		setLayout(new MigLayout("", "[][][][][][][][grow]", "[][][][][][][][]"));
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		add(lblNewLabel_1, "cell 3 2");
		
		textField = new JTextField();
		add(textField, "cell 7 3,growx");
		textField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("New label");
		add(lblNewLabel, "cell 7 6");
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPanel p2 = new p2(j);
				j.atualizarPainel(p2);
			}
		});
		add(btnNewButton, "cell 3 7");

	}

}
