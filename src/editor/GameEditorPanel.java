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

    public GameEditorPanel(EditorPanel parent, int width, int height, Tileset tileSet){
        this.parent = parent;

        this.tileSet = tileSet;
        tileMap = new Tilemap(width, height, 50, tileSet);

        addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                Point p = e.getPoint();
                handleClick(p.x, p.y);             
                repaint();
            }
        });
    }

    private void handleClick(int px, int py){
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
