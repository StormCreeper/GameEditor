package editor;

import game.Tileset;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.*;

public class ObjectSelectionPanel extends JPanel {
    private final EditorPanel parent;

    private final Tileset tileSet;
    private int selectedTextureId;
    private final ArrayList<TextureButton> textureButtons = new ArrayList<>();    

    public ObjectSelectionPanel(EditorPanel parent, Tileset tileSet){
        super();
        setLayout(new GridLayout(2,0,5,5));
        
        this.parent = parent;

        this.tileSet = tileSet;

        this.selectedTextureId = 0;

        for(int i=0; i<tileSet.getNbTextures(); i++){
            TextureButton tb = new TextureButton(this, i);
            textureButtons.add(tb);
            add(tb);
        }
    }

    public int getSelectedTextureId(){
        return selectedTextureId;
    }

    public void setSelectedTextureId(int id){
        selectedTextureId = id;
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
