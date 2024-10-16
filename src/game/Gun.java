package game;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Gun {
    private ArrayList<Tile.Type> bullets = new ArrayList<>();
    
    private boolean firing = false;

    public Gun() {
    }

    public boolean isEmpty() {
        return bullets.isEmpty();
    }

    public void fire() {
        firing = true;
    }

    public boolean isFiring() {
        return firing;
    }

    public Tile.Type getNextBullet() {
        firing = false;
        return bullets.remove(0);
    }

    public void addBullet(Tile.Type type) {
        bullets.add(type);
    }

    public void drawLoad(Point2D playerPos, Graphics2D g2d) {

        Point loadPos = new Point((int) playerPos.getX()+25, (int) playerPos.getY());

        for(Tile.Type b : bullets) {

            switch(b) {
                case Tile.Type.ground -> g2d.setColor(Color.gray);
                case Tile.Type.water -> g2d.setColor(Color.blue);
                case Tile.Type.lava -> g2d.setColor(Color.red);
           }
            g2d.fillRect(loadPos.x, loadPos.y, 20, 5);
            loadPos.setLocation(loadPos.getX(), loadPos.getY()-5);
        }
    }
}
