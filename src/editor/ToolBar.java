package editor;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ToolBar extends JPanel {

    private final JButton addLineButton = new JButton("Add Line");
    private final JButton addColumnButton = new JButton("Add Column");
    private final JButton centerViewButton = new JButton("Center");

    ToolBar(
            EditorPanel parent) {
        addLineButton.addActionListener(e -> {
            parent.addLine();
        });

        addColumnButton.addActionListener(e -> {
            parent.addColumn();
        });

        centerViewButton.addActionListener(e -> {
            parent.centerView();
        });

        this.add(addLineButton);
        this.add(addColumnButton);
        this.add(centerViewButton);
    }
}
