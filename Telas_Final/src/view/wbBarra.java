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

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import controller.PopupController;
import net.miginfocom.swing.MigLayout;

public class wbBarra extends JPanel {

	private static final long serialVersionUID = 1L;

	JPanel wbPanel;
	private JLabel lblBarra;
	JLabel lblMenu;
	JLabel lblNewLabel;

	/**
	 * Create the panel.
	 */
	public wbBarra() {

		setPreferredSize(new Dimension(900, 85));
		setBackground(new Color(0, 102, 204));
		setBorder(new EmptyBorder(0, 0, 0, 0));
		setLayout(new MigLayout("fill", "[grow][grow][grow]", "[grow]"));

		lblMenu = new JLabel();
		lblMenu.setHorizontalAlignment(SwingConstants.LEFT);
		lblMenu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblMenu.setEnabled(true);
		lblMenu.setVisible(true);
		lblMenu.setPreferredSize(new Dimension(48, 40)); // altura menor
		lblMenu.setMinimumSize(new Dimension(32, 32));

		ImageIcon menuIcon = new ImageIcon(getClass().getResource("/imagens/Casa.png"));
		Image img = menuIcon.getImage();
		Image scaled = img.getScaledInstance(32, 32, Image.SCALE_SMOOTH);
		lblMenu.setIcon(new ImageIcon(scaled));
		add(lblMenu, "flowx,cell 0 0,alignx left,growy");

		lblNewLabel = new JLabel("WorkITBr");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblNewLabel.setForeground(Color.WHITE);
		add(lblNewLabel, "cell 1 0,grow");

		setLblBarra(new JLabel());
		getLblBarra().setHorizontalAlignment(SwingConstants.RIGHT);
		getLblBarra().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		getLblBarra().setPreferredSize(new Dimension(96, 48));
		getLblBarra().setMinimumSize(new Dimension(64, 40));
		ImageIcon barraIcon = new ImageIcon(getClass().getResource("/imagens/MenuBarra.png"));
		Image imgBarra = barraIcon.getImage();
		Image scaledBarra = imgBarra.getScaledInstance(64, 40, Image.SCALE_SMOOTH);
		getLblBarra().setIcon(new ImageIcon(scaledBarra));
		add(getLblBarra(), "cell 2 0,alignx right,growy,gapright 15px");


		ajustarIcones();
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				ajustarIcones();
				ImageIcon barraIcon = new ImageIcon(getClass().getResource("/imagens/MenuBarra.png"));
				Image imgbarra = barraIcon.getImage();
				Image scaledBarra = imgbarra.getScaledInstance(getWidth() / 40, getHeight() * 2 / 4,
						Image.SCALE_SMOOTH);
				getLblBarra().setIcon(new ImageIcon(scaledBarra));
			}
		});

		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				int panelHeight = getHeight();
				int fontSize = Math.max(15, panelHeight / 17);
				int fontSize2 = Math.max(15, panelHeight / 40);
				lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, fontSize));

				ajustarFonte();

			}
		});

	}

	@Override
	public void addNotify() {
		super.addNotify();
		ajustarIcones();
	}

	public void barra(MouseListener actionListener) {
		this.getLblBarra().addMouseListener(actionListener);
	}

	public void menu(MouseAdapter mouseAdapter) {
		this.lblMenu.addMouseListener(mouseAdapter);
	}

	public void setMenuClickListener(MouseListener listener) {
        for (MouseListener ml : getLblBarra().getMouseListeners()) {
            getLblBarra().removeMouseListener(ml);
        }
        getLblBarra().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                System.out.println("[DEBUG] Clique detectado no lblBarra");
                listener.mouseClicked(e);
            }
            @Override public void mousePressed(java.awt.event.MouseEvent e) { listener.mousePressed(e); }
            @Override public void mouseReleased(java.awt.event.MouseEvent e) { listener.mouseReleased(e); }
            @Override public void mouseEntered(java.awt.event.MouseEvent e) { listener.mouseEntered(e); }
            @Override public void mouseExited(java.awt.event.MouseEvent e) { listener.mouseExited(e); }
        });
    }

	public void ajustarFonte() {
		int w = getWidth();
		if (w <= 0)
			return;

		float novoTamanho = Math.max(12f, w / 40f);

		lblNewLabel.setFont(lblNewLabel.getFont().deriveFont(novoTamanho));
		revalidate();
		repaint();
	}

	public void ajustarIcones() {
		int w = getWidth() > 0 ? getWidth() : 900;
		int h = getHeight() > 0 ? getHeight() : 85;
		int largura = Math.max(64, w / 25);
		int altura = Math.max(40, h * 2 / 5); 

		int larguraCasa = Math.max(32, w / 45);
		int alturaCasa = Math.max(32, Math.min(40, h * 2 / 3));

		ImageIcon menuIcon = new ImageIcon(getClass().getResource("/imagens/Casa.png"));
		Image img = menuIcon.getImage();
		Image scaled = img.getScaledInstance(larguraCasa, alturaCasa, Image.SCALE_SMOOTH);

		ImageIcon barraIcon = new ImageIcon(getClass().getResource("/imagens/MenuBarra.png"));
		Image imgBarra = barraIcon.getImage();
		Image scaledBarra = imgBarra.getScaledInstance(largura, altura, Image.SCALE_SMOOTH);

		lblMenu.setIcon(new ImageIcon(scaled));
		lblBarra.setIcon(new ImageIcon(scaledBarra));
	}

	public JLabel getLblBarra() {
		return lblBarra;
	}

	public void setLblBarra(JLabel lblBarra) {
		this.lblBarra = lblBarra;
	}

}