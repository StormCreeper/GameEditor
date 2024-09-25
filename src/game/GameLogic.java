package game;

import game.Character;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;

/**
 * "Interface" class to act as an entry point for the game logic only: all functions in the class will be called by the system
 * This is to focus on the game logic only
 */
public class GameLogic {

	private Tileset tileset;
    private Character character;

    /**
     * To have access to getWidth(), getHeight()
     */
    private JPanel parent;

    public GameLogic(JPanel parent) {
        this.parent = parent;

		tileset = new Tileset(16, 7);
		tileset.loadTextures();

        character = new Character("textures/chara.png");


    }

    /**
     * Call each frame before draw
     * @param currentTime time in seconds elapsed since the program start
     * @param deltaTime time in seconds since last frame
     */
    public void update(float currentTime, float deltaTime) {
        character.update(deltaTime);
    }

    /**
     * Called each frame after update
     * @param g the graphics object used for drawing
     */
    public void draw(Graphics2D g) {
        g.clearRect(0, 0, parent.getWidth(), parent.getHeight());

        // All drawing goes here 

        for(int j=0; j<7; j++) {
            for(int i=0; i<7; i++) {
                g.drawImage(tileset.getTextureArray().get((i+j)%7), i*100, j*100, 90, 90, null);
            }
        }  
        
        character.drawSelf(g);
    }

    /**
     * Function called each time a keyPressed event occurs
     * @param e the event object
     */
    public void keyPressed(KeyEvent e) {
        character.keyPressed(e);
    }
    /**
     * Function called each time a keyReleased event occurs
     * @param e the event object
     */
    public void keyReleased(KeyEvent e) {
        character.keyReleased(e);
    }
}
