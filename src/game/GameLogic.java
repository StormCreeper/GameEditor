package game;

import game.Character;
import java.awt.Graphics2D;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 * "Interface" class to act as an entry point for the game logic only: all
 * functions in the class will be called by the system
 * This is to focus on the game logic only
 */
public class GameLogic {

    private final Tileset tileset;
    private final Tilemap tilemap;

    private final Character character;

    private final ArrayList<Bullet> bullets = new ArrayList<>();

    private final Camera camera;

    /**
     * To have access to getWidth(), getHeight()
     */
    private final JPanel parent;

    public GameLogic(JPanel parent, Tileset tileset, Tilemap tilemap) {
        this.parent = parent;

        this.tileset = tileset;
        this.tilemap = tilemap;

        character = new Character(tilemap.getTileSize());

        camera = new Camera(character, 10.0);

    }

    /**
     * Call each frame before draw
     * 
     * @param currentTime time in seconds elapsed since the program start
     * @param deltaTime   time in seconds since last frame
     */
    public void update(double currentTime, double deltaTime) {
        // System.out.println("Delta time : " + deltaTime);
        character.update(deltaTime);
        for(Bullet b : bullets){
            b.update(deltaTime);
        }
        camera.update(deltaTime);
    }

    /**
     * Called each frame after update
     * 
     * @param g the graphics object used for drawing
     */
    public void draw(Graphics2D g) {
        g.clearRect(0, 0, parent.getWidth(), parent.getHeight());

        // All drawing goes here

        camera.updateCanvas(g); // Center the graphics to the camera

        tilemap.drawSelf(g);

        character.drawSelf(g);
        for(Bullet b : bullets){
            b.drawSelf(g);
        }
    }

    /**
     * Function called each time a keyPressed event occurs
     * 
     * @param e the event object
     */
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_UP || key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_DOWN || key == KeyEvent.VK_LEFT){
            character.keyPressed(key);
        } else if(key == KeyEvent.VK_S){
            Point2D p = character.getPosition();
            bullets.add(new Bullet(p.getX(), p.getY(), 0, 50, 10));
        }
    }

    /**
     * Function called each time a keyReleased event occurs
     * 
     * @param e the event object
     */
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_UP || key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_DOWN || key == KeyEvent.VK_LEFT){
            character.keyReleased(key);
        }
    }

    public void mousePressed(MouseEvent e){
        
    }

    public void mouseReleased(MouseEvent e){
        
    }

    public void mouseDragged(MouseEvent e){
        
    }
}
