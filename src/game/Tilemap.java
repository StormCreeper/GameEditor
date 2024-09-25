package game;

public class Tilemap {
    Tile[][] tileMap;
    
    public Tilemap(int width, int height){
        tileMap = new Tile[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
               tileMap[i][j] = new Tile();
            }         
        }
    }
}
