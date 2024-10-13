package game;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Character {

    private int size; // The size of the character on the screen

    private double x;
    private double y;

    private double velX;
    private double velY;

    private boolean upPressed;
    private boolean downPressed;
    private boolean leftPressed;
    private boolean rightPressed;

    private BufferedImage image;

    public Character(int size) {
        try {
            image = ImageIO.read(new File("textures/chara_new.png"));
        } catch (IOException ex) {
        }

        this.size = size;

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

        // To correct the speed when the character goes diagonally (so it doesn't go faster)
        double speed = Math.sqrt(velX * velX + velY * velY);
        if (speed > 1) {
            velX /= speed;
            velY /= speed;
        }

        x += deltaTime * 300 * velX;
        y += deltaTime * 300 * velY;

        velX = 0;
        velY = 0;
    }

    public void drawSelf(Graphics2D g) {
        g.drawImage(image, (int)x - size/2, (int)y - size/2, size, size, null);
    }

    public void keyPressed(int key) {
        if (key == KeyEvent.VK_LEFT)
            leftPressed = true;
        if (key == KeyEvent.VK_UP)
            upPressed = true;
        if (key == KeyEvent.VK_RIGHT)
            rightPressed = true;
        if (key == KeyEvent.VK_DOWN)
            downPressed = true;
    }

    public void keyReleased(int key) {
        if (key == KeyEvent.VK_LEFT)
            leftPressed = false;
        if (key == KeyEvent.VK_UP)
            upPressed = false;
        if (key == KeyEvent.VK_RIGHT)
            rightPressed = false;
        if (key == KeyEvent.VK_DOWN)
            downPressed = false;

    }

    // Getters and Setters

    public Point2D getPosition() {
        return new Point2D.Double(x, y);
    }

}
