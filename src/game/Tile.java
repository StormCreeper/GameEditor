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
    private int[] layersTexturesID = {0, 0};

    private ArrayList<Integer> baseTextures = new ArrayList<>(); // The texture ids that are used to create the first layer of the tile

    // The tile texture ids for each layer : floor, collision, decoration
    // private int[] tileTextureIds = new int[3]; 

    public Tile() {
        this(Type.ground);
    }

    public Tile(Type type) {
        this.type = type;
    }
    
    // 2 bits for type, 15 bits for collision layer, 15 bits for decoration layer
    public Tile(int repr) {
        this.type = intToType(repr & 0b11);
        this.layersTexturesID[0] = (repr >> 2) & 0b111111111111111;
        this.layersTexturesID[1] = (repr >> 17) & 0b111111111111111;
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

    public Type intToType(int i) {
        return switch (i) {
            case 0 -> Type.ground;
            case 1 -> Type.water;
            case 2 -> Type.lava;
            default -> Type.ground;
        };
    }

    public int typeToInt() {
        return switch (type) {
            case ground -> 0;
            case water -> 1;
            case lava -> 2;
        };
    }

    // Encode the tile in an integer
    // 2 bits for type, 15 bits for collision layer, 15 bits for decoration layer
    public int repr() {
        return typeToInt() | layersTexturesID[0] << 2 | layersTexturesID[1] << 17;
    }
}