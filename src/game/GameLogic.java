package game;

import java.awt.Graphics2D;
import java.awt.event.*;
import javax.swing.JPanel;

/**
 * "Interface" class to act as an entry point for the game logic only: all
 * functions in the class will be called by the system
 * This is to focus on the game logic only
 */
public class GameLogic {
    private final Tilemap tilemap;

    private final Character character;

    private final Camera camera;

    /**
     * To have access to getWidth(), getHeight()
     */
    private final JPanel parent;

    public GameLogic(JPanel parent, Tilemap tilemap) {
        this.parent = parent;
        this.tilemap = tilemap;

        character = new Character(tilemap.getTileSize() - 4, tilemap);

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
    }

    /**
     * Function called each time a keyPressed event occurs
     * 
     * @param e the event object
     */
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        character.keyPressed(key);
    }

    /**
     * Function called each time a keyReleased event occurs
     * 
     * @param e the event object
     */
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        character.keyReleased(key);
    }

    public void mousePressed(MouseEvent e){
        
    }

    public void mouseReleased(MouseEvent e){
        
    }

    public void mouseDragged(MouseEvent e){
        
    }
}
