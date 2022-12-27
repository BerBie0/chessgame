package View;

import javax.swing.*;
import java.awt.*;

import Controller.GameController;
import Model.Player.Player;

public class EnterNameMenu extends JFrame implements IPlayerObserver {
    JFrame frame;
    JTextField whiteName;
    JTextField blackName;
    JButton button;
    JLabel label, label2, label3;
    Player wPlayer;
    Player bPlayer;
    GameController gameController;

    public EnterNameMenu(Player wPlayer, Player bPlayer, GameController gameController)
    {
        this.wPlayer = wPlayer;
        this.bPlayer = bPlayer;
        this.gameController = gameController;
        this.init();
    }
    public JButton getButton() {
        return button;
    }

    public JLabel getLabel() {
        return label;
    }

    public JLabel getLabel2() {
        return label2;
    }

    public JLabel getLabel3() {
        return label3;
    }

    public void init()
    {
        frame = new JFrame("exemple poo");
        whiteName = new JTextField("white");
        whiteName.setColumns(10);
        blackName = new JTextField("black");
        blackName.setColumns(10);
        button = new JButton("ok");
        label = new JLabel("label1");
        label2 = new JLabel("label2");
        label3 = new JLabel("label3");

        frame.setLayout(new FlowLayout());
        frame.getContentPane().add(whiteName);
        frame.getContentPane().add(blackName);
        frame.getContentPane().add(button);
        button.addActionListener(e -> {
            setPlayerName(whiteName.getText(), wPlayer);
            setPlayerName(blackName.getText(), bPlayer);
        });
        frame.getContentPane().add(label);
        frame.getContentPane().add(label2);
        frame.getContentPane().add(label3);

        frame.setVisible(true);
        frame.pack();

        frame.setLocationRelativeTo(null);
    }

    public void setPlayerName(String name, Player player)
    {
        gameController.setPlayerName(name, player);
    }

    @Override
    public void updatePlayerName()
    {
        label.setText( wPlayer.getName() );
        label2.setText( bPlayer.getName() );
        label3.setText("ok ?");
    }
}
