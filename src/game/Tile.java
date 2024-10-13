package game;

import java.awt.geom.Rectangle2D;

public class Tile {

    // The tile texture ids for each layer : floor, collision, decoration
    private int[] tileTextureIds = new int[3]; 

    public Tile() {
        this(0,0,0);
    }

    public Tile(int floorTextureId, int collisionTextureId, int decorationTextureId) {
        tileTextureIds[0] = floorTextureId;
        tileTextureIds[1] = collisionTextureId;
        tileTextureIds[2] = decorationTextureId;
    }

    public void setTextureID(int layer, int id) {
        tileTextureIds[layer] = id;
    }

    public int getTextureID(int layer) {
        return tileTextureIds[layer];
    }

    public Rectangle2D getBoundingBoxNorm() {
        if(tileTextureIds[0] == 13 || tileTextureIds[0] == 14 || tileTextureIds[0] == 15) return null;
        return new Rectangle2D.Double(0, 0, 1, 1);
    }
}