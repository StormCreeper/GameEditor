package editor;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ToolBar extends JPanel {

    private final EditorPanel parent;

    private JButton addLineButton = new JButton("Add Line");
    private JButton addColumnButton = new JButton("Add Column");

    ToolBar(EditorPanel parent) {
        this.parent = parent;
    }
}
