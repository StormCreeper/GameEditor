package game;

import java.awt.image.*;

public class Tile {

    private int xPosition;
    private int yPosition;

    private BufferedImage tileTexture;

    public void setXPosition(int x) {
        xPosition = x;
    }

    public int getXPosition() {
        return xPosition;
    }

    public void setYPosition(int y) {
        yPosition = y;
    }

    public int getYPosition() {
        return yPosition;
    }

    public void setTexture(BufferedImage texture) {
        tileTexture = texture;
    }

    public BufferedImage getBufferedImage() {
        return tileTexture;
    }
}
