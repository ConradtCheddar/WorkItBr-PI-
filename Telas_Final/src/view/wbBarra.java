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

public class wbBarra extends JPanel {

	private static final long serialVersionUID = 1L;

	JPanel wbPanel;
	private JLabel lblBarra;
	JLabel lblVoltar;
	JLabel lblTitulo;
	private boolean backEnabled = true;
	private boolean menuEnabled = true;
	private boolean resizeListenersEnabled = false; // Flag para controlar listeners de resize

	public wbBarra() {

		setPreferredSize(new Dimension(900, 85));
		setBackground(new Color(0, 102, 204));
		setBorder(new EmptyBorder(0, 0, 0, 0));
		setLayout(new MigLayout("fill", "[grow]", "[grow]"));

		lblVoltar = new JLabel();
		lblVoltar.setHorizontalAlignment(SwingConstants.LEFT);
		lblVoltar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblVoltar.setEnabled(true);

		ImageIcon menuIcon = new ImageIcon(getClass().getResource("/imagens/Casa.png"));
		Image img = menuIcon.getImage();
		Image scaled = img.getScaledInstance(32, 32, Image.SCALE_SMOOTH);
		lblVoltar.setIcon(new ImageIcon(scaled));
		lblVoltar.setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 4, 4, 4));
		// Dimensões iniciais - serão ajustadas dinamicamente pelo ajustarIcones()
		add(lblVoltar, "flowx,cell 0 0,alignx left,aligny center");

		lblVoltar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				if (backEnabled) lblVoltar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				lblVoltar.setCursor(Cursor.getDefaultCursor());
			}
		});

		lblVoltar.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				if (backEnabled) lblVoltar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
		});

		this.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				java.awt.Point p = e.getPoint();
				java.awt.Rectangle r = lblVoltar.getBounds();
				if (r.contains(p)) {
					if (backEnabled) setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				} else {
					setCursor(Cursor.getDefaultCursor());
				}
			}
		});

		lblTitulo = new JLabel("WorkITBr");
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblTitulo.setForeground(Color.WHITE);
		add(lblTitulo, "cell 1 0,grow");

		setLblBarra(new JLabel());
		getLblBarra().setHorizontalAlignment(SwingConstants.RIGHT);
		getLblBarra().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		ImageIcon barraIcon = new ImageIcon(getClass().getResource("/imagens/MenuBarra.png"));
		Image imgBarra = barraIcon.getImage();
		Image scaledBarra = imgBarra.getScaledInstance(64, 40, Image.SCALE_SMOOTH);
		getLblBarra().setIcon(new ImageIcon(scaledBarra));
		add(getLblBarra(), "cell 2 0,alignx right,growy");


		ajustarIcones();
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				if (!resizeListenersEnabled) return; // Ignora durante inicialização
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
				if (!resizeListenersEnabled) return; // Ignora durante inicialização
				int panelHeight = getHeight();
				int fontSize = Math.max(15, panelHeight / 17);
				int fontSize2 = Math.max(15, panelHeight / 40);
				lblTitulo.setFont(new Font("Tahoma", Font.PLAIN, fontSize));

				ajustarFonte();

			}
		});

	}
	
	/**
	 * Ativa os listeners de redimensionamento após a inicialização completa.
	 * Isso evita múltiplos redimensionamentos durante o carregamento inicial.
	 */
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
			@Override public void mousePressed(java.awt.event.MouseEvent e) { listener.mousePressed(e); }
			@Override public void mouseReleased(java.awt.event.MouseEvent e) { listener.mouseReleased(e); }
			@Override public void mouseEntered(java.awt.event.MouseEvent e) { listener.mouseEntered(e); }
			@Override public void mouseExited(java.awt.event.MouseEvent e) { listener.mouseExited(e); }
		});
	}

	public void setBackEnabled(boolean enabled) {
		this.backEnabled = enabled;
		this.lblVoltar.setCursor(enabled ? Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
				: Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		// Aplica efeito visual de desabilitado (opacidade reduzida) igual ao menu
		this.lblVoltar.setEnabled(enabled);
		this.lblVoltar.setVisible(true);
		this.lblVoltar.setOpaque(false);
		this.repaint();
	}

	public void setMenuEnabled(boolean enabled) {
		this.menuEnabled = enabled;
		this.lblBarra.setCursor(enabled ? Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
				: Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		// Aplica efeito visual de desabilitado (opacidade reduzida)
		this.lblBarra.setEnabled(enabled);
		this.repaint();
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
		
		// Redimensionar a área do lblVoltar dinamicamente
		int padding = Math.max(4, w / 200); // Padding proporcional ao tamanho
		lblVoltar.setBorder(javax.swing.BorderFactory.createEmptyBorder(padding, padding, padding, padding));
		java.awt.Dimension hitArea = new java.awt.Dimension(
			larguraCasa + (padding * 2), 
			alturaCasa + (padding * 2)
		);
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

}