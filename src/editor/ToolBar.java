package editor;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class ToolBar extends JPanel {
    private final JButton addLineButton = new JButton("Add Line");
    private final JButton addColumnButton = new JButton("Add Column");
    private final JButton newEmptyMapButton = new JButton("New Empty Map");
    private final JButton centerViewButton = new JButton("Reset view");

    private final JRadioButton layer1RadioButton = new JRadioButton("Layer 1");
    private final JRadioButton layer2RadioButton = new JRadioButton("Layer 2");
    private final JRadioButton layer3RadioButton = new JRadioButton("Layer 3");

    private int layer = 0;

    private final JCheckBox automaticFillingCheckBox = new JCheckBox("Automatic filling");

    private final ArrayList<ActionListener> layerChangeListeners = new ArrayList<>();

    ToolBar(EditorPanel parent) {
        addLineButton.setBackground(Color.WHITE);
        addLineButton.addActionListener(e -> {
            parent.addLine();
        });

        addColumnButton.setBackground(Color.WHITE);
        addColumnButton.addActionListener(e -> {
            parent.addColumn();
        });

        centerViewButton.setBackground(Color.WHITE);
        centerViewButton.addActionListener(e -> {
            parent.centerView();
        });

        newEmptyMapButton.setBackground(Color.WHITE);
        newEmptyMapButton.addActionListener(e -> {
            JTextField xField = new JTextField(5);
            JTextField yField = new JTextField(5);

            // A JOptionPane to get the width and height of the new map asked by the user
            JPanel myPanel = new JPanel();
            myPanel.add(new JLabel("width:"));
            myPanel.add(xField);
            myPanel.add(Box.createHorizontalStrut(15)); // a spacer
            myPanel.add(new JLabel("height:"));
            myPanel.add(yField);
            int response = JOptionPane.showConfirmDialog(null, myPanel, "New map :", JOptionPane.OK_CANCEL_OPTION);
            if(response==JOptionPane.OK_OPTION){
                parent.newEmptyMap(Integer.parseInt(xField.getText()), Integer.parseInt(yField.getText()));
            } 
            
        });

        ButtonGroup group = new ButtonGroup();
        layer1RadioButton.setBackground(Color.WHITE);
        layer2RadioButton.setBackground(Color.WHITE);
        layer3RadioButton.setBackground(Color.WHITE);
        group.add(layer1RadioButton);
        group.add(layer2RadioButton);
        group.add(layer3RadioButton);

        layer1RadioButton.addActionListener(e -> changeLayer());
        layer2RadioButton.addActionListener(e -> changeLayer());
        layer3RadioButton.addActionListener(e -> changeLayer());

        automaticFillingCheckBox.setBackground(Color.WHITE);
        automaticFillingCheckBox.addActionListener(e -> parent.setAutomaticFilling(automaticFillingCheckBox.isSelected()));

        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new java.awt.Insets(5, 5, 5, 5); // Set horizontal and vertical gaps

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        
        this.add(new JLabel(""), gbc);

        gbc.gridy = 1;
        gbc.weightx = 1;
        gbc.gridx = 0;
        this.add(addLineButton, gbc);
        gbc.gridx = 1;
        this.add(addColumnButton, gbc);

        gbc.gridx = 0;
        gbc.gridwidth = 2;

        gbc.gridy = 2;
        this.add(new JLabel(""), gbc);
        gbc.gridy = 3;
        this.add(newEmptyMapButton, gbc);

        gbc.gridy = 4;
        this.add(new JLabel("Select the layer to modify :"), gbc);

        gbc.gridy = 5;
        layer1RadioButton.setSelected(true);
        this.add(layer1RadioButton, gbc);
        gbc.gridy = 6;
        this.add(layer2RadioButton, gbc);
        gbc.gridy = 7;
        this.add(layer3RadioButton, gbc);

        gbc.gridy = 8;
        this.add(new JLabel("Fill interior automatically :"), gbc);

        gbc.gridy = 9;
        this.add(automaticFillingCheckBox, gbc);

        gbc.gridy = 10;
        gbc.weighty = 1;
        this.add(centerViewButton, gbc);

        setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 2, true));
        setBackground(Color.WHITE);
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
