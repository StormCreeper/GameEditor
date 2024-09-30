package game;

import game.Character;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Point2D;

public class Camera {
    private final Character target;
    private final Point2D.Double position;

    private final double easeFactor;

    public Camera(Character target, double easeFactor) {
        this.easeFactor = easeFactor;
        this.target = target;
        this.position = (Point2D.Double) target.getPosition();
    }

    public void update(double deltaTime) {
        // position.x = target.getPosition().getX();
        // position.y = target.getPosition().getY();

        position.x = position.x + (target.getPosition().getX() - position.x) * easeFactor * deltaTime;
        position.y = position.y + (target.getPosition().getY() - position.y) * easeFactor * deltaTime;
    }

    public void updateCanvas(Graphics2D g) {
        Rectangle bounds = g.getClipBounds();
        g.translate(-position.x + bounds.width / 2, -position.y + bounds.height / 2);
    }
}
