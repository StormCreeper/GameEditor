import javax.swing.JFrame;
import java.awt.Dimension;

public class MainWindow  extends JFrame{

    public MainWindow() {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        setPreferredSize(new Dimension(1000, 800));
        
        pack();
        setVisible(true);
    }

}
