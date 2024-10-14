package editor;

import game.Tile.Type;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.JToggleButton;

public class TextureButton extends JToggleButton{
    private final ObjectSelectionPanel objectSelectionPanel;

    private final Type type;
    private final BufferedImage image;

    public TextureButton(ObjectSelectionPanel panel, Type type){
        super();
        setPreferredSize(new Dimension(100,100));

        this.objectSelectionPanel = panel;

        this.type = type;

        switch(type){
            case ground -> this.image = objectSelectionPanel.getTileSet().getTexture(5);
            case water -> this.image = objectSelectionPanel.getTileSet().getTexture(13);
            case lava -> this.image = objectSelectionPanel.getTileSet().getTexture(21);
            default -> this.image = objectSelectionPanel.getTileSet().getTexture(0);
        }

        addActionListener((ActionEvent e) -> {
            objectSelectionPanel.setSelectedType(type);
        });
    }

    public boolean isCurrentType(){
        return objectSelectionPanel.getSelectedType() == type;
    }

    @Override
    public void paintComponent(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        int strokeSize = 2;
        if (isCurrentType()) g2d.setColor(Color.BLACK);
        else g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        g2d.drawImage(this.image, strokeSize, strokeSize, getWidth()-2*strokeSize, getHeight()-2*strokeSize, null);
    }    

}
