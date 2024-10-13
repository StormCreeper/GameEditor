package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;


public class Bullet {
    int size;

    double x;
    double y;

    double vx;
    double vy;

    private final Tilemap map;
    private boolean isDead = false;

    public Bullet(double x, double y, double vx, double vy, int size, Tilemap map) {
        this.size = size;
        this.map = map;

        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
    }

    public void update(double deltaTime) {
        x += vx * deltaTime;
        y += vy * deltaTime;

        if (collide()) {
            isDead = true;
        }
    }

    public boolean isDead() {
        return isDead;
    }

    public void drawSelf(Graphics2D g){
        g.setColor(Color.RED);
        g.fillOval((int) x - size/2, (int) y - size/2, size, size);
    }

    private boolean collide() {
        ArrayList<Rectangle2D> collisions = map.getCollisions(new Point2D.Double(x, y));

        for(Rectangle2D r : collisions) {
            if(r.intersects(getBounds())) {
                return true;
            }
        }

        return false;
    }

    private Rectangle2D getBounds(){
        return new Rectangle2D.Double(x - size/2, y - size/2, size, size);
    }
}
