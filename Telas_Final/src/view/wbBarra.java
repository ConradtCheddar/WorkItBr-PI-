package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import controller.PopupController;
import net.miginfocom.swing.MigLayout;
import util.FontScaler;
import util.FontScaler.FontSize;

public class wbBarra extends JPanel {

	private static final long serialVersionUID = 1L;

	JPanel wbPanel;
	private JLabel lblBarra;
	JLabel lblVoltar;
	JLabel lblTitulo;
	private boolean backEnabled = true;
	private boolean menuEnabled = true;
	private boolean resizeListenersEnabled = false;

	public wbBarra() {

		setPreferredSize(new Dimension(900, 85));
		setBackground(new Color(0, 102, 204));
		setBorder(new EmptyBorder(0, 0, 0, 0));
		setLayout(new MigLayout("fill", "[80:80:80][grow,fill][80:80:80]", "[grow]"));

		lblVoltar = new JLabel();
		lblVoltar.setHorizontalAlignment(SwingConstants.LEFT);
		lblVoltar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblVoltar.setEnabled(true);

		ImageIcon menuIcon = new ImageIcon(getClass().getResource("/imagens/Casa.png"));
		Image img = menuIcon.getImage();
		Image scaled = img.getScaledInstance(32, 32, Image.SCALE_SMOOTH);
		lblVoltar.setIcon(new ImageIcon(scaled));
		lblVoltar.setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 4, 4, 4));
		add(lblVoltar, "cell 0 0,alignx left,aligny center");

		lblVoltar.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				if (backEnabled && lblVoltar.isVisible()) {
					lblVoltar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				}
			}
		});

		this.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				java.awt.Point p = e.getPoint();
				java.awt.Rectangle r = lblVoltar.getBounds();
				if (r.contains(p)) {
					if (backEnabled && lblVoltar.isVisible()) {
						setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					}
				} else {
					setCursor(Cursor.getDefaultCursor());
				}
			}
		});

		lblTitulo = new JLabel("WorkITBr");
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setForeground(Color.WHITE);
		add(lblTitulo, "cell 1 0,growx,aligny center");

		setLblBarra(new JLabel());
		getLblBarra().setHorizontalAlignment(SwingConstants.RIGHT);
		getLblBarra().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		ImageIcon barraIcon = new ImageIcon(getClass().getResource("/imagens/MenuBarra.png"));
		Image imgBarra = barraIcon.getImage();
		Image scaledBarra = imgBarra.getScaledInstance(64, 40, Image.SCALE_SMOOTH);
		getLblBarra().setIcon(new ImageIcon(scaledBarra));
		add(getLblBarra(), "cell 2 0,alignx right,aligny center");

		ajustarIcones();
		
		FontScaler.addAutoResizeWithCallback(this, 
			() -> {
				if (resizeListenersEnabled) {
					ajustarIcones();
				}
			},
			new Object[] { lblTitulo, FontSize.BARRA_TITULO }
		);
	}

	public void habilitarResizeListeners() {
		this.resizeListenersEnabled = true;
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
		this.lblVoltar.addMouseListener(mouseAdapter);
	}

	public void setMenuClickListener(MouseListener listener) {
		for (MouseListener ml : getLblBarra().getMouseListeners()) {
			getLblBarra().removeMouseListener(ml);
		}
		getLblBarra().addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent e) {
				listener.mouseClicked(e);
			}

			@Override
			public void mousePressed(java.awt.event.MouseEvent e) {
				listener.mousePressed(e);
			}

			@Override
			public void mouseReleased(java.awt.event.MouseEvent e) {
				listener.mouseReleased(e);
			}

			@Override
			public void mouseEntered(java.awt.event.MouseEvent e) {
				listener.mouseEntered(e);
			}

			@Override
			public void mouseExited(java.awt.event.MouseEvent e) {
				listener.mouseExited(e);
			}
		});
	}

	public void setBackEnabled(boolean enabled) {
		this.backEnabled = enabled;
		this.lblVoltar.setVisible(enabled);
		if (enabled) {
			this.lblVoltar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		}
	}

	public void setMenuEnabled(boolean enabled) {
		this.menuEnabled = enabled;
		this.lblBarra.setVisible(enabled);
		if (enabled) {
			this.lblBarra.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		}
	}

	public void ajustarFonte() {
		int w = getWidth();
		if (w <= 0)
			return;

		float novoTamanho = Math.max(12f, w / 40f);

		lblTitulo.setFont(lblTitulo.getFont().deriveFont(novoTamanho));
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

		ImageIcon menuIcon = new ImageIcon(getClass().getResource("/imagens/Seta-retorno.png"));
		Image img = menuIcon.getImage();
		Image scaled = img.getScaledInstance(larguraCasa, alturaCasa, Image.SCALE_SMOOTH);

		ImageIcon barraIcon = new ImageIcon(getClass().getResource("/imagens/MenuBarra.png"));
		Image imgBarra = barraIcon.getImage();
		Image scaledBarra = imgBarra.getScaledInstance(largura, altura, Image.SCALE_SMOOTH);

		lblVoltar.setIcon(new ImageIcon(scaled));
		lblBarra.setIcon(new ImageIcon(scaledBarra));

		int padding = Math.max(4, w / 200);
		lblVoltar.setBorder(javax.swing.BorderFactory.createEmptyBorder(padding, padding, padding, padding));
		java.awt.Dimension hitArea = new java.awt.Dimension(larguraCasa + (padding * 2), alturaCasa + (padding * 2));
		lblVoltar.setPreferredSize(hitArea);
		lblVoltar.setMinimumSize(hitArea);
		lblVoltar.setMaximumSize(hitArea);
	}

	public JLabel getLblBarra() {
		return lblBarra;
	}

	public void setLblBarra(JLabel lblBarra) {
		this.lblBarra = lblBarra;
	}

	public JLabel getLblVoltar() {
		return lblVoltar;
	}

	public void setTitulo(String titulo) {
		this.lblTitulo.setText(titulo);
	}

	public String getTitulo() {
		return this.lblTitulo.getText();
	}
}