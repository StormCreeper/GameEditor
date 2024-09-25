package game;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Character {

    private float x;
    private float y;

    private float velX;
    private float velY;

    private boolean upPressed;
    private boolean downPressed;
    private boolean leftPressed;
    private boolean rightPressed;

    private BufferedImage image;

    public Character(String imagePath) {
        try {
            image = ImageIO.read(new File("textures/chara.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        x = 0;
        y = 0;
        velX = 0;
        velY = 0;
    }

    public void update(float deltaTime) {
        if (rightPressed)
            velX += 1;
        if (leftPressed)
            velX -= 1;
        if (upPressed)
            velY -= 1;
        if (downPressed)
            velY += 1;

        x += deltaTime * 300 * velX;
        y += deltaTime * 300 * velY;

        velX = 0;
        velY = 0;
    }

    public void drawSelf(Graphics2D g) {
        g.drawImage(image, (int) x, (int) y, 100, 100, null);
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == 37)
            leftPressed = true;
        if (e.getKeyCode() == 38)
            upPressed = true;
        if (e.getKeyCode() == 39)
            rightPressed = true;
        if (e.getKeyCode() == 40)
            downPressed = true;
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == 37)
            leftPressed = false;
        if (e.getKeyCode() == 38)
            upPressed = false;
        if (e.getKeyCode() == 39)
            rightPressed = false;
        if (e.getKeyCode() == 40)
            downPressed = false;

    }

}
