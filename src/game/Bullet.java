package game;

import game.Tile.Type;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;


public class Bullet {
    int size;

    double x;
    double y;

    double vx;
    double vy;

    Tile.Type type;

    private final Tilemap map;
    private boolean isDead = false;

    public Bullet(double x, double y, double vx, double vy, int size, Tilemap map, Tile.Type type) {
        this.size = size;
        this.map = map;

        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;

        this.type = type;
    }

    public void update(double deltaTime) {
        x += vx * deltaTime;
        y += vy * deltaTime;

        if (collide()) {

            //If water bullet collides lava, the lava becomes ground
            Point pos = new Point((int)(x+vx), (int)(y+vy));

            Type tileType = map.getType(pos.x, pos.y);
            if (tileType==Type.lava && type==Tile.Type.water) {
                map.setTile(pos.x/map.getTileSize(), pos.y/map.getTileSize(), Type.ground);
            } 

            isDead = true;
        }
    }

    public boolean isDead() {
        return isDead;
    }

    public void drawSelf(Graphics2D g){
        switch (type) {
            case water
                -> g.setColor(Color.blue);
            case lava
                -> g.setColor(Color.red);
            case ground
                -> g.setColor(Color.gray);
        }
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
