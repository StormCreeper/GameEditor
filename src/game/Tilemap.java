package game;

import java.awt.Graphics2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Tilemap {
    private final Tile[][] tileMap;
    private final Tileset tileset;

    private final int numTilesX, numTilesY;
    private int tileSize;

    public Tilemap(int numTilesX, int numTilesY, int tileSize, Tileset tileset) {
        this.tileset = tileset;
        this.numTilesX = numTilesX;
        this.numTilesY = numTilesY;
        this.tileSize = tileSize;

        tileMap = new Tile[numTilesX][numTilesY];
        for (int i = 0; i < numTilesX; i++) {
            for (int j = 0; j < numTilesY; j++) {
                tileMap[i][j] = new Tile(i, j, this.tileset);
            }
        }
    }

    public Tile getTile(int i, int j) {
        if (i < 0 || i >= numTilesX || j < 0 || j >= numTilesY) {
            return null;
        }
        return tileMap[i][j];
    }

    public void setTile(int i, int j, Tile tile) {
        if (i < 0 || i >= numTilesX || j < 0 || j >= numTilesY) {
            return;
        }
        tileMap[i][j] = tile;
    }

    public void setTile(int i, int j, int tileID) {
        if (i < 0 || i >= numTilesX || j < 0 || j >= numTilesY) {
            return;
        }
        tileMap[i][j].setTextureID(tileID);
    }

    public void loadFromFile(String filename) {
        File file = new File(filename);

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                System.out.println(data);
            }
        } catch (FileNotFoundException ex) {
            System.err.println("Could not load " + filename + " :(");
        }
    }

    public void drawSelf(Graphics2D g) {
        for (int j = 0; j < numTilesY; j++) {
            for (int i = 0; i < numTilesX; i++) {
                g.drawImage(tileMap[i][j].getImage(), i * tileSize, j * tileSize, tileSize, tileSize, null);
            }
        }
    }
}
