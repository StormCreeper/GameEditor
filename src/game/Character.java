package game;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Character {

    private double x;
    private double y;

    private double velX;
    private double velY;

    private boolean upPressed;
    private boolean downPressed;
    private boolean leftPressed;
    private boolean rightPressed;

    private BufferedImage image;

    public Character(String imagePath) {
        try {
            image = ImageIO.read(new File("textures/chara.png"));
        } catch (IOException ex) {
        }

        x = 0;
        y = 0;
        velX = 0;
        velY = 0;
    }

    public void update(double deltaTime) {
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
        g.drawImage(image, (int)x - 50, (int)y - 50, 100, 100, null);
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT)
            leftPressed = true;
        if (e.getKeyCode() == KeyEvent.VK_UP)
            upPressed = true;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT)
            rightPressed = true;
        if (e.getKeyCode() == KeyEvent.VK_DOWN)
            downPressed = true;
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT)
            leftPressed = false;
        if (e.getKeyCode() == KeyEvent.VK_UP)
            upPressed = false;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT)
            rightPressed = false;
        if (e.getKeyCode() == KeyEvent.VK_DOWN)
            downPressed = false;

    }

    // Getters and Setters

    public Point2D getPosition() {
        return new Point2D.Double(x, y);
    }

}
