package game;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Character {
    
    private float x;
    private float y;

    private float velX;
    private float velY;

    private BufferedImage image;

    public Character(String imagePath) {
        try {
            image = ImageIO.read(getClass().getResource(imagePath));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        x = 0;
        y = 0;
        velX = 0;
        velY = 0;
    }

    public void drawSelf(Graphics2D g) {
        g.drawImage(image, (int)x, (int)y, 100, 100, null);
    }
    

}
