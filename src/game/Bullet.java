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

            Point pos = new Point((int)(x), (int)(y));
            Type tileType = map.getType(pos.x, pos.y);

            int i = pos.x/map.getTileSize();
            int j = pos.y/map.getTileSize();
            //If water bullet collides lava, the lava becomes ground
            if (tileType==Type.lava && type==Tile.Type.water) {
                map.setType(i, j, Type.ground);
            } 

            //If ground bullet collides water, the water becomes ground
            if (tileType==Tile.Type.water && type==Tile.Type.ground) {
                map.setType(i, j, Type.ground);
            }

            int[] layerTextures = map.getTile(i, j).getLayersTextures();
            for(int k = 0; k<layerTextures.length ; k++) {

                //If lava bullet collides tree, tree disappear
                if(layerTextures[k]==31 && type==Tile.Type.lava) {
                    layerTextures[k] = 0;
                }

                //If water bullet collapses crack, it feels the crack with water
                if(layerTextures[k]==30 && type==Tile.Type.water) {
                    layerTextures[k] = 0;
                    map.setType(i, j, Tile.Type.water);
                }
            }

            //Whatever the result is, the bullet disappear after the collision
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
        ArrayList<Rectangle2D> collisions = map.getCollisions(new Point2D.Double(x, y), false);

        for(Rectangle2D r : collisions) {
            if(r.contains(x, y)) {
                return true;
            }
        }

        return false;
    }
}
