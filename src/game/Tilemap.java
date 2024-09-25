package game;

public class Tilemap {
    Tile[][] tileMap;
    
    public Tilemap(int width, int height){
        tileMap = new Tile[width][height];
    }
}
