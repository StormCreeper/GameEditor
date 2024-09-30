package editor;

import game.Tileset;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class ObjectSelectionPanel extends JPanel {
    private Tileset tileSet;
    

    public ObjectSelectionPanel(int nbTextures){
        super();
        setLayout(new FlowLayout());

        tileSet = new Tileset(16,nbTextures);
        tileSet.loadTextures();

        for(BufferedImage image : tileSet.getTextureArray()){
            JButton texButton = new JButton(new ImageIcon(image));
            add(texButton);
        }
    }

    

}
