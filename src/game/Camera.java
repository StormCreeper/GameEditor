package game;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;

import game.game_interfaces.GameDrawable;
import game.game_interfaces.GameUpdatable;


/**
 * Handles the panning of the canvas to simulate a 2D camera:
 * Tracks a position smoothly, and transforms the canvas so that every object is translated accordingly
 * The camera also supports the screenshake effect, simulated via a mass attached to the target position by a spring
 */
public class Camera implements GameDrawable, GameUpdatable {

    private static final double EASE_FACTOR = 10;

    private static final double SPRING_STIFFNESS = 1000;
    private static final double SPRING_DAMPING = 20; 

    // Since the Camera is unique, we can access it from everywhere easily
    public static Camera instance;

    private final Character target;
    private final Point2D.Double position;

    // To simulate a mass attached to a spring
    private final Point2D.Double shakeOffset = new Point2D.Double(0, 0);
    private final Point2D.Double shakeVelocity = new Point2D.Double(0, 0);

    public Camera(Character target) {
        instance = this;
        this.target = target;
        this.position = (Point2D.Double) target.getPosition();
    }

    public Point2D.Double getPosition(){
        return position;
    }

    public void update(double deltaTime) {
        // Smooth follow computation

        position.x = position.x + (target.getPosition().getX() - position.x) * EASE_FACTOR * deltaTime;
        position.y = position.y + (target.getPosition().getY() - position.y) * EASE_FACTOR * deltaTime;

        // Screenshake computation

        shakeOffset.x += shakeVelocity.x * deltaTime;
        shakeOffset.y += shakeVelocity.y * deltaTime;

        // Spring forces (Hooke's law and linear damping)

        shakeVelocity.x -= shakeOffset.x * SPRING_STIFFNESS * deltaTime;
        shakeVelocity.y -= shakeOffset.y * SPRING_STIFFNESS * deltaTime;

        shakeVelocity.x -= shakeVelocity.x * SPRING_DAMPING * deltaTime;
        shakeVelocity.y -= shakeVelocity.y * SPRING_DAMPING * deltaTime;
    }

    /**
     * Translates the whole canvas, to be called before drawing other game components
     * @param g the Graphics object to transform
     */
    public void drawSelf(Graphics2D g) { 
        Rectangle bounds = g.getClipBounds();
        g.translate(-position.x + bounds.width / 2 + shakeOffset.x, -position.y + bounds.height / 2 + shakeOffset.y);
    }

    /**
     * "Push" the camera off equilibrium with a certain intensity, to trigger the shake effect
     * @param intensity direction and intensity of the shake
     */
    public void shake(Point.Double intensity) {
        shakeVelocity.x += intensity.x;
        shakeVelocity.y += intensity.y;
    }
}
