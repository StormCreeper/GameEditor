package editor;

import game.Tileset;
import java.awt.FlowLayout;
import java.util.ArrayList;
import javax.swing.*;

public class ObjectSelectionPanel extends JPanel {
    private final EditorPanel parent;

    private Tileset tileSet;
    private int selectedTextureId = 0;
    private ArrayList<TextureButton> textureButtons = new ArrayList<>();
    

    public ObjectSelectionPanel(EditorPanel parent, Tileset tileSet){
        super();
        setLayout(new FlowLayout());

        this.parent = parent;

        this.tileSet = tileSet;

        for(int i=0; i<tileSet.getNbTextures(); i++){
            TextureButton tb = new TextureButton(this, i, tileSet.getTexture(i));
            textureButtons.add(tb);
            add(tb);
        }
    }

    public int getSelectedTextureId(){
        return selectedTextureId;
    }

    public void setSelectedTextureId(int id){
        selectedTextureId = id;
        System.out.println("Texture selected : " + selectedTextureId);
        updateButtons();
    }

    private void updateButtons(){
        for(int i=0; i<tileSet.getNbTextures(); i++){
            textureButtons.get(i).setSelected(false);
        }
        textureButtons.get(selectedTextureId).setSelected(true);
    }
    

}
