package game;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Tilemap {
    private Tile[][] tileMap;
    private final Tileset tileset;

    private int numTilesX, numTilesY;
    private int tileSize;

    public Tilemap(int numTilesX, int numTilesY, int tileSize, Tileset tileset) {
        this.tileset = tileset;

        this.numTilesX = numTilesX;
        this.numTilesY = numTilesY;

        this.tileSize = tileSize;

        tileMap = new Tile[numTilesX][numTilesY];
        for (int i = 0; i < numTilesX; i++) {
            for (int j = 0; j < numTilesY; j++) {
                tileMap[i][j] = new Tile((i + j) % 30, 0, 0);
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

    public void setTile(int i, int j, int textureID) {
        if (i < 0 || i >= numTilesX || j < 0 || j >= numTilesY) {
            return;
        }
        tileMap[i][j].setTextureID(0, textureID);
    }

    public int getTileSize() {
        return tileSize;
    }

    public Rectangle2D getBoundingBoxWorld(int i, int j) {
        Rectangle2D rect = getTile(i, j).getBoundingBoxNorm();
        if(rect != null) {
            rect.setRect(new Rectangle2D.Double(rect.getX() * tileSize, rect.getY() * tileSize, rect.getWidth() * tileSize, rect.getHeight() * tileSize));
            rect.setRect(new Rectangle2D.Double(rect.getX() + i * tileSize, rect.getY() + j * tileSize, rect.getWidth(), rect.getHeight()));
        }

        return rect;
    }

    public ArrayList<Rectangle2D> getCollisions(Point2D center) {
        int center_i = (int)(center.getX() / tileSize);
        int center_j = (int)(center.getY() / tileSize);

        ArrayList<Rectangle2D> collisions = new ArrayList<>();

        for(int i = Math.max(center_i-1, 0); i<=Math.min(center_i+1, numTilesX-1);i++) {
            for(int j = Math.max(center_j-1, 0); j<=Math.min(center_j+1, numTilesY-1);j++) {
                Rectangle2D rect = getBoundingBoxWorld(i, j);
                if(rect != null)
                    collisions.add(rect);
            }
        }

        return collisions;
    }

    public void loadFromFile(String filename) {
        File file = new File(filename);

        String data = "";
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                data = data + "\n" + scanner.nextLine();
                // This causes an empty line at the beginning
            }
        } catch (FileNotFoundException ex) {
            System.err.println("Could not load " + filename + " :(");
        }

        String[] lines = data.split("\n");

        numTilesX = lines[1].split(" ").length ; // We divide by 2 to account for the spaces
        numTilesY = lines.length - 1; // -1 for empty line at beginning

        tileMap = new Tile[numTilesX][numTilesY];

        for (int j = 0; j < numTilesY; j++) {
            String[] tileList = lines[1 + j].split(" "); // + 1 for empty line at beginning

            for (int i = 0; i < numTilesX; i++) {
                tileMap[i][j] = new Tile();
                tileMap[i][j].setTextureID(0, Integer.parseInt(tileList[i]));
            }
        }

    }

    public void drawSelf(Graphics2D g) {
        for (int j = 0; j < numTilesY; j++) {
            for (int i = 0; i < numTilesX; i++) {
                g.drawImage(tileset.getTexture(tileMap[i][j].getTextureID(0)), i * tileSize, j * tileSize, tileSize, tileSize, null);
            }
        }
    }

    public void save(String filePath) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath + ".txt"));

            for (int j = 0; j < numTilesY; j++) {
                for (int i = 0; i < numTilesX; i++) {
                    writer.write(String.valueOf(tileMap[i][j].getTextureID(0)));
                    writer.write(" ");
                }
                writer.write('\n');
            }

            writer.close();
        } catch (IOException e) {
            System.out.println("Could not save to file" + filePath);
        }
    }

    public void addLine() {
        numTilesY += 1;
        Tile[][] temp = tileMap.clone();
        tileMap = new Tile[numTilesX][numTilesY];

        for (int j = 0; j < numTilesY; j++) {

            for (int i = 0; i < numTilesX; i++) {
                tileMap[i][j] = new Tile();
                if (j != numTilesY - 1) {
                    tileMap[i][j].setTextureID(0, temp[i][j].getTextureID(0));
                }
            }
        }
    }

    public void addColumn() {
        numTilesX += 1;
        Tile[][] temp = tileMap.clone();
        tileMap = new Tile[numTilesX][numTilesY];

        for (int j = 0; j < numTilesY; j++) {

            for (int i = 0; i < numTilesX; i++) {
                tileMap[i][j] = new Tile();
                if (i != numTilesX - 1) {
                    tileMap[i][j].setTextureID(0, temp[i][j].getTextureID(0));
                }
            }
        }
    }

    public int getNumX() {
        return numTilesX;
    }

    public int getNumY() {
        return numTilesY;
    }
}
