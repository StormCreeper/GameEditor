package editor;

import game.Tilemap;
import game.Tileset;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComponent;

public class GameEditorPanel extends JComponent {
    private Tilemap tileMap;
    private Tileset tileSet;

    public GameEditorPanel(int width, int height, Tileset tileSet){
        this.tileSet = tileSet;
        tileMap = new Tilemap(width, height, 50, tileSet);

        addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                repaint();
            }
        });
    }

    @Override
    public void paintComponent(Graphics g){
        tileMap.drawSelf((Graphics2D) g);
    }
}
