package main;

import java.awt.image.BufferedImage;
import java.awt.Image;
import java.awt.Graphics;

public class Utils {

    public static BufferedImage getScaledInstance(BufferedImage img, int width, int height) {
        Image tex = img.getScaledInstance(width, height, Image.SCALE_DEFAULT);
        BufferedImage newTex = new BufferedImage(width, height, img.getType());

        Graphics g = newTex.getGraphics();
        g.drawImage(tex, 0, 0, null);
        g.dispose();

        return newTex;
    }
}
