package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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

    private int direction = 0; // 0: down, 1: left, 2: up, 3: right

    private BufferedImage image;

    private Tilemap map;

    public Character(int size, Tilemap map) {
        this.map = map;
        try {
            image = ImageIO.read(new File("textures/chara_new.png"));
        } catch (IOException ex) {
        }

        this.size = size;

        x = -50;
        y = -50;
        velX = 0;
        velY = 0;

        image = Tileset.getScaledInstance(image, size, size);
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
        if(collide()) {
            // Don't
            x -= deltaTime * 300 * velX;
        }

        y += deltaTime * 300 * velY;
        if(collide()) {
            // Don't
            y -= deltaTime * 300 * velY;
        }

        velX = 0;
        velY = 0;

        // Update direction: if only one of the arrow keys is pressed, the character looks in that direction

        if (downPressed && !upPressed && !leftPressed && !rightPressed)
            direction = 0;
        if (leftPressed && !rightPressed && !upPressed && !downPressed)
            direction = 1;
        if (upPressed && !downPressed && !leftPressed && !rightPressed)
            direction = 2;
        if (rightPressed && !leftPressed && !upPressed && !downPressed)
            direction = 3;
        
    }

    public void drawSelf(Graphics2D g) {
        
        g.setColor(Color.red);
        
        ArrayList<Rectangle2D> collisions = map.getCollisions(new Point2D.Double(x, y), true);

        for(Rectangle2D r : collisions) {
            g.drawRect((int) r.getX(), (int) r.getY(), (int) r.getWidth(), (int) r.getHeight());
        }
        g.drawImage(image, (int)x - size/2, (int)y - size/2, null);
        
        Rectangle2D bounds = getBounds();
        g.setColor(Color.blue);
        g.drawRect((int) bounds.getX(), (int) bounds.getY(), (int) bounds.getWidth(), (int) bounds.getHeight());

        // Show the direction of the character

        g.setColor(Color.blue);
        switch (direction) {
            case 0:
                g.drawLine((int) x, (int) y, (int) x, (int) y + size);
                break;
            case 1:
                g.drawLine((int) x, (int) y, (int) x - size, (int) y);
                break;
            case 2:
                g.drawLine((int) x, (int) y, (int) x, (int) y - size);
                break;
            case 3:
                g.drawLine((int) x, (int) y, (int) x + size, (int) y);
                break;
        }
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

    private boolean collide() {
        ArrayList<Rectangle2D> collisions = map.getCollisions(new Point2D.Double(x, y), true);

        for(Rectangle2D r : collisions) {
            if(r.intersects(getBounds())) {
                return true;
            }
        }

        return false;
    }

    // Getters and Setters

    public Point2D getPosition() {
        return new Point2D.Double(x, y);
    }

    public Rectangle2D getBounds() {
        return new Rectangle2D.Double((int)x - size/2, (int)y - size/2, size, size);
    }

    public int getDirection() {
        return direction;
    }

}
