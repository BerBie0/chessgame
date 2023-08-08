package View;

import Model.Utils.Color2;

import javax.swing.*;
import java.awt.*;

public class EndMenu extends JFrame{

    JFrame frame;
    JLabel winnerName;

    public EndMenu(Color2 color) {
        this.winnerName = new JLabel("le gagnant est : " + color.toString());
        init();
    }

    public void init() {
        frame = new JFrame("exemple poo");

        frame.setLayout(new FlowLayout());
        frame.getContentPane().add(winnerName);

        frame.setVisible(true);
        frame.pack();
        frame.setLocationRelativeTo(null);
    }
}
