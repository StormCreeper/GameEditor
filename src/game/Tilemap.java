package game;

import game.Tile.Type;
import java.awt.Graphics2D;
import java.awt.Point;
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

    private boolean hasChanged = false;

    private Point startPos = new Point(-1,-1);

    public Tilemap(int numTilesX, int numTilesY, int tileSize, Tileset tileset) {
        this.tileset = tileset;

        this.numTilesX = numTilesX;
        this.numTilesY = numTilesY;

        this.tileSize = tileSize;

        tileMap = new Tile[numTilesX][numTilesY];
        for (int i = 0; i < numTilesX; i++) {
            for (int j = 0; j < numTilesY; j++) {
                tileMap[i][j] = new Tile(Type.ground);
            }
        }
    }

    public void change() {
        hasChanged = true;
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
        hasChanged = true;
    }

    public void setType(int i, int j, Type type) {
        if (i < 0 || i >= numTilesX || j < 0 || j >= numTilesY) {
            return;
        }
        tileMap[i][j].setType(type);
        hasChanged = true;
    }

    public void setTileLayers(int i, int j, int ID, int layer) {
        if (i < 0 || i >= numTilesX || j < 0 || j >= numTilesY) {
            return;
        }
        if(ID==35) {
            tileMap[startPos.x][startPos.y].setLayer(0, layer);
            startPos = new Point(i, j);
        }
        tileMap[i][j].setLayer(ID, layer);
    }

    public void setTileHighlighted(int i, int j, boolean highlight) {
        if (i < 0 || i >= numTilesX || j < 0 || j >= numTilesY) {
            return;
        }
        tileMap[i][j].setHighlighted(highlight);
    }

    public int getTileSize() {
        return tileSize;
    }

    public Point getStartPos() {
        return startPos;
    }

    public void newEmptyMap(int numTilesX, int numTilesY) {
        this.numTilesX = numTilesX;
        this.numTilesY = numTilesY;

        tileMap = new Tile[numTilesX][numTilesY];
        for (int i = 0; i < numTilesX; i++) {
            for (int j = 0; j < numTilesY; j++) {
                tileMap[i][j] = new Tile(Type.ground);
            }
        }

        hasChanged = true;
    }

    public Rectangle2D getBoundingBoxWorld(int i, int j, boolean isCharacter) {
        Tile tile = getTile(i, j);
        if(tile == null) return null;
        Rectangle2D rect = getTile(i, j).getBoundingBoxNorm(isCharacter);
        if(rect != null) {
            rect.setRect(new Rectangle2D.Double(rect.getX() * tileSize, rect.getY() * tileSize, rect.getWidth() * tileSize, rect.getHeight() * tileSize));
            rect.setRect(new Rectangle2D.Double(rect.getX() + i * tileSize, rect.getY() + j * tileSize, rect.getWidth(), rect.getHeight()));
        }

        return rect;
    }

    public ArrayList<Rectangle2D> getCollisions(Point2D center, boolean isCharacter) {
        int center_i = (int)(center.getX() / tileSize);
        int center_j = (int)(center.getY() / tileSize);

        ArrayList<Rectangle2D> collisions = new ArrayList<>();

        for(int i = Math.max(center_i-1, 0); i<=Math.min(center_i+1, numTilesX-1);i++) {
            for(int j = Math.max(center_j-1, 0); j<=Math.min(center_j+1, numTilesY-1);j++) {
                Rectangle2D rect = getBoundingBoxWorld(i, j, isCharacter);
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

        numTilesX = lines[1].split(" ").length ;
        numTilesY = lines.length - 1; // -1 for empty line at beginning

        tileMap = new Tile[numTilesX][numTilesY];

        for (int j = 0; j < numTilesY; j++) {
            String[] tileList = lines[1 + j].split(" "); // + 1 for empty line at beginning

            for (int i = 0; i < numTilesX; i++) {
                tileMap[i][j] = new Tile(Integer.parseInt(tileList[i])); // Use encoded representation of the tile
                if(tileMap[i][j].getLayersTextures()[0] == 34) {
                    startPos = new Point(i,j);
                }
            }
        }

        hasChanged = true;

    }

    public void save(String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (int j = 0; j < numTilesY; j++) {
                for (int i = 0; i < numTilesX; i++) {
                    writer.write(String.valueOf(tileMap[i][j].repr()));
                    writer.write(" ");
                }
                writer.write('\n');
            }
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
                    tileMap[i][j].setType(temp[i][j].getType());
                }
            }
        }
        hasChanged = true;
    }

    public void addColumn() {
        numTilesX += 1;
        Tile[][] temp = tileMap.clone();
        tileMap = new Tile[numTilesX][numTilesY];

        for (int j = 0; j < numTilesY; j++) {

            for (int i = 0; i < numTilesX; i++) {
                tileMap[i][j] = new Tile();
                if (i != numTilesX - 1) {
                    tileMap[i][j].setType(temp[i][j].getType());
                }
            }
        }

        hasChanged = true;
    }

    public int getNumX() {
        return numTilesX;
    }

    public int getNumY() {
        return numTilesY;
    }

    public Type getType(int x, int y) {
        int i = x/tileSize;
        int j = y/tileSize;

        return tileMap[i][j].getType();
    }

    public void doBorders() {
        
        //Base Texture
        for (int j = 0; j < numTilesY; j++) {
            for (int i = 0; i < numTilesX; i++) {
                Tile tile = tileMap[i][j];

                tile.resetBaseTexture();
                switch(tile.getType()) {
                    case ground -> tile.addBaseTexture(5);
                    case water -> tile.addBaseTexture(13);
                    case lava -> tile.addBaseTexture(21);
                }
            }
        }

        //Borders
        for (int j=0; j < numTilesY; j++) {
            for (int i = 0; i<numTilesX; i++) {
                Tile tile = tileMap[i][j];
                
                //We only need to add borders on water or lava :
                if(tile.getType() == Type.water || tile.getType() == Type.lava) {

                    // Borders

                        if(j!=0 && tileMap[i][j-1].getType() == Type.ground) { //Ground above

                            if(j!=numTilesY-1 && tileMap[i][j+1].getType() == Type.ground) { //Ground above and below
                               
                                if(i!=0 && tileMap[i-1][j].getType() == Type.ground) { //Ground above, below, and left
                               
                                    if(i!=numTilesX-1 && tileMap[i+1][j].getType() == Type.ground) { //Ground all around
                                        tile.addBaseTexture(1);
                                    }
                                    else { //Ground around, below and left
                                        tile.addBaseTexture(25);
                                    }
                                }
                                else {
                                    if(i!=numTilesX-1 && tileMap[i+1][j].getType() == Type.ground) { //Ground above, below and right
                                        tile.addBaseTexture(27);
                                    }
                                    else { //Ground above and below
                                        tile.addBaseTexture(3);
                                    }
                                }
                            }

                            else { //Ground above, no ground below
                                if(i!=0 && tileMap[i-1][j].getType() == Type.ground) { //Ground above and left
                                    if(i!=numTilesX-1 && tileMap[i+1][j].getType() == Type.ground) { //Ground above, left and right
                                        tile.addBaseTexture(26);
                                    }
                                    else { //Ground above and left
                                        tile.addBaseTexture(11);
                                    }
                                }
                                else {
                                    if(i!=numTilesX-1 && tileMap[i+1][j].getType() == Type.ground) {//Ground above and right
                                        tile.addBaseTexture(10);
                                    }
                                    else { //Ground only above
                                        tile.addBaseTexture(19);
                                    }
                                }
                            }
                        } else { //No ground above
                            if(j!=numTilesY-1 && tileMap[i][j+1].getType() == Type.ground) { //Ground below
                                
                                if(i!=0 && tileMap[i-1][j].getType() == Type.ground) { //Ground below and left

                                    if(i!=numTilesX-1 && tileMap[i+1][j].getType() == Type.ground) { //Ground below, left and right
                                        tile.addBaseTexture(24);
                                    }
                                    else{ //Ground below and left
                                        tile.addBaseTexture(8);
                                    }

                                }
                                else { //No ground above or left, ground below
                                    if(i!=numTilesX-1 && tileMap[i+1][j].getType() == Type.ground) {//Ground below and right
                                        tile.addBaseTexture(9);
                                    }
                                    else { //Ground only below
                                        tile.addBaseTexture(17);
                                    }
                                }
                            }

                            else { //No ground above or below

                                if(i!=0 && tileMap[i-1][j].getType() == Type.ground) { //Ground left
                                    if(i!=numTilesX-1 && tileMap[i+1][j].getType() == Type.ground) { //Ground left and right
                                        tile.addBaseTexture(2);
                                    }
                                    else {// Ground only left
                                        tile.addBaseTexture(16);
                                    }
                                }

                                else { //No ground above, below, or left
                                    if(i!=numTilesX-1 && tileMap[i+1][j].getType() == Type.ground) { //Ground only right
                                        tile.addBaseTexture(18);
                                    }
                                }

                            }
                        }

                        // Corners

                        if(i>0 && j>0 && tileMap[i-1][j-1].getType() == Type.ground && tileMap[i-1][j].getType() != Type.ground && tileMap[i][j-1].getType() != Type.ground) { //Ground top left
                            tile.addBaseTexture(28);
                        }

                        if(i<numTilesX-1 && j>0 && tileMap[i+1][j-1].getType() == Type.ground && tileMap[i+1][j].getType() != Type.ground && tileMap[i][j-1].getType() != Type.ground) { //Ground top left
                            tile.addBaseTexture(20);
                        }

                        if(i>0 && j<numTilesY-1 && tileMap[i-1][j+1].getType() == Type.ground && tileMap[i-1][j].getType() != Type.ground && tileMap[i][j+1].getType() != Type.ground) { //Ground top left
                            tile.addBaseTexture(4);
                        }

                        if(i<numTilesX-1 && j<numTilesY-1 && tileMap[i+1][j+1].getType() == Type.ground && tileMap[i+1][j].getType() != Type.ground && tileMap[i][j+1].getType() != Type.ground) { //Ground top left
                            tile.addBaseTexture(12);
                        }

                }

            }
        }
                
    }

    public void drawSelf(Graphics2D g) {
        if(hasChanged) {
            hasChanged = false;
            doBorders();
        }

        for (int j = 0; j < numTilesY; j++) {
            for (int i = 0; i < numTilesX; i++) {

                ArrayList<Integer> firstLayerIDs = tileMap[i][j].getBaseTextures();
                
                for(int k=0; k<firstLayerIDs.size(); k++) {
                    g.drawImage(tileset.getTexture(firstLayerIDs.get(k)), i * tileSize, j * tileSize, null);
                }
                for(int ID : tileMap[i][j].getLayersTextures()) {
                    g.drawImage(tileset.getTexture(ID), i * tileSize, j * tileSize, null);
                }

                if(tileMap[i][j].isHighlighted()){
                    g.drawImage(tileset.getTexture(32), i * tileSize, j * tileSize, null);
                }
 
            }
        }
        
    }
}

