package editor;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class ToolBar extends JPanel {

    private final JButton addLineButton = new JButton("Add Line");
    private final JButton addColumnButton = new JButton("Add Column");
    private final JButton centerViewButton = new JButton("Center");

    private final JRadioButton layer1RadioButton = new JRadioButton("Layer 1");
    private final JRadioButton layer2RadioButton = new JRadioButton("Layer 2");
    private final JRadioButton layer3RadioButton = new JRadioButton("Layer 3");

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
        this.add(addLineButton, gbc);
        gbc.gridx = 1;
        this.add(addColumnButton, gbc);
        gbc.gridx = 2;
        this.add(centerViewButton, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        this.add(new JLabel("Right click drag to pan around"), gbc);

        gbc.gridy = 2;
        this.add(layer1RadioButton, gbc);
        gbc.gridy = 3;
        this.add(layer2RadioButton, gbc);
        gbc.gridy = 4;
        gbc.weighty = 1;
        this.add(layer3RadioButton, gbc);

        ButtonGroup group = new ButtonGroup();
        group.add(layer1RadioButton);
        group.add(layer2RadioButton);
        group.add(layer3RadioButton);
    }

    public int getSelectedLayer() {
        if(layer1RadioButton.isSelected()) return 0;
        if(layer1RadioButton.isSelected()) return 1;
        if(layer1RadioButton.isSelected()) return 2;
        return -1; // Should not happen
    }
}
