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

    private enum Mode {
        GAME,
        EDITOR
    }

    private Mode mode = Mode.GAME;

    private Tileset tileSet;
    private Tilemap tileMap;
    private int mapWidth = 10;
    private int mapHeight = 10;

    private boolean debug = false;

    private String levelPath = "";

    public MainWindow() {

        instance = this;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        setPreferredSize(new Dimension(1000, 800));

        JMenuBar menuBar = new JMenuBar();
        JMenu modeMenu = new JMenu("Mode");
        JMenu fileMenu = new JMenu("Level");
        JMenu helpMenu = new JMenu("Help");
        JMenuItem helpMenuItem = new JMenuItem("Help");
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
        helpMenu.add(helpMenuItem);
        menuBar.add(modeMenu);
        menuBar.add(fileMenu);
        menuBar.add(helpMenu);
        setJMenuBar(menuBar);

        //Init variables that are useful for both modes :
        tileSet = new Tileset(16,64);
        tileSet.loadTextures();
        tileSet.resizeTextures(50);
        tileMap = new Tilemap(mapWidth, mapHeight, 50, tileSet);
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

        final JComponent[] inputs = new JComponent[] {
            new JLabel("Arrow Keys to move"),
            new JLabel("S to shoot"),
            new JLabel("D to pick a bullet"),
            new JLabel("Bullets can act on blocks:"),
            new JLabel(new ImageIcon("textures/chara_new.png"))
        };

        helpMenuItem.addActionListener(e -> {
            JOptionPane.showConfirmDialog(this, inputs, "Help menu", JOptionPane.PLAIN_MESSAGE);
        });


        setContentPane(new InGamePanel(tileSet, tileMap, this));
        
        pack();
        setVisible(true);
    }

    public void resetLevel() {
        if(levelPath == "") {
            tileMap = new Tilemap(mapWidth, mapHeight, 50, tileSet);
        } else {
            tileMap.loadFromFile(levelPath);
        }

        switchToGame();
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
        setContentPane(new EditorPanel(mapWidth,mapHeight, tileSet, tileMap));
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
