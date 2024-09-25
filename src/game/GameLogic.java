package game;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;

public class GameLogic {

	private Tileset tileset;

    private JPanel parent;

    public GameLogic(JPanel parent) {
		tileset = new Tileset(16, 7);
		tileset.loadTextures();

        this.parent = parent;
    }

    public void update(float currentTime, float deltaTime) {

    }

    public void draw(Graphics2D g) {
        g.clearRect(0, 0, parent.getWidth(), parent.getHeight());

        for(int i=0; i<7; i++) {
            g.drawImage(tileset.getTextureArray().get(i), i*100, 0, 90, 90, null);
        }
    }

    public void keyPressed(KeyEvent e) {

    }

    public void keyReleased(KeyEvent e) {

    }
}
