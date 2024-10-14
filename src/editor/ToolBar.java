package editor;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ToolBar extends JPanel {

    private final JButton addLineButton = new JButton("Add Line");
    private final JButton addColumnButton = new JButton("Add Column");
    private final JButton centerViewButton = new JButton("Center");

    ToolBar(EditorPanel parent) {
        addLineButton.addActionListener(e -> {
            parent.addLine();
        });

        addColumnButton.addActionListener(e -> {
            parent.addColumn();
        });

        centerViewButton.addActionListener(e -> {
            parent.centerView();
        });

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.weighty = 1;
        this.add(addLineButton, gbc);
        gbc.gridx = 1;
        this.add(addColumnButton, gbc);
        gbc.gridx = 2;
        this.add(centerViewButton, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        this.add(new JLabel("Right click drag to pan around"), gbc);
    }
}
