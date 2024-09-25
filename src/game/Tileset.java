package game;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.util.ArrayList;

public class Tileset {

    private int tileSize; //A changer quand on aura la vraie map
    private BufferedImage tileTexture;
    private ArrayList<BufferedImage> textureArray = new ArrayList<BufferedImage>();
    private int nbTextures;

    Tileset(int tileSize, int nbTextures) {
        
        this.tileSize = tileSize;
        this.nbTextures = nbTextures;

        try {
            tileTexture = ImageIO.read(new File("textures/tileSet.png"));
        } catch (Exception e) {
            System.out.println("Could not load tile textures");
        }
    }

    public void loadTextures() {

        for(int n=0; n<nbTextures; n++) {
            int i = n%(tileTexture.getWidth()/tileSize);
            int j = n/(tileTexture.getHeight()/tileSize);

            BufferedImage tex = tileTexture.getSubimage(i*tileSize, j*tileSize, tileSize, tileSize);
            textureArray.add(tex);
        }

    }

    public ArrayList<BufferedImage> getTextureArray() {
        return textureArray;
    }

    public int getTileSize() {
        return tileSize;
    }

}
