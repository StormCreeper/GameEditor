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

        gamePanel = new GameEditorPanel(this, width, height, tileSet, tileMap);

        add(gamePanel, BorderLayout.CENTER);
        add(objectSelectionPanel = new ObjectSelectionPanel(this, tileSet), BorderLayout.SOUTH); 
        add(toolBar = new ToolBar(this), BorderLayout.EAST);
    }

    public int getSelectedTextureId() {
        return objectSelectionPanel.getSelectedTextureId();
    }

    public void addLine() {
        tileMap.addLine();
    }

    public void addColumn() {
        tileMap.addColumn();
    }

    public void centerView() {
        gamePanel.centerView();
    }

}
