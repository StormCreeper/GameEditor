package editor;

import game.Tile;
import game.Tile.Type;
import game.Tileset;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.*;

public class ObjectSelectionPanel extends JPanel {
    private final Tileset tileSet;
    private final ArrayList<TextureButton> textureButtons = new ArrayList<>();

    private final ArrayList<ArrayList<Integer>> layerElements =  new ArrayList<>();

    private EditorPanel parent;

    private int tool = 0;

    public ObjectSelectionPanel(EditorPanel parent, Tileset tileSet){
        super();
        this.parent = parent;
        setLayout(new FlowLayout());

        layerElements.add(new ArrayList<>());
        layerElements.get(0).addAll(Arrays.asList(5, 13, 21));

        layerElements.add(new ArrayList<>());
        layerElements.get(1).addAll(Arrays.asList(29));

        layerElements.add(new ArrayList<>());
        layerElements.get(2).addAll(Arrays.asList(30, 31));
        
        
        this.tileSet = tileSet;

        replaceButtons();
    }

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

        TextureButton tbEraser = new TextureButton(this, tileSet.getTexture(33), -1);
        textureButtons.add(tbEraser);
        add(tbEraser);

        repaintButtons();
    }

    public Type getSelectedType(){
        if(tool == 5) return Tile.Type.ground;
        if(tool == 13) return Tile.Type.water;
        if(tool == 21) return Tile.Type.lava;

        return Tile.Type.ground;
    }

    public void setSelectedTool(int i) {
        tool = i;
        repaintButtons();
    }

    public int getSelectedTool() {
        return tool;
    }

    public void repaintButtons(){
        for(TextureButton tb : textureButtons){
            revalidate();
            tb.repaint();
        }
    }

}
