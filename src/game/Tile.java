package game;

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
}