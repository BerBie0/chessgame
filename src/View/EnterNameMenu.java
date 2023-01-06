package View;

import javax.swing.*;
import java.awt.*;

import Controller.GameManager;
import Model.Board.Board;
import Model.utils.MoveLog;
import Model.Player.IPlayerObserver;
import Model.Player.Player;

public class EnterNameMenu extends JFrame implements IPlayerObserver {
    JFrame frame;
    JTextField whiteName;
    JTextField blackName;
    JButton button;
    JLabel label, label2, label3;
    Player wPlayer;
    Player bPlayer;
    Board board;
    GameManager gameManager;
    MoveLog moveLog;
    private int cpt = 0;

    public EnterNameMenu(Player wPlayer, Player bPlayer, Board board, MoveLog moveLog, GameManager GameManager) {
        this.wPlayer = wPlayer;
        this.bPlayer = bPlayer;
        this.board = board;
        this.gameManager = GameManager;
        this.moveLog = moveLog;
        this.init();
    }

    public void init() {
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
            setPlayerName(blackName.getText(), bPlayer);
            setPlayerName(whiteName.getText(), wPlayer);
        });
        frame.getContentPane().add(label);
        frame.getContentPane().add(label2);
        frame.getContentPane().add(label3);

        frame.setVisible(true);
        frame.pack();

        frame.setLocationRelativeTo(null);
    }

    public void setPlayerName(String name, Player player) {
        gameManager.setPlayerName(name, player);
    }

    @Override
    public void updatePlayerName() {
        cpt++;
        label.setText(wPlayer.getName());
        label2.setText(bPlayer.getName());
        frame.setVisible(false);

        if (cpt > 1) {
            GameFrame2 gameFrame2 = GameFrame2.createInstance(wPlayer, bPlayer, board, moveLog, gameManager);
            board.addObserver(gameFrame2);
            bPlayer.addObserverGame(gameFrame2);
            wPlayer.addObserverGame(gameFrame2);
            moveLog.addObserver(gameFrame2);
            gameFrame2.setVisible(true);
        }
    }
}
