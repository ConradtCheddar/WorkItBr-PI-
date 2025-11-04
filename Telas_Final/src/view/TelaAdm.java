package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;
import util.FontScaler;
import util.FontScaler.FontSize;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;

public class TelaAdm extends JPanel {

	private static final long serialVersionUID = 1L;

	public TelaAdm() {
		setBorder(new EmptyBorder(0, 0, 0, 0));
		setLayout(new MigLayout("fill, insets 0", "[20px][98.00,grow][grow][81.00][-56.00][65.00,grow][][]", "[35px][66.00,grow][grow][][][66]"));
		
		// FontScaler será aplicado quando houver componentes para redimensionar
		// Por enquanto, removido o listener vazio
	}

}