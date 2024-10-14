package editor;

import game.Tile.Type;
import game.Tileset;
import java.awt.FlowLayout;
import java.util.ArrayList;
import javax.swing.*;

public class ObjectSelectionPanel extends JPanel {
    private final EditorPanel parent;

    private final Tileset tileSet;
    private Type selectedType;
    private final ArrayList<TextureButton> textureButtons = new ArrayList<>();    

    public ObjectSelectionPanel(EditorPanel parent, Tileset tileSet){
        super();
        setLayout(new FlowLayout());
        
        this.parent = parent;

        this.tileSet = tileSet;

        this.selectedType = Type.ground;

        for(Type type : Type.values()){
            TextureButton tb = new TextureButton(this, type);
            textureButtons.add(tb);
            add(tb);
        }
    }

    public Type getSelectedType(){
        return selectedType;
    }

    public void setSelectedType(Type type){
        selectedType = type;
        updateButtons();
    }

    public Tileset getTileSet(){
        return tileSet;
    }

    public void updateButtons(){
        for(TextureButton tb : textureButtons){
            tb.repaint();
        }
    }

}
