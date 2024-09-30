import editor.EditorPanel;
import game.InGamePanel;
import java.awt.Dimension;
import java.awt.event.*;
import javax.swing.*;

public class MainWindow  extends JFrame{

    public MainWindow() {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        setPreferredSize(new Dimension(1000, 800));

        JMenuBar menuBar = new JMenuBar();
        JMenu modeMenu = new JMenu("Mode");
        JMenuItem gameMode = new JMenuItem("Game");
        JMenuItem editorMode = new JMenuItem("Editor");
        modeMenu.add(gameMode);
        modeMenu.add(editorMode);
        menuBar.add(modeMenu);
        setJMenuBar(menuBar);

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

        setContentPane(new InGamePanel());
        
        pack();
        setVisible(true);
    }

    public void switchToGame(){
        remove(getContentPane());
        setContentPane(new InGamePanel());
        revalidate();
        repaint();
    }

    public void switchToEditor(){
        remove(getContentPane());
        setContentPane(new EditorPanel(10,10));
        revalidate();
        repaint();
    }

}
