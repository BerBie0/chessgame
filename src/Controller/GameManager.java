package Controller;

import Model.Board.Board;
import Model.utils.Color2;
import Model.Player.Player;

public class GameManager {
    private Board board;
    private Player bPlayer;
    private Player wPlayer;

    public GameManager(Board board, Player b, Player w)
    {
        this.board = board;
        this.bPlayer = b;
        this.wPlayer = w;
    }

    /*TEST*/
    public static void main(String[] args) {
        Board b = new Board();
        Player bPlayer = new Player(Color2.BLACK, "nom1");
        Player wPlayer = new Player(Color2.WHITE, "nom2");
        //b.displayBoard();
        b.inializeBoard();
        b.displayBoard();
        b.move(b.getPieces().get(0), 71);
        b.displayBoard();
        System.out.println();
        System.out.println(b.getPieces());
    }
}
