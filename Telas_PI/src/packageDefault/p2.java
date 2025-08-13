package packageDefault;

import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class p2 extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField textField;

	/**
	 * Create the panel.
	 */
	public p2(Seila j) {
		setLayout(new MigLayout("", "[][][][grow][][][][]", "[][][][][][][]"));
		
		textField = new JTextField();
		add(textField, "cell 3 3,growx");
		textField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("New label");
		add(lblNewLabel, "cell 7 4");
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPanel p1 = new p1(j);
				j.atualizarPainel(p1);
			}
		});
		add(btnNewButton, "cell 3 6");

	}

}
