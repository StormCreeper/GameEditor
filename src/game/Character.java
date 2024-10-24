package game;

import game.Tile.Type;
import game.game_interfaces.GameDrawable;
import game.game_interfaces.GameUpdatable;

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
import main.MainWindow;
import main.Utils;

public class Character implements GameDrawable, GameUpdatable{

    private final static Point2D.Double[] directions = {
        new Point2D.Double(0, 1),
        new Point2D.Double(-1, 0),
        new Point2D.Double(0, -1),
        new Point2D.Double(1, 0)
    };

    public static Point2D.Double getScaledDirection(int direction, double scale) {
        return new Point2D.Double(directions[direction].getX()*scale, directions[direction].getY()*scale);
    }

    private static final int PLAYER_SPEED = 300;

    private final int size; // The size of the character on the screen

    private double x;
    private double y;

    private double velX;
    private double velY;

    private int direction = 0; // 0: down, 1: left, 2: up, 3: right

    private boolean upPressed;
    private boolean downPressed;
    private boolean leftPressed;
    private boolean rightPressed;
    private boolean dPressed;

    private boolean hasWon = false;

    private boolean selectionOk = false;
    private Type selectedType = Type.ground;

    private int boxSelX;
    private int boxSelY;

    private BufferedImage image;

    private Tilemap tilemap;

    private final ArrayList<Bullet> bullets = new ArrayList<>();
    private final Gun gun = new Gun();

    public boolean hasWon() {
        return hasWon;
    }

    public Character(int size, Tilemap map) {
        this.tilemap = map;
        try {
            image = ImageIO.read(new File("textures/chara_new.png"));
        } catch (IOException ex) {
        }

        this.size = size;

        x = tilemap.getTileSize()*tilemap.getStartPos().x + tilemap.getTileSize()/2;
        y = tilemap.getTileSize()*tilemap.getStartPos().y + tilemap.getTileSize()/2;
        velX = 0;
        velY = 0;

        image = Utils.getScaledInstance(image, size, size);

        gun.addAmmo(Type.water);
    }

    private void updateGun(double deltaTime) {
        for(Bullet b : bullets){
            b.update(deltaTime);
        }
        bullets.removeIf(Bullet::isDead);

        if(gun.isFiring()) {
            Point2D p = getPosition();
            int shakeIntensity = 500;

            Point2D.Double bulletVel = Character.getScaledDirection(direction, 500);
            bullets.add(new Bullet(p.getX(), p.getY(), bulletVel.getX(), bulletVel.getY(), 10, tilemap, gun.getNextAmmo()));

            Camera.instance.shake(Character.getScaledDirection(direction, -shakeIntensity));
        }
    }

    public void update(double deltaTime) {  
        
        updateGun(deltaTime);

        velX = 0;
        velY = 0;

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

        velX *= PLAYER_SPEED * deltaTime;
        velY *= PLAYER_SPEED * deltaTime;

        x += velX;
        if(collide()) {
            // Don't
            x -= velX;
        }

        y += velY;
        if(collide()) {
            // Don't
            y -= velY;
        }

        gun.setPosition(new Point2D.Double(x+25, y));

        if(detectWin())
            hasWon = true;

        // Get selection box pos
        if(dPressed) {
            boxSelX = (int) (x / tilemap.getTileSize());
            boxSelY = (int) (y / tilemap.getTileSize());
            switch (direction) {
                case 0 -> boxSelY++;
                case 1 -> boxSelX--;
                case 2 -> boxSelY--;
                case 3 -> boxSelX++;
            }

            // Get the tile under the selection box
            Tile tile = tilemap.getTile(boxSelX, boxSelY);
            if(tile != null) {
                selectedType = tile.getType();
                selectionOk = true;
            } else {
                selectionOk = false;
            }
        }

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
        ArrayList<Rectangle2D> collisions = tilemap.getCollisions(new Point2D.Double(x, y), true);

        if(dPressed) {
            g.setColor(Color.green);
            g.drawRect(boxSelX * tilemap.getTileSize(), boxSelY * tilemap.getTileSize(), tilemap.getTileSize(), tilemap.getTileSize());
        }

        // Draw gun
        if(direction == 2) {
            g.setColor(Color.black);
            g.fillRect((int)x - 5, (int)y - 30, 10, 20);
        }

        // Draw character
        g.drawImage(image, (int)x - size/2, (int)y - size/2, null);

        // Draw gun (in front of the character)
        if(direction == 0) {
            g.setColor(Color.black);
            g.fillRect((int)x - 5, (int)y + 15, 10, 20);
        }
        if(direction == 1) {
            g.setColor(Color.black);
            g.fillRect((int)x - 30, (int)y - 5, 20, 10);
        }
        if(direction == 3) {
            g.setColor(Color.black);
            g.fillRect((int)x + 10, (int)y - 5, 20, 10);
        }

        // Show the direction of the character

        if(MainWindow.instance.isDebug()) {
            
            Rectangle2D bounds = getBounds();
            g.setColor(Color.blue);
            g.drawRect((int) bounds.getX(), (int) bounds.getY(), (int) bounds.getWidth(), (int) bounds.getHeight());
            
            g.setColor(Color.red);
            for(Rectangle2D r : collisions) {
                g.drawRect((int) r.getX(), (int) r.getY(), (int) r.getWidth(), (int) r.getHeight());
            }

            g.setColor(Color.blue);
            switch (direction) {
                case 0 -> g.drawLine((int) x, (int) y, (int) x, (int) y + size);
                case 1 -> g.drawLine((int) x, (int) y, (int) x - size, (int) y);
                case 2 -> g.drawLine((int) x, (int) y, (int) x, (int) y - size);
                case 3 -> g.drawLine((int) x, (int) y, (int) x + size, (int) y);
            }
        }

        for(Bullet b : bullets){
            b.drawSelf(g);
        }

        gun.drawSelf(g);
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
        if (key == KeyEvent.VK_D)
            dPressed = true;
        
        
        if(key == KeyEvent.VK_S){
            if(!gun.isEmpty()) {
                gun.fire();
            }
        }
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
        if (key == KeyEvent.VK_D) {
            if(selectionOk)
                gun.addAmmo(selectedType);
            dPressed = false;
        }
    }

    private boolean detectWin() {
        ArrayList<Rectangle2D> collisions = tilemap.getCollisions(new Point2D.Double(x, y), 35);

        for(Rectangle2D r : collisions) {
            if(r.intersects(getBounds())) {
                return true;
            }
        }

        return false;
    }

    private boolean collide() {
        ArrayList<Rectangle2D> collisions = tilemap.getCollisions(new Point2D.Double(x, y), true);

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
