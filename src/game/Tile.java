package game;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class Tile {

    public enum Type {
        ground, 
        water, 
        lava
    };

    private Type type;

    private ArrayList<Integer> baseTextures = new ArrayList<>(); // The texture ids that are used to create the first layer of the tile

    // The tile texture ids for each layer : floor, collision, decoration
    // private int[] tileTextureIds = new int[3]; 

    public Tile() {
        this(Type.ground);
    }

    public Tile(Type type) {
        this.type = type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public Rectangle2D getBoundingBoxNorm() {
        if(type == Type.ground) return null;
        return new Rectangle2D.Double(0, 0, 1, 1);
    }

    public ArrayList<Integer> getBaseTextures() {
        return baseTextures;
    }

    public void addBaseTexture(int texID) {
        baseTextures.add(texID);
    }

    public void resetBaseTexture() {
        baseTextures = new ArrayList<>();
    }

    public int typeToInt() {
        return switch (type) {
            case ground -> 0;
            case water -> 1;
            case lava -> 2;
        };
    }
}