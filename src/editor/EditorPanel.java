package editor;

import game.Tile.Type;
import game.Tilemap;
import game.Tileset;
import java.awt.BorderLayout;
import javax.swing.JPanel;

public class EditorPanel extends JPanel {

    private final GameEditorPanel gamePanel;
    private ObjectSelectionPanel objectSelectionPanel;
    private final ToolBar toolBar;
    private final Tilemap tileMap;

    public EditorPanel(int width, int height, Tileset tileSet, Tilemap tileMap) {
        super();
        setLayout(new BorderLayout());

        this.tileMap = tileMap;

        gamePanel = new GameEditorPanel(this, width, height, tileSet, tileMap);

        add(gamePanel, BorderLayout.CENTER);
        add(objectSelectionPanel = new ObjectSelectionPanel(this, tileSet), BorderLayout.SOUTH); 
        add(toolBar = new ToolBar(this), BorderLayout.EAST);

        toolBar.addLayerChangeListener(e -> {
            objectSelectionPanel.replaceButtons();
            repaint();
        });
    }

    public int getSelectedLayer() {
        if(toolBar == null) return 0;
        return toolBar.getSelectedLayer();
    }

    public Type getSelectedType() {
        return objectSelectionPanel.getSelectedType();
    }

    public int getSelectedTool() {
        return objectSelectionPanel.getSelectedTool();
    }

    public void addLine() {
        tileMap.addLine();
        tileMap.change();
        gamePanel.repaint();
    }

    public void addColumn() {
        tileMap.addColumn();
        tileMap.change();
        gamePanel.repaint();
    }

    public void centerView() {
        gamePanel.centerView();
    }

    public void newEmptyMap(int width, int height) {
        tileMap.newEmptyMap(width, height);
        tileMap.change();
        gamePanel.repaint();
    }


    public void setAutomaticFilling(boolean b){
        gamePanel.setAutomaticFilling(b);
    }

}
