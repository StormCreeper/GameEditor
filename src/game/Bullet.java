package game;

import java.awt.Color;
import java.awt.Graphics2D;;

public class Bullet {
    int size;

    double x;
    double y;

    double vx;
    double vy;

    public Bullet(double x, double y, double vx, double vy, int size) {
        this.size = size;

        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
    }

    public void update(double deltaTime) {
        x += vx * deltaTime;
        y += vy * deltaTime;
    }

    public void drawSelf(Graphics2D g){
        g.setColor(Color.RED);
        g.fillOval((int) x - size/2, (int) y - size/2, size, size);
    }
}
