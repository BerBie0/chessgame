package Controller;

import Model.Board.Board;
import Model.Move.IMove;
import Model.Move.MoveFactory;
import Model.Pieces.King;
import Model.Pieces.Piece;
import Model.utils.Color2;
import Model.Player.Player;

public class GameManager
{
    /*-------------------------------------------ATTRIBUTS------------------------------------------------------------*/

    private final Board board;
    private final  Player bPlayer;
    private final Player wPlayer;
    private IMove lastMove;
    private final MoveFactory moveFactory;



    /*-------------------------------------------CONSTRUCTORS---------------------------------------------------------*/

    public GameManager()
    {
        this.board = new Board();
        board.inializeBoard();
        this.bPlayer = new Player(Color2.BLACK, "nom1");
        this.wPlayer = new Player(Color2.WHITE, "nom2");
        this.moveFactory = new MoveFactory(board);
    }

    /*---------------------------------------------GET SET------------------------------------------------------------*/

    public Player getCurrentPlayer()
    {
        return wPlayer.isUrTurn() ? wPlayer : bPlayer;
    }

    public void setBlackTurn( boolean turn )
    {
        bPlayer.setUrTurn(turn);
    }

    public void setWhiteTurn( boolean turn )
    {
        wPlayer.setUrTurn(turn);
    }
    /*-------------------------------------------OVERRIDE METHOD------------------------------------------------------*/
    /*-------------------------------------------INTERFACE METHOD-----------------------------------------------------*/
    /*------------------------------------------------METHOD----------------------------------------------------------*/

    public boolean isCheckMate()
    {
        Color2 currentPlayerColor = getCurrentPlayer().getColor();
        King currentPlayerKing = (King) board.getKing(currentPlayerColor);
        return board.isCheck(currentPlayerColor) && !board.anyValidMove(currentPlayerColor);
    }

    //TODO
    public boolean isPat()
    {
        return false;
    }

    public void execute(int oldPos, int newPos, Piece piece, Player player, Board board)
    {
        IMove move = moveFactory.createMove(oldPos, newPos, piece, player, board);
        move.execute();
        lastMove = move;
    }

    public void undo()
    {
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
