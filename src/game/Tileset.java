package game;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.util.ArrayList;

public class Tileset {

    private int tileSize = 16; //A changer quand on aura la vraie map
    private BufferedImage tileTexture;
    private ArrayList<BufferedImage> textureArray;

    Tileset() {
        try {
            tileTexture = ImageIO.read(new File("textures/tileSet.png"));
        } catch (Exception e) {
            System.out.println("Could not load tile textures");
        }
    }

}
