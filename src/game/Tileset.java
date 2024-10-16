package game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Tileset {

    public static BufferedImage getScaledInstance(BufferedImage img, int width, int height) {
        Image tex = img.getScaledInstance(width, height, Image.SCALE_DEFAULT);
        BufferedImage newTex = new BufferedImage(width, height, img.getType());

        Graphics g = newTex.getGraphics();
        g.drawImage(tex, 0, 0, null);
        g.dispose();

        return newTex;
    }

    private int tileSize; // A changer quand on aura la vraie map
    private BufferedImage tileTexture;
    private ArrayList<BufferedImage> textureArray = new ArrayList<>();
    private int nbTextures;

    public Tileset(int tileSize, int nbTextures) {

        this.tileSize = tileSize;
        this.nbTextures = nbTextures;

        try {
            tileTexture = ImageIO.read(new File("textures/tileSet_new.png"));
        } catch (IOException e) {
            System.out.println("Could not load tile textures");
        }
    }

    public void loadTextures() {

        for (int n = 0; n < nbTextures; n++) {
            int i = n % (tileTexture.getWidth() / tileSize);
            int j = n / (tileTexture.getWidth() / tileSize); // Correction of the formula

            BufferedImage tex = tileTexture.getSubimage(i * tileSize, j * tileSize, tileSize, tileSize);
            textureArray.add(tex);
        }

    }

    public void resizeTextures(int imageSize) {
        for(int n = 0; n < nbTextures; n++) {
            textureArray.set(n, getScaledInstance(textureArray.get(n), imageSize, imageSize));
        }
    }

    public int getNbTextures(){
        return nbTextures;
    }

    public ArrayList<BufferedImage> getTextureArray() {
        return textureArray;
    }

    public BufferedImage getTexture(int id) {
        return textureArray.get(id);
    }

    public int getTileSize() {
        return tileSize;
    }

}
