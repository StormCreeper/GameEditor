package editor;

import game.Tilemap;
import game.Tileset;
import java.awt.BorderLayout;
import javax.swing.JPanel;

public class EditorPanel extends JPanel {

    private GameEditorPanel gamePanel;
    private ObjectSelectionPanel objectSelectionPanel;
    private ToolBar toolBar;

    private Tileset tileSet;
    private Tilemap tileMap;

    public EditorPanel(int width, int height, Tileset tileSet, Tilemap tileMap) {
        super();
        setLayout(new BorderLayout());

        this.tileSet = tileSet;
        this.tileMap = tileMap;

        add(gamePanel = new GameEditorPanel(this, width, height, tileSet, tileMap), BorderLayout.CENTER);
        add(objectSelectionPanel = new ObjectSelectionPanel(this, tileSet), BorderLayout.SOUTH); // 7 : nb of textures
        add(toolBar = new ToolBar(), BorderLayout.EAST);
    }

    public int getSelectedTextureId(){
        return objectSelectionPanel.getSelectedTextureId();
    }

}
