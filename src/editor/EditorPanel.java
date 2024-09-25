package editor;

import java.awt.BorderLayout;
import javax.swing.JPanel;

public class EditorPanel extends JPanel {

    private GameEditorPanel gamePanel;
    private ObjectSelectionPanel objectSelectionPanel;
    private ToolBar toolBar;

    public EditorPanel() {
        setLayout(new BorderLayout());

        add(gamePanel = new GameEditorPanel(), BorderLayout.CENTER);
        add(objectSelectionPanel = new ObjectSelectionPanel(), BorderLayout.SOUTH);
        add(toolBar = new ToolBar(), BorderLayout.EAST);
    }

}
