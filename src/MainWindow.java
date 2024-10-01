import editor.EditorPanel;
import game.InGamePanel;
import game.Tilemap;
import game.Tileset;

import java.awt.Dimension;
import java.awt.event.*;
import javax.swing.*;

public class MainWindow  extends JFrame{

    private Tileset tileSet;
    private Tilemap tileMap;
    private int mapWidth = 10;
    private int mapHeight = 10;

    public MainWindow() {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        setPreferredSize(new Dimension(1000, 800));

        JMenuBar menuBar = new JMenuBar();
        JMenu modeMenu = new JMenu("Mode");
        JMenu fileMenu = new JMenu("File");
        JMenuItem gameMode = new JMenuItem("Game");
        JMenuItem editorMode = new JMenuItem("Editor");
        modeMenu.add(gameMode);
        modeMenu.add(editorMode);
        JMenuItem saveFile = new JMenuItem("Save");
        JMenuItem loadFile = new JMenuItem("Load");
        fileMenu.add(saveFile);
        fileMenu.add(loadFile);
        menuBar.add(modeMenu);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        //Init variables that are useful for both modes :
        tileSet = new Tileset(16,7);
        tileSet.loadTextures();
        tileMap = new Tilemap(mapWidth, mapHeight, 50, tileSet);

        gameMode.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                System.out.println("Switch to game");
                switchToGame();
            }
        });

        editorMode.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                System.out.println("Switch to editor");
                switchToEditor();
            }
        });

        saveFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                System.out.println("Saving level");
            }
        });

        loadFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                System.out.println("Loading level");
            }
        });

        setContentPane(new InGamePanel(tileSet, tileMap));
        
        pack();
        setVisible(true);
    }

    public void switchToGame(){
        remove(getContentPane());
        setContentPane(new InGamePanel(tileSet, tileMap));
        revalidate();
        repaint();
    }

    public void switchToEditor(){
        remove(getContentPane());
        setContentPane(new EditorPanel(mapWidth,mapHeight, tileSet, tileMap));
        revalidate();
        repaint();
    }

}
