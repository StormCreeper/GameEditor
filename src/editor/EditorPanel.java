package editor;

import game.Tileset;
import java.awt.BorderLayout;
import javax.swing.JPanel;

public class EditorPanel extends JPanel {

    private GameEditorPanel gamePanel;
    private ObjectSelectionPanel objectSelectionPanel;
    private ToolBar toolBar;

    private Tileset tileSet;

    public EditorPanel(int width, int height) {
        super();
        setLayout(new BorderLayout());

        tileSet = new Tileset(16,7);
        tileSet.loadTextures();

        add(gamePanel = new GameEditorPanel(width, height, tileSet), BorderLayout.CENTER);
        add(objectSelectionPanel = new ObjectSelectionPanel(tileSet), BorderLayout.SOUTH); // 7 : nb of textures
        add(toolBar = new ToolBar(), BorderLayout.EAST);
    }

}
