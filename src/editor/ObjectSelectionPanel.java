package editor;

import game.Tileset;
import java.awt.FlowLayout;
import java.util.ArrayList;
import javax.swing.*;

public class ObjectSelectionPanel extends JPanel {
    private Tileset tileSet;
    private int selectedTextureId = 0;
    private ArrayList<TextureButton> textureButtons = new ArrayList<>();
    

    public ObjectSelectionPanel(int nbTextures){
        super();
        setLayout(new FlowLayout());

        tileSet = new Tileset(16,nbTextures);
        tileSet.loadTextures();

        for(int i=0; i<tileSet.getNbTextures(); i++){
            TextureButton tb = new TextureButton(this, i, tileSet.getTexture(i));
            textureButtons.add(tb);
            add(tb);
        }
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
