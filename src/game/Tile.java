package game;

import java.awt.image.BufferedImage;

public class Tile {

    private int xPosition;
    private int yPosition;

    private int tileTextureId;

    private final Tileset tileset;

    public Tile(int x, int y, Tileset tileset) {
        this.tileset = tileset;
        xPosition = x;
        yPosition = y;
    }

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

    public void setTextureID(int id) {
        tileTextureId = id;
    }

    public int getImageID() {
        return tileTextureId;
    }

    public BufferedImage getImage() {
        return tileset.getTexture(tileTextureId);
    }
}
