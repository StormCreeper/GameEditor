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
                    tileMap.doBorders();
                    repaint();
                } else {
                    lastPoint = e.getPoint();
                }
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    Point p = e.getPoint();
                    mousePressedOn((int) (p.x - offset.x), (int) (p.y - offset.y));
                    tileMap.doBorders();
                    repaint();
                } else {
                    offset.x += (e.getX() - lastPoint.getX());
                    offset.y += (e.getY() - lastPoint.getY());
                    lastPoint = e.getPoint();

                    tileMap.doBorders();
                    repaint();
                }
            }
        });
    }

    private void mousePressedOn(int px, int py) {
        int tileSize = tileMap.getTileSize();
        int i = px / tileSize;
        int j = py / tileSize;
        tileMap.setTile(i, j, parent.getSelectedTextureId());
    }

    @Override
    public void paintComponent(Graphics g) {
        g.translate((int) offset.x, (int) offset.y);
        tileMap.drawSelf((Graphics2D) g);
    }

    public void centerView() {
        offset = new Point2D.Double(0, 0);
        repaint();
    }
}
