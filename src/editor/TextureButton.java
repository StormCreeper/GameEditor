package editor;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.JToggleButton;

public class TextureButton extends JToggleButton{
    private final ObjectSelectionPanel objectSelectionPanel;

    private int textureId;
    private BufferedImage image;

    public TextureButton(ObjectSelectionPanel panel, int id, BufferedImage image){
        super();
        setPreferredSize(new Dimension(100,100));

        objectSelectionPanel = panel;

        textureId = id;
        this.image = image;

        addActionListener((ActionEvent e) -> {
            objectSelectionPanel.setSelectedTextureId(id);
        });
    }

    @Override
    public void paintComponent(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.image, 0, 0, getWidth(), getHeight(), null);
        if(isSelected()){
            g2d.setPaint(Color.BLACK);
            g2d.setStroke(new BasicStroke(5.0f));
            g2d.draw(getVisibleRect());
        }
    }    

}
