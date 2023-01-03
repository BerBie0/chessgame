package Controller;

import Model.Board.Board;
import Model.Pieces.Piece;
import Model.Player.Player;
import Model.utils.Color2;

public class GameController {
    /*-------------------------------------------ATTRIBUTS------------------------------------------------------------*/

    private final GameManager gameManager;



    /*-------------------------------------------CONSTRUCTORS---------------------------------------------------------*/

    public GameController(GameManager gmm) {
        gameManager = gmm;
    }

    /*---------------------------------------------GET SET------------------------------------------------------------*/

    public void setPlayerName(String name, Player player) {
        gameManager.setPlayerName(name, player);
    }

    public String getPlayerName(Color2 color) {
        return color == Color2.WHITE ? gameManager.getWhiteName() : gameManager.getBlackName();
    }

    public Board getBoard() {
        return gameManager.getBoard();
    }

    public Player getCurrentPlayer() {
        return gameManager.getCurrentPlayer();
    }

    public void changeTurn()
    {
        gameManager.setWhiteTurn(!gameManager.isWhitePlayer());
        gameManager.setBlackTurn(!gameManager.isBlackPlayer());
    }
    /*-------------------------------------------OVERRIDE METHOD------------------------------------------------------*/
    /*-------------------------------------------INTERFACE METHOD-----------------------------------------------------*/
    /*------------------------------------------------METHOD----------------------------------------------------------*/

    public void Game(int oldPos, int newPos, Piece piece) {
        while (!gameManager.isCheckMate() && !gameManager.isPat()) {
            do {
                gameManager.execute(oldPos, newPos, piece, gameManager.getCurrentPlayer(), gameManager.getBoard());
                if (gameManager.isCurrentPlayerCheck())
                    gameManager.undo();
            } while (gameManager.isCurrentPlayerCheck());

            gameManager.setWhiteTurn(!gameManager.isWhitePlayer());
            gameManager.setBlackTurn(!gameManager.isBlackPlayer());
        }
    }

    public void execute(int oldPos, int newPos, Piece piece, Player player, Board board) {
        gameManager.execute(oldPos, newPos, piece, player, board);
    }
    public void undo() {
        gameManager.undo();
    }
}
