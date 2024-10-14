package game;
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
}
