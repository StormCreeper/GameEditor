package editor;

import game.Tilemap;
import game.Tileset;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;

public class GameEditorPanel extends JComponent {
    private final EditorPanel parent;

    private final Tilemap tileMap;
    
    // Last pressed Tile on editing
    private int lastX= -1;
    private int lastY= -1;

    // If the automatic filling is selected
    boolean automaticFilling = false;
    // Array of selected indexes for automatic filling
    private boolean[][] indexesForFilling;

    // Variables for dragging the map
    private Point2D lastPoint = new Point2D.Double(0, 0);
    private Point2D.Double offset = new Point2D.Double(0, 0);

    public GameEditorPanel(EditorPanel parent, int width, int height, Tileset tileSet, Tilemap tileMap) {
        this.parent = parent;

        this.tileMap = tileMap;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    Point p = e.getPoint();
                    mousePressedOn((int) (p.x - offset.x), (int) (p.y - offset.y));
                } else {
                    lastPoint = e.getPoint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                lastX = -1;
                lastY = -1;
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    Point p = e.getPoint();
                    mousePressedOn((int) (p.x - offset.x), (int) (p.y - offset.y));
                } else {
                    offset.x += (e.getX() - lastPoint.getX());
                    offset.y += (e.getY() - lastPoint.getY());
                    lastPoint = e.getPoint();

                    repaint();
                }
            }
        });
    }

    private void mousePressedOn(int px, int py) {
        int tileSize = tileMap.getTileSize();
        int i = px / tileSize;
        int j = py / tileSize;
        if(i != lastX || j != lastY) {
            lastX = i;
            lastY = j;
            int layer = parent.getSelectedLayer();
            if(layer == 0) 
                tileMap.setType(i, j, parent.getSelectedType());
            else
                tileMap.setTileLayers(i, j, parent.getSelectedTool(), layer-1);
            repaint();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(new Color(220, 220, 220));
        g.fillRect(0, 0, parent.getWidth(), parent.getHeight());
        g.translate((int) offset.x, (int) offset.y);
        tileMap.drawSelf((Graphics2D) g);
    }

    public void centerView() {
        offset = new Point2D.Double(0, 0);
        repaint();
    }

    public void setAutomaticFilling(boolean b){
        automaticFilling = b;
    }
}
