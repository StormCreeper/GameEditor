package editor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ToolBar extends JPanel {

    private final EditorPanel parent;

    private JButton addLineButton = new JButton("Add Line");
    private JButton addColumnButton = new JButton("Add Column");

    ToolBar(EditorPanel parent) {
        this.parent = parent;

        addLineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.addLine();
                parent.repaint();
            }
        });

        this.add(addLineButton);
        this.add(addColumnButton);
    }
}
