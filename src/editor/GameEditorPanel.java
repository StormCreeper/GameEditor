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
                    if(automaticFilling) indexesForFilling = new boolean[tileMap.getNumX()][tileMap.getNumY()];
                    mousePressedOn((int) (p.x - offset.x), (int) (p.y - offset.y));
                } else {
                    lastPoint = e.getPoint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                lastX = -1;
                lastY = -1;
                if(automaticFilling) completeFilling();
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
        if(i<0 || i>=tileMap.getNumX() || j<0 || j>=tileMap.getNumY()) return;
        if(i != lastX || j != lastY) {
            lastX = i;
            lastY = j;
            changeTile(i,j);
            if(automaticFilling) indexesForFilling[i][j] = true;
            repaint();
        }
    }

    private void changeTile(int i,int j){
        int layer = parent.getSelectedLayer();
            if(layer == 0) 
                tileMap.setType(i, j, parent.getSelectedType());
            else
                tileMap.setTileLayers(i, j, parent.getSelectedTool(), layer-1);
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

    /**
     * Computes the automatic filling of the selected area
     */
    private void completeFilling(){
        int[][] boundsOnLines = new int[2][tileMap.getNumY()];
        for(int j = 0; j<tileMap.getNumY(); j++) {
            boundsOnLines[0][j] = tileMap.getNumX();
            boundsOnLines[1][j] = -1;
            for(int i = 0; i<tileMap.getNumX(); i++) {
                if(indexesForFilling[i][j]) {
                    boundsOnLines[0][j] = Math.min(boundsOnLines[0][j], i);
                    boundsOnLines[1][j] = Math.max(boundsOnLines[1][j], i);
                }
            }
        }
        for (int j = 0; j < tileMap.getNumY(); j++) {
            for(int i=boundsOnLines[0][j]; i<=boundsOnLines[1][j]; i++) {
                changeTile(i, j);
            }
        }
        int[][] boundsOnColumns = new int[tileMap.getNumX()][2];
        for(int i = 0; i<tileMap.getNumX(); i++) {
            boundsOnColumns[i][0] = tileMap.getNumY();
            boundsOnColumns[i][1] = -1;
            for(int j = 0; j<tileMap.getNumY(); j++) {
                if(indexesForFilling[i][j]) {
                    boundsOnColumns[i][0] = Math.min(boundsOnColumns[i][0], j);
                    boundsOnColumns[i][1] = Math.max(boundsOnColumns[i][1], j);
                }
            }
        }
        for (int i = 0; i < tileMap.getNumX(); i++) {
            for(int j=boundsOnColumns[i][0]; j<=boundsOnColumns[i][1]; j++) {
                changeTile(i, j);
            }
        }

        repaint();
    }
    
}
