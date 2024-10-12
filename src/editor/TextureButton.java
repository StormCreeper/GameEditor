package editor;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.JToggleButton;

public class TextureButton extends JToggleButton{
    private final ObjectSelectionPanel objectSelectionPanel;

    private int textureId;
    private BufferedImage image;

    public TextureButton(ObjectSelectionPanel panel, int id){
        super();
        setPreferredSize(new Dimension(100,100));

        objectSelectionPanel = panel;

        setTexture(id);

        addActionListener((ActionEvent e) -> {
            objectSelectionPanel.setSelectedTextureId(id);
        });
    }

    private void setTexture(int id){
        textureId = id;
        image = objectSelectionPanel.getTileSet().getTexture(id);
    }

    public boolean isCurrentTexture(){
        return objectSelectionPanel.getSelectedTextureId() == textureId;
    }

    @Override
    public void paintComponent(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        int strokeSize = 2;
        if (isCurrentTexture()) g2d.setColor(Color.BLACK);
        else g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        g2d.drawImage(this.image, strokeSize, strokeSize, getWidth()-2*strokeSize, getHeight()-2*strokeSize, null);
    }    

}
