package game;
import java.util.ArrayList;

public class Gun {
    public ArrayList<Tile.Type> bullets = new ArrayList<>();

    public Gun() {
        for(int i=0; i<5; i++) {
            bullets.add(Tile.Type.water);
        }
    }

    public Tile.Type getNextBullet() {
        
        return bullets.get(0);
    }
}
