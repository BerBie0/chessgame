package View;

import Controller.GameController;
import Model.Board.Board;
import Model.Move.MoveLog;
import Model.Player.Player;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainMenu extends JFrame {
    JPanel jpMainMenu = new JPanel();
    JPanel jpTitle = new JPanel();
    JPanel jpChose = new JPanel();
    JLabel title = new JLabel("Chess Game");
    JButton multiPlayer = new JButton("2 player");
    JButton exit = new JButton("Exit");
    JButton git = new JButton("Git");
    private Player wPlayer;
    private Player bPlayer;
    private Board board;
    private MoveLog moveLog;
    private GameController gm;

    public MainMenu(Player wPlayer, Player bPlayer, Board board, MoveLog moveLog, GameController gm) {
        super("Main menu");
        this.wPlayer = wPlayer;
        this.bPlayer = bPlayer;
        this. board = board;
        this.moveLog = moveLog;
        this.gm = gm;
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.setSize(800, 800);
        this.setLocationRelativeTo(null);
        jpMainMenu.setLayout(new GridLayout(3, 1));
        jpMainMenu.add(jpTitle);
        jpMainMenu.add(jpChose);
        this.add(jpMainMenu);
        mainMenu();
        multiPlayer.addActionListener(this::game2pEnterMenu);
    }

    private void mainMenu() {
        Border border = title.getBorder();
        Border margin = new EmptyBorder(50, 100, 100, 100);
        title.setSize(new Dimension(600, 100));
        title.setBorder(new CompoundBorder(border, margin));
        int fontSizeToUse = setSize(title);
        title.setFont(new Font("Serif", Font.PLAIN, fontSizeToUse));
        jpTitle.add(title);
        jpChose.setLayout(new BoxLayout(jpChose, BoxLayout.PAGE_AXIS));

        Border border2 = jpChose.getBorder();
        Border margin2 = new EmptyBorder(50, 0, 0, 100);
        Dimension dim = new Dimension(300, 100);
        jpChose.setBorder(new CompoundBorder(border2, margin2));
        multiPlayer.setMaximumSize(dim);
        multiPlayer.setAlignmentX(Component.RIGHT_ALIGNMENT);
        jpChose.add(multiPlayer);
        exit.setMaximumSize(dim);
        exit.setAlignmentX(Component.RIGHT_ALIGNMENT);
        jpChose.add(exit);
        git.setMaximumSize(dim);
        git.setAlignmentX(Component.RIGHT_ALIGNMENT);
        jpChose.add(git);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int clickedButton = JOptionPane.showConfirmDialog(MainMenu.this, "etes vous surs ?", "title", JOptionPane.YES_NO_OPTION);
                if (clickedButton == JOptionPane.YES_OPTION) {
                    dispose();
                    System.exit(0);
                }
            }
        });
    }
    private int setSize(JLabel title) {
        Font labelFont = title.getFont();
        String labelText = title.getText();
        int stringWidth = title.getFontMetrics(labelFont).stringWidth(labelText);
        int componentWidth = title.getWidth();
        double widthRatio = (double) componentWidth / (double) stringWidth;
        int newFontSize = (int) (labelFont.getSize() * widthRatio);
        int componentHeight = title.getHeight();
        int fontSizeToUse = Math.min(newFontSize, componentHeight);
        return fontSizeToUse;
    }
    private void game2pEnterMenu(ActionEvent event) {
        EnterNameMenu enterNameMenu = new EnterNameMenu(wPlayer, bPlayer, board, moveLog, gm);
        wPlayer.addObserver(enterNameMenu);
        bPlayer.addObserver(enterNameMenu);
        this.setVisible(false);
        enterNameMenu.setVisible(true);
    }


}
