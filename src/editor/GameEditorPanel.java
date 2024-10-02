package editor;

import game.Tilemap;
import game.Tileset;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComponent;

public class GameEditorPanel extends JComponent {
    private final EditorPanel parent;

    private Tilemap tileMap;
    private Tileset tileSet;

    public GameEditorPanel(EditorPanel parent, int width, int height, Tileset tileSet, Tilemap tileMap){
        this.parent = parent;

        this.tileSet = tileSet;
        this.tileMap = tileMap;

        addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent e){
                Point p = e.getPoint();
                mousePressedOn(p.x, p.y);             
                repaint();
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e){
                Point p = e.getPoint();
                mousePressedOn(p.x, p.y);
                repaint();
            }
        });
    }

    private void mousePressedOn(int px, int py){
        int tileSize = tileMap.getTileSize();
        int i = px/tileSize;
        int j = py/tileSize;
        tileMap.setTile(i, j, parent.getSelectedTextureId());
    }

    @Override
    public void paintComponent(Graphics g){
        tileMap.drawSelf((Graphics2D) g);
    }
}
