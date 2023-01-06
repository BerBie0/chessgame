package Model.Move;

import Model.Board.Board;
import Model.Pieces.King;
import Model.Pieces.Pawn;
import Model.Pieces.Piece;
import Model.Player.Player;

public class MoveFactory {
    /*-------------------------------------------ATTRIBUTS------------------------------------------------------------*/



    /*-------------------------------------------CONSTRUCTORS---------------------------------------------------------*/

    public MoveFactory() {
    }



    /*---------------------------------------------GET SET------------------------------------------------------------*/
    /*-------------------------------------------OVERRIDE METHOD------------------------------------------------------*/
    /*-------------------------------------------INTERFACE METHOD-----------------------------------------------------*/
    /*------------------------------------------------METHOD----------------------------------------------------------*/

    public IMove createMove(int oldPos, int newPos, Piece piece, Player player, Board board) {
        if (piece instanceof King && Math.abs(oldPos - newPos) == 2) {
            return new CastleMove(oldPos, newPos, piece, player, board);
        }
        if (!board.isPositionOccupied(newPos))
            return new SimpleMove(oldPos, newPos, piece, player, board);
        if (board.isPositionOccupied(newPos))
            if (piece instanceof Pawn && (Math.abs(oldPos - newPos) == 9) || (Math.abs(oldPos - newPos)) == 11)
                return new AttackMove(oldPos, newPos, piece, player, board);
            else if (piece instanceof Pawn && (Math.abs(oldPos - newPos) == 10))
                throw new IllegalArgumentException("MoveFactory.java : can't capture pawn +10");
            else
                return new AttackMove(oldPos, newPos, piece, player, board);

        throw new IllegalArgumentException("MoveFactory.java : createCommand(int oldPos, int newPos, Piece piece, Player player, Board board) : " +
                "can't create move");
    }
}
