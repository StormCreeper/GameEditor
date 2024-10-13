package game;

import game.Tile.Type;
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

                ArrayList<Integer> firstLayerIDs = tileMap[i][j].getBaseTextures();
                
                for(int k=0; k<firstLayerIDs.size(); k++) {
                    g.drawImage(tileset.getTexture(firstLayerIDs.get(k)), i * tileSize, j * tileSize, tileSize, tileSize, null);
                }

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

    public void doBorders() {
        
        //Base Texture
        for (int j = 0; j < numTilesY; j++) {
            for (int i = 0; i < numTilesX; i++) {
                Tile tile = tileMap[i][j];

                // ***** A ENLEVER : random pour tester *****
                int rand = (int) (Math.random()*3);
                switch(rand){
                    case 0:
                        tile.setBaseTile(Type.ground);
                        break;
                    case 1:
                        tile.setBaseTile(Type.water);
                        break;
                    case 2:
                        tile.setBaseTile(Type.lava);
                        break;
                }
                // *********

                tile.resetBaseTexture();
                switch(tile.getBaseTile()) {
                    case ground:
                        tile.addBaseTexture(5);
                        break;
                    case water:
                        tile.addBaseTexture(13);
                        break;
                    case lava:
                        tile.addBaseTexture(21);
                        break;
                }
            }
        }

        //Borders
        for (int j=0; j < numTilesY; j++) {
            for (int i = 0; i<numTilesX; i++) {
                Tile tile = tileMap[i][j];
                
                //We only need to add borders on water or lava :
                if(tile.getBaseTile() == Type.water || tile.getBaseTile() == Type.lava) {

                        if(j!=0 && tileMap[i][j-1].getBaseTile() == Type.ground) { //Ground above

                            if(j!=numTilesY-1 && tileMap[i][j+1].getBaseTile() == Type.ground) { //Ground above and below
                               
                                if(i!=0 && tileMap[i-1][j].getBaseTile() == Type.ground) { //Ground above, below, and left
                               
                                    if(i!=numTilesX-1 && tileMap[i+1][j].getBaseTile() == Type.ground) { //Ground all around
                                        tile.addBaseTexture(1);
                                    }
                                    else { //Ground around, below and left
                                        tile.addBaseTexture(25);
                                    }
                                }
                                else {
                                    if(i!=numTilesX-1 && tileMap[i+1][j].getBaseTile() == Type.ground) { //Ground above, below and right
                                        tile.addBaseTexture(27);
                                    }
                                    else { //Ground above and below
                                        tile.addBaseTexture(3);
                                    }
                                }
                            }

                            else { //Ground above, no ground below
                                if(i!=0 && tileMap[i-1][j].getBaseTile() == Type.ground) { //Ground above and left
                                    if(i!=numTilesX-1 && tileMap[i+1][j].getBaseTile() == Type.ground) { //Ground above, left and right
                                        tile.addBaseTexture(26);
                                    }
                                    else { //Ground above and left
                                        tile.addBaseTexture(11);
                                    }
                                }
                                else {
                                    if(i!=numTilesX-1 && tileMap[i+1][j].getBaseTile() == Type.ground) {//Ground above and right
                                        tile.addBaseTexture(10);
                                    }
                                    else { //Ground only above
                                        tile.addBaseTexture(19);
                                    }
                                }
                            }
                        } else { //No ground above
                            if(j!=numTilesY-1 && tileMap[i][j+1].getBaseTile() == Type.ground) { //Ground below
                                
                                if(i!=0 && tileMap[i-1][j].getBaseTile() == Type.ground) { //Ground below and left

                                    if(i!=numTilesX-1 && tileMap[i+1][j].getBaseTile() == Type.ground) { //Ground below, left and right
                                        tile.addBaseTexture(24);
                                    }
                                    else{ //Ground below and left
                                        tile.addBaseTexture(8);
                                    }

                                }
                                else { //No ground above or left, ground below
                                    if(i!=numTilesX-1 && tileMap[i+1][j].getBaseTile() == Type.ground) {//Ground below and right
                                        tile.addBaseTexture(9);
                                    }
                                    else { //Ground only below
                                        tile.addBaseTexture(17);
                                    }
                                }
                            }

                            else { //No ground above or below

                                if(i!=0 && tileMap[i-1][j].getBaseTile() == Type.ground) { //Ground left
                                    if(i!=numTilesX-1 && tileMap[i+1][j].getBaseTile() == Type.ground) { //Ground left and right
                                        tile.addBaseTexture(2);
                                    }
                                    else {// Ground only left
                                        tile.addBaseTexture(16);
                                    }
                                }

                                else { //No ground above, below, or left
                                    if(i!=numTilesX-1 && tileMap[i+1][j].getBaseTile() == Type.ground) { //Ground only right
                                        tile.addBaseTexture(18);
                                    }
                                }

                            }
                        }

                        if(i>0 && j>0 && tileMap[i-1][j-1].getBaseTile() == Type.ground && tileMap[i-1][j].getBaseTile() != Type.ground && tileMap[i][j-1].getBaseTile() != Type.ground) { //Ground top left
                            tile.addBaseTexture(28);
                        }

                        if(i<numTilesX-1 && j>0 && tileMap[i+1][j-1].getBaseTile() == Type.ground && tileMap[i+1][j].getBaseTile() != Type.ground && tileMap[i][j-1].getBaseTile() != Type.ground) { //Ground top left
                            tile.addBaseTexture(20);
                        }

                        if(i>0 && j<numTilesY-1 && tileMap[i-1][j+1].getBaseTile() == Type.ground && tileMap[i-1][j].getBaseTile() != Type.ground && tileMap[i][j+1].getBaseTile() != Type.ground) { //Ground top left
                            tile.addBaseTexture(4);
                        }

                        if(i<numTilesX-1 && j<numTilesY-1 && tileMap[i+1][j+1].getBaseTile() == Type.ground && tileMap[i+1][j].getBaseTile() != Type.ground && tileMap[i][j+1].getBaseTile() != Type.ground) { //Ground top left
                            tile.addBaseTexture(12);
                        }

                }

            }
        }
            //TODO : misc corners
                
    }
}

