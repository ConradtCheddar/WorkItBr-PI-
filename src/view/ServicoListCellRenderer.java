package view;

import model.Servico;
import javax.swing.*;
import java.awt.*;

public class ServicoListCellRenderer extends JPanel implements ListCellRenderer<Servico> {
    private JLabel lblTitle = new JLabel();
    private JLabel lblModalidade = new JLabel();
    private JLabel lblDesc = new JLabel();
    private JLabel lblValue = new JLabel();
    private JPanel topPanel = new JPanel();
    private JPanel centerPanel = new JPanel();

    public ServicoListCellRenderer() {
        setLayout(new BorderLayout(10, 0));

        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        topPanel.setOpaque(false);
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblModalidade.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblModalidade.setForeground(Color.WHITE);
        lblModalidade.setAlignmentX(Component.LEFT_ALIGNMENT);
        topPanel.add(lblTitle);
        topPanel.add(Box.createHorizontalStrut(12));
        topPanel.add(lblModalidade);
        topPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);
        centerPanel.add(topPanel);
        centerPanel.add(Box.createVerticalStrut(4));
        lblDesc.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblDesc.setForeground(Color.WHITE);
        lblDesc.setAlignmentX(Component.LEFT_ALIGNMENT);
        centerPanel.add(lblDesc);
        centerPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        add(centerPanel, BorderLayout.CENTER);
        lblValue.setForeground(Color.WHITE);

        lblValue.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblValue.setHorizontalAlignment(SwingConstants.RIGHT);
        add(lblValue, BorderLayout.EAST);

        setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Servico> list, Servico value, int index, boolean isSelected, boolean cellHasFocus) {
        lblTitle.setText(value.getNome_Servico());
        lblModalidade.setText(value.getModalidade());
        lblDesc.setText(value.getDescricao());
        lblValue.setText("R$ " + value.getValor());

        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }

        return this;
    }
}
