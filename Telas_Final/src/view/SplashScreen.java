package view;

import javax.swing.*;
import java.awt.*;

public class SplashScreen extends JWindow {
	private static final long serialVersionUID = 1L;
	private JProgressBar progressBar;
	private JLabel lblStatus;
	private JLabel lblLogo;

	public SplashScreen() {
		JPanel content = new JPanel(new BorderLayout());
		content.setBackground(new Color(30, 30, 30));
		content.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 204), 3));

		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		centerPanel.setBackground(new Color(30, 30, 30));
		centerPanel.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));

		lblLogo = new JLabel("WorkITBr");
		lblLogo.setFont(new Font("Segoe UI", Font.BOLD, 48));
		lblLogo.setForeground(new Color(0, 102, 204));
		lblLogo.setAlignmentX(Component.CENTER_ALIGNMENT);

		try {
			ImageIcon icon = new ImageIcon(getClass().getResource("/imagens/w.png"));
			Image img = icon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
			JLabel lblIcon = new JLabel(new ImageIcon(img));
			lblIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
			centerPanel.add(lblIcon);
			centerPanel.add(Box.createVerticalStrut(20));
		} catch (Exception e) {
		}

		centerPanel.add(lblLogo);
		centerPanel.add(Box.createVerticalStrut(30));

		progressBar = new JProgressBar(0, 100);
		progressBar.setPreferredSize(new Dimension(400, 25));
		progressBar.setMaximumSize(new Dimension(400, 25));
		progressBar.setStringPainted(true);
		progressBar.setForeground(new Color(0, 102, 204));
		progressBar.setBackground(new Color(50, 50, 50));
		progressBar.setBorderPainted(false);
		progressBar.setAlignmentX(Component.CENTER_ALIGNMENT);
		centerPanel.add(progressBar);

		centerPanel.add(Box.createVerticalStrut(15));

		lblStatus = new JLabel("Inicializando...");
		lblStatus.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblStatus.setForeground(Color.WHITE);
		lblStatus.setAlignmentX(Component.CENTER_ALIGNMENT);
		centerPanel.add(lblStatus);

		content.add(centerPanel, BorderLayout.CENTER);

		JLabel lblVersion = new JLabel("Versão 1.0");
		lblVersion.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		lblVersion.setForeground(Color.GRAY);
		lblVersion.setHorizontalAlignment(SwingConstants.CENTER);
		lblVersion.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
		content.add(lblVersion, BorderLayout.SOUTH);

		setContentPane(content);
		pack();
		setLocationRelativeTo(null);
	}

	public void setProgress(int progress, String status) {
		SwingUtilities.invokeLater(() -> {
			progressBar.setValue(progress);
			lblStatus.setText(status);
		});
	}

	public void closeSplash() {
		closeSplash(300);
	}

	public void closeSplash(int delayMs) {
		SwingUtilities.invokeLater(() -> {
			try {
				Thread.sleep(delayMs);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			setVisible(false);
			dispose();
		});
	}
}