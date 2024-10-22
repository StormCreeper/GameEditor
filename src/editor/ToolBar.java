package editor;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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
    private final JButton centerViewButton = new JButton("Center");

    private final JRadioButton layer1RadioButton = new JRadioButton("Layer 1");
    private final JRadioButton layer2RadioButton = new JRadioButton("Layer 2");
    private final JRadioButton layer3RadioButton = new JRadioButton("Layer 3");

    private int layer = 0;

    private final JCheckBox automaticFillingCheckBox = new JCheckBox("Automatic filling");

    private final ArrayList<ActionListener> layerChangeListeners = new ArrayList<>();

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

        newEmptyMapButton.addActionListener(e -> {
            JTextField xField = new JTextField(5);
            JTextField yField = new JTextField(5);

            JPanel myPanel = new JPanel();
            myPanel.add(new JLabel("width:"));
            myPanel.add(xField);
            myPanel.add(Box.createHorizontalStrut(15)); // a spacer
            myPanel.add(new JLabel("height:"));
            myPanel.add(yField);
            int response = JOptionPane.showConfirmDialog(null, myPanel, "Please Enter X and Y Values", JOptionPane.OK_CANCEL_OPTION);
            if(response==JOptionPane.OK_OPTION){
                parent.newEmptyMap(Integer.parseInt(xField.getText()), Integer.parseInt(yField.getText()));
            } 
            
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
        this.add(newEmptyMapButton, gbc);

        gbc.gridy = 2;
        layer1RadioButton.setSelected(true);
        this.add(layer1RadioButton, gbc);
        gbc.gridy = 3;
        this.add(layer2RadioButton, gbc);
        gbc.gridy = 4;
        this.add(layer3RadioButton, gbc);

        ButtonGroup group = new ButtonGroup();
        group.add(layer1RadioButton);
        group.add(layer2RadioButton);
        group.add(layer3RadioButton);

        layer1RadioButton.addActionListener(e -> changeLayer());
        layer2RadioButton.addActionListener(e -> changeLayer());
        layer3RadioButton.addActionListener(e -> changeLayer());

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.weightx = 3;
        gbc.weighty = 1;
        this.add(automaticFillingCheckBox, gbc);

        automaticFillingCheckBox.addActionListener(e -> parent.setAutomaticFilling(automaticFillingCheckBox.isSelected()));
        
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
