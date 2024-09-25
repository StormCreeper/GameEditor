import game.InGamePanel;
import java.awt.Dimension;
import javax.swing.JFrame;

public class MainWindow  extends JFrame{

    public MainWindow() {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        setPreferredSize(new Dimension(1000, 800));

        setContentPane(new InGamePanel());
        
        pack();
        setVisible(true);
    }

}
