package features;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ClosableTabComponent extends JPanel {
	private static final long serialVersionUID = 1L;
    public ClosableTabComponent(JTabbedPane tabbedPane, Component tabContent, String title) {
        setOpaque(false);
        setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

        JLabel titleLabel = new JLabel(title + " ");
        JButton closeButton = new JButton("x");

        closeButton.setMargin(new Insets(0, 5, 0, 5));
        closeButton.setBorder(BorderFactory.createEmptyBorder());
        closeButton.setFocusPainted(false);
        closeButton.setContentAreaFilled(false);
        closeButton.setForeground(Color.RED);
        closeButton.setToolTipText("Close this tab");

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = tabbedPane.indexOfComponent(tabContent);
                if (index != -1) {
                    tabbedPane.remove(index);
                }
            }
        });

        add(titleLabel);
        add(closeButton);
    }
}

