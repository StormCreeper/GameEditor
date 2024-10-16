package editor;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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

    private int layer = 0;


    private ArrayList<ActionListener> layerChangeListeners = new ArrayList<ActionListener>();

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
        layer1RadioButton.setSelected(true);
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

        layer1RadioButton.addActionListener(e -> changeLayer());
        layer2RadioButton.addActionListener(e -> changeLayer());
        layer3RadioButton.addActionListener(e -> changeLayer());
    }

    public int getSelectedLayer() {
        if(layer1RadioButton.isSelected()) return 0;
        if(layer2RadioButton.isSelected()) return 1;
        if(layer3RadioButton.isSelected()) return 2;
        return -1; // Should not happen
    }

    public void addLayerChangeListener(ActionListener listener) {
        layerChangeListeners.add(listener);
    }

    private void changeLayer() {
        int newLayer = getSelectedLayer();

        if(newLayer != layer) {
            layer = newLayer;
            for(ActionListener listener : layerChangeListeners) {
                listener.actionPerformed(new ActionEvent(this, 
                                                         (int)System.currentTimeMillis(),
                                                          "layerChanged"));
            }
        }
    }
}
