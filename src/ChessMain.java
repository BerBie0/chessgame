import Controller.GameController;
import Model.Board.Board;
import Model.Move.MoveLog;
import Model.Player.Player;
import Model.Utils.Color2;
import View.GameFrame2;
import View.MainMenu;

public class ChessMain {
    public static void main(String[] args) {
        Player wPlayer = new Player(Color2.WHITE, "p1");
        Player bPlayer = new Player(Color2.BLACK, "p2");
        Board board = new Board();
        board.initializeBoard();
        MoveLog moveLog = new MoveLog();

        GameController gm = new GameController(wPlayer, bPlayer, board, moveLog);

        MainMenu mainMenu = new MainMenu(wPlayer, bPlayer, board, moveLog, gm);
        mainMenu.setVisible(true);
    }

}
