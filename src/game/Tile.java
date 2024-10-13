package game;

import java.util.ArrayList;

public class Tile {

    // The tile texture ids for each layer : floor, collision, decoration
    private int[] tileTextureIds = new int[3]; 
    private ArrayList<Integer> baseTextures = new ArrayList<Integer>();
    public enum Type {ground, water, lava};

    private Type baseTile = Type.water; 

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

    public ArrayList<Integer> getBaseTextures() {
        return baseTextures;
    }

    public void addBaseTexture(int texID) {
        baseTextures.add(texID);
    }

    public void resetBaseTexture() {
        baseTextures = new ArrayList<Integer>();
    }

    public Type getBaseTile() {
        return baseTile;
    }

    public void setBaseTile(Type base) {
        baseTile = base;
    }
}