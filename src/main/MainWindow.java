package main;
import editor.EditorPanel;
import game.InGamePanel;
import game.Tilemap;
import game.Tileset;
import java.awt.Dimension;
import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;

public class MainWindow  extends JFrame{

    public static MainWindow instance;

    public enum Mode {
        GAME,
        EDITOR
    }

    private Mode mode = Mode.GAME;

    private final Tileset tileSet;
    private Tilemap tileMap;

    private boolean debug = false;

    private String levelPath = "maps/level_1.txt";

    public MainWindow() {

        instance = this;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        setPreferredSize(new Dimension(1000, 800));

        JMenuBar menuBar = new JMenuBar();
        JMenu modeMenu = new JMenu("Mode");
        JMenu fileMenu = new JMenu("File");
        JMenuItem gameMode = new JMenuItem("Game");
        JMenuItem editorMode = new JMenuItem("Editor");
        JCheckBoxMenuItem debugMode = new JCheckBoxMenuItem("Toogle Debug");
        modeMenu.add(gameMode);
        modeMenu.add(editorMode);
        modeMenu.add(debugMode);
        JMenuItem saveFile = new JMenuItem("Save");
        JMenuItem loadFile = new JMenuItem("Load");
        fileMenu.add(saveFile);
        fileMenu.add(loadFile);
        menuBar.add(modeMenu);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        //Init variables that are useful for both modes :
        tileSet = new Tileset(16,64);
        tileSet.loadTextures();
        tileSet.resizeTextures(50);
        tileMap = new Tilemap(0,0, 50, tileSet);
        tileMap.loadFromFile(levelPath);
        tileMap.change();

        debugMode.addActionListener(e -> setDebug(debugMode.isSelected()));

        gameMode.addActionListener(e -> switchMode(Mode.GAME));

        editorMode.addActionListener(e -> switchMode(Mode.EDITOR));

        saveFile.addActionListener(e -> {
            System.out.println("Saving level");

            JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView());
            fileChooser.setCurrentDirectory(new File("maps/"));
            int returnValue = fileChooser.showSaveDialog(null);
            
            String filePath = null;
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                filePath = file.getAbsolutePath();
            }
            tileMap.save(filePath);
        });

        loadFile.addActionListener(e -> {
            System.out.println("Loading level");

            JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView());
            fileChooser.setCurrentDirectory(new File("maps/"));

            int returnValue = fileChooser.showOpenDialog(null);

            String filePath=null;
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                filePath = file.getAbsolutePath();
                levelPath = filePath;
                resetLevel();
            }
    

            repaint();
            
        });

        setContentPane(new InGamePanel(tileSet, tileMap, this));
        
        pack();
        setVisible(true);
    }

    public void resetLevel() {
        if(levelPath == "") {
            tileMap = new Tilemap(0,0, 50, tileSet);
        } else {
            tileMap.loadFromFile(levelPath);
        }
    }

    public void switchMode(Mode mode){
        if(mode != this.mode){
            this.mode = mode;
            if(mode == Mode.GAME){
                switchToGame();
            }else{
                switchToEditor();
            }
        }
    }

    private void switchToGame(){
        System.out.println("Switching to game");
        remove(getContentPane());
        setContentPane(new InGamePanel(tileSet, tileMap, this));
        revalidate();
        repaint();
    }

    private void switchToEditor(){
        System.out.println("Switching to editor");
        ((InGamePanel)getContentPane()).stopGame();
        remove(getContentPane());
        setContentPane(new EditorPanel(tileSet, tileMap));
        revalidate();
        repaint();
    }

    public boolean isDebug(){
        return debug;
    }

    public void setDebug(boolean debug){
        this.debug = debug;
    }

}
