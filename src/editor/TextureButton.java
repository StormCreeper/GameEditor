package editor;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.JToggleButton;

/**
 * A button to select a texture
 */
public class TextureButton extends JToggleButton {
    // The panel containing the button
    private final ObjectSelectionPanel objectSelectionPanel;

    // The image representing the button
    private final BufferedImage image;

    // The tool index associated to the button
    private final int tool;

    public TextureButton(ObjectSelectionPanel panel, BufferedImage image, int tool) { 
        super();
        setPreferredSize(new Dimension(100,100));
        this.image = image;
        this.tool = tool;

        this.objectSelectionPanel = panel;

        addActionListener((ActionEvent e) -> {
            objectSelectionPanel.setSelectedTool(tool);
        });
    }

    /**
     * @return if the button is the current selected button
     */
    public boolean isCurrentTool(){
        return objectSelectionPanel.getSelectedTool() == tool;
    }

    @Override
    public void paintComponent(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        int strokeSize = 2;

        if (isCurrentTool())
            g2d.setColor(Color.BLACK);
        else
            g2d.setColor(Color.WHITE);

        g2d.fillRect(0, 0, getWidth(), getHeight());
        g2d.drawImage(this.image, strokeSize, strokeSize, getWidth()-2*strokeSize, getHeight()-2*strokeSize, null);
    }    

}
