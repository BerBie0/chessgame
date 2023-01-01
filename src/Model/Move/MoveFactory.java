package Model.Move;

import Model.Board.Board;
import Model.Pieces.King;
import Model.Pieces.Piece;
import Model.Player.Player;

public class MoveFactory {
    /*-------------------------------------------ATTRIBUTS------------------------------------------------------------*/

    private Board board;



    /*-------------------------------------------CONSTRUCTORS---------------------------------------------------------*/

    public MoveFactory(Board b) {
        board = b;
    }



    /*---------------------------------------------GET SET------------------------------------------------------------*/
    /*-------------------------------------------OVERRIDE METHOD------------------------------------------------------*/
    /*-------------------------------------------INTERFACE METHOD-----------------------------------------------------*/
    /*------------------------------------------------METHOD----------------------------------------------------------*/

    public IMove createMove(int oldPos, int newPos, Piece piece, Player player, Board board) {
        if (piece instanceof King && Math.abs(oldPos - newPos) == 2) {
            System.out.println("castleMove");
            return new CastleMove(oldPos, newPos, piece, player, board);
        }
        if (!board.isPositionOccupied(newPos))
            return new SimpleMove(oldPos, newPos, piece, player, board);
        if (board.isPositionOccupied(newPos))
            return new AttackMove(oldPos, newPos, piece, player, board);


        throw new IllegalArgumentException("MoveFactory.java : createCommand(int oldPos, int newPos, Piece piece, Player player, Board board) : " +
                "can't create move");
    }
}
