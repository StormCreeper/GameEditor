package game;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import game.game_interfaces.GameDrawable;

public class Gun implements GameDrawable{
    private final ArrayList<Tile.Type> ammoList = new ArrayList<>();
    
    private boolean firing = false;

    Point2D.Double position;

    public Gun() {
    }

    public boolean isEmpty() {
        return ammoList.isEmpty();
    }

    public void fire() {
        firing = true;
    }

    public boolean isFiring() {
        return firing;
    }

    public Tile.Type getNextAmmo() {
        firing = false;
        return ammoList.remove(0);
    }

    public void addAmmo(Tile.Type type) {
        ammoList.add(type);
    }

    public void setPosition(Point2D.Double position) {
        this.position = position;
    }

    public void drawSelf(Graphics2D g2d) {

        Point loadPos = new Point((int) position.getX(), (int) position.getY());

        for(Tile.Type bulletType : ammoList) {

            switch(bulletType) {
                case ground -> g2d.setColor(new Color(168,150,150));
                case water -> g2d.setColor(Color.blue);
                case lava -> g2d.setColor(Color.red);
           }
            g2d.fillRect(loadPos.x, loadPos.y, 20, 5);
            loadPos.setLocation(loadPos.getX(), loadPos.getY()-5);
        }
    }
}
