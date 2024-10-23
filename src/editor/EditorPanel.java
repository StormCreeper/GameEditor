package editor;

import game.Tile.Type;
import game.Tilemap;
import game.Tileset;
import java.awt.BorderLayout;
import javax.swing.JPanel;

/**
 * The main panel of the editor, containing :
 * - The game panel
 * - The object selection panel
 * - The tool bar
 */
public class EditorPanel extends JPanel {

    private final GameEditorPanel gamePanel;
    private ObjectSelectionPanel objectSelectionPanel;
    private final ToolBar toolBar;
    private final Tilemap tileMap;

    public EditorPanel(Tileset tileSet, Tilemap tileMap) {
        super();
        setLayout(new BorderLayout());

        this.tileMap = tileMap;

        gamePanel = new GameEditorPanel(this, tileSet, tileMap);

        add(gamePanel, BorderLayout.CENTER);
        add(objectSelectionPanel = new ObjectSelectionPanel(this, tileSet), BorderLayout.SOUTH); 
        add(toolBar = new ToolBar(this), BorderLayout.EAST);

        toolBar.addLayerChangeListener(e -> {
            objectSelectionPanel.replaceButtons();
            repaint();
        });
    }

    /**
     * @return the current edition layer
     */
    public int getSelectedLayer() {
        if(toolBar == null) return 0;
        return toolBar.getSelectedLayer();
    }

    /**
     * 
     */
    public Type getSelectedType() {
        return objectSelectionPanel.getSelectedType();
    }

    public int getSelectedTool() {
        return objectSelectionPanel.getSelectedTool();
    }

    /**
     * Add a line to the tile map
     */
    public void addLine() {
        tileMap.addLine();
        tileMap.change();
        gamePanel.repaint();
    }

    /**
     * Add a column to the tile map
     */
    public void addColumn() {
        tileMap.addColumn();
        tileMap.change();
        gamePanel.repaint();
    }

    /**
     * Reset the view of the game editor panel
     */
    public void centerView() {
        gamePanel.centerView();
    }

    /**
     * Replace the tileMap by a new map of the given width and height, covered with floor
     * @param width
     * @param height
     */
    public void newEmptyMap(int width, int height) {
        tileMap.newEmptyMap(width, height);
        tileMap.change();
        gamePanel.repaint();
    }

    /**
     * Set the variable the manage automatic filling
     * @param b
     */
    public void setAutomaticFilling(boolean b){
        gamePanel.setAutomaticFilling(b);
    }

}
