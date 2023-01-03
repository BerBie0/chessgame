package Controller;

import Model.Board.Board;
import Model.Move.IMove;
import Model.Move.MoveFactory;
import Model.Pieces.King;
import Model.Pieces.Piece;
import Model.utils.Color2;
import Model.Player.Player;

import java.util.LinkedList;

public class GameManager {
    /*-------------------------------------------ATTRIBUTS------------------------------------------------------------*/

    private final Board board;
    private final Player bPlayer;
    private final Player wPlayer;
    private IMove lastMove;
    private final MoveFactory moveFactory;



    /*-------------------------------------------CONSTRUCTORS---------------------------------------------------------*/

    public GameManager(Player wPlayer, Player bPlayer, Board board) {
        this.board = board;
        //board.inializeBoard();
        this.bPlayer = bPlayer;
        this.wPlayer = wPlayer;
        this.moveFactory = new MoveFactory(board);
    }

    /*---------------------------------------------GET SET------------------------------------------------------------*/

    public Player getCurrentPlayer() {
        return wPlayer.isUrTurn() ? wPlayer : bPlayer;
    }

    public void setBlackTurn(boolean turn) {
        bPlayer.setUrTurn(turn);
    }

    public void setWhiteTurn(boolean turn) {
        wPlayer.setUrTurn(turn);
    }

    public Board getBoard() {
        return board;
    }

    public void setPlayerName(String name, Player player) {
        if (player.isWhite()) {
            wPlayer.setName(name);
        } else {
            bPlayer.setName(name);
        }
    }

    public String getWhiteName() {
        return wPlayer.getName();
    }
    public String getBlackName() {
        return bPlayer.getName();
    }
    public Player getBlackPlayer() {
        return bPlayer;
    }
    public Player getWhitePlayer() {
        return wPlayer;
    }

    /*-------------------------------------------OVERRIDE METHOD------------------------------------------------------*/
    /*-------------------------------------------INTERFACE METHOD-----------------------------------------------------*/
    /*------------------------------------------------METHOD----------------------------------------------------------*/

    public boolean isWhitePlayer() {
        return wPlayer.isUrTurn();
    }

    public boolean isBlackPlayer() {
        return bPlayer.isUrTurn();
    }

    public boolean isCheckMate() {
        Color2 currentPlayerColor = getCurrentPlayer().getColor();
        King currentPlayerKing = (King) board.getKing(currentPlayerColor);
        return board.isCheck(currentPlayerColor) && !board.anyValidMove(currentPlayerColor);
    }

    public boolean isCurrentPlayerCheck() {
        return board.isCheck(board.getKing(getCurrentPlayer().getColor()).getColor());
    }

    //TODO
    public boolean isPat() {
        return false;
    }

    public IMove execute(int oldPos, int newPos, Piece piece, Player player, Board board) {
        IMove move = moveFactory.createMove(oldPos, newPos, piece, player, board);
        move.execute();
        lastMove = move;
        return move;
    }

    public void undo() {
        lastMove.undo();
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
