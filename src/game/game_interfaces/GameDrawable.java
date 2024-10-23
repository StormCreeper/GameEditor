package game.game_interfaces;

import java.awt.Graphics2D;


/**
 * Objects that can be drawn in the game canvas
 */
public interface GameDrawable {

    /**
     * Draws itself to the graphics object, without consideration to the camera or screen clearing
     * @param g
     */
    public void drawSelf(Graphics2D g);

}
