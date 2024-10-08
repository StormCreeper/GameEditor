package editor;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ToolBar extends JPanel {

    private final EditorPanel parent;

    private final JButton addLineButton = new JButton("Add Line");
    private final JButton addColumnButton = new JButton("Add Column");
    private final JButton centerViewButton = new JButton("Center");

    ToolBar(
            EditorPanel parent) {
        this.parent = parent;

        addLineButton.addActionListener(e -> {
            parent.addLine();
            parent.repaint();
        });

        addColumnButton.addActionListener(e -> {
            parent.addColumn();
            parent.repaint();
        });

        centerViewButton.addActionListener(e -> {
            parent.centerView();
        });

        this.add(addLineButton);
        this.add(addColumnButton);
        this.add(centerViewButton);
    }
}
