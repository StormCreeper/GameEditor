package editor;

import game.Tile;
import game.Tile.Type;
import game.Tileset;
import java.awt.Color;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.*;
/**
 * The panel where you can select the texture to draw
 */
public class ObjectSelectionPanel extends JPanel {
    // The parent editor panel
    private final EditorPanel parent;

    // the tileset
    private final Tileset tileSet;

    // The buttons to select textures
    private final ArrayList<TextureButton> textureButtons = new ArrayList<>();

    // The possible textures for each layer
    private final ArrayList<ArrayList<Integer>> layerElements =  new ArrayList<>();

    // The current selected tool
    private int tool = 0;

    public ObjectSelectionPanel(EditorPanel parent, Tileset tileSet){
        super();
        this.parent = parent;
        setLayout(new FlowLayout());

        layerElements.add(new ArrayList<>());
        layerElements.get(0).addAll(Arrays.asList(5, 13, 21));

        layerElements.add(new ArrayList<>());
        layerElements.get(1).addAll(Arrays.asList(29, 34, 35));

        layerElements.add(new ArrayList<>());
        layerElements.get(2).addAll(Arrays.asList(30, 31));
        
        
        this.tileSet = tileSet;

        setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 2, true));
        setBackground(Color.WHITE);

        replaceButtons();
    }

    /**
     * Replace the buttons with the textures of the selected layer
     */
    public void replaceButtons() {
        int layer = parent.getSelectedLayer();

        for(TextureButton tb : textureButtons) {
            remove(tb);
        }
        textureButtons.clear();

        for(Integer el : layerElements.get(layer)) {
            TextureButton tb = new TextureButton(this, tileSet.getTexture(el), el);
            textureButtons.add(tb);
            add(tb);
        }

        if(layer != 0) {
            TextureButton tbEraser = new TextureButton(this, tileSet.getTexture(33), -1);
            textureButtons.add(tbEraser);
            add(tbEraser);
        }
        

        repaintButtons();
    }

    /**
     * @return the selected type of tile (ONLY for first layer)
     */
    public Type getSelectedType(){
        if(tool == 5) return Tile.Type.ground;
        if(tool == 13) return Tile.Type.water;
        if(tool == 21) return Tile.Type.lava;

        return Tile.Type.ground;
    }

    /**
     * Set the selected tool
     * @param i the index of the selected tool
     */
    public void setSelectedTool(int i) {
        tool = i;
        repaintButtons();
    }

    /**
     * @return the selected tool
     */
    public int getSelectedTool() {
        return tool;
    }

    /**
     * Repaint all the buttons
     */
    public void repaintButtons(){
        for(TextureButton tb : textureButtons){
            revalidate();
            tb.repaint();
        }
    }

}
