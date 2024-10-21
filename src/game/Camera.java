package game;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;

public class Camera {

    public static Camera instance;

    private final Character target;
    private final Point2D.Double position;

    private Point2D.Double shakeOffset = new Point2D.Double(0, 0);
    private Point2D.Double shakeVelocity = new Point2D.Double(0, 0);

    private final double easeFactor;

    public Camera(Character target, double easeFactor) {
        instance = this;
        this.easeFactor = easeFactor;
        this.target = target;
        this.position = (Point2D.Double) target.getPosition();
    }

    public Point2D.Double getPosition(){
        return position;
    }

    public void update(double deltaTime) {
        // position.x = target.getPosition().getX();
        // position.y = target.getPosition().getY();

        position.x = position.x + (target.getPosition().getX() - position.x) * easeFactor * deltaTime;
        position.y = position.y + (target.getPosition().getY() - position.y) * easeFactor * deltaTime;

        shakeOffset.x += shakeVelocity.x * deltaTime;
        shakeOffset.y += shakeVelocity.y * deltaTime;

        shakeVelocity.x -= shakeOffset.x * 1000 * deltaTime;
        shakeVelocity.y -= shakeOffset.y * 1000 * deltaTime;

        shakeVelocity.x -= shakeVelocity.x * 20 * deltaTime;
        shakeVelocity.y -= shakeVelocity.y * 20 * deltaTime;
    }

    public void updateCanvas(Graphics2D g) {
        Rectangle bounds = g.getClipBounds();
        g.translate(-position.x + bounds.width / 2 + shakeOffset.x, -position.y + bounds.height / 2 + shakeOffset.y);
    }

    public void shake(Point.Double intensity) {
        shakeVelocity.x += intensity.x;
        shakeVelocity.y += intensity.y;
    }
}
