package Model.Move;

import Model.Board.Board;
import Model.Pieces.King;
import Model.Pieces.Pawn;
import Model.Pieces.Piece;
import Model.Player.Player;

public class MoveFactory {


    public MoveFactory(Board board) {
    }

    public IMove createMove(int newPos, int oldPos, Piece piece, Player player, Board board) {
        if (piece instanceof King && Math.abs(newPos - oldPos) == 2)
            return new CastleMove(newPos, oldPos, piece, player, board);
        if (!board.isPositionOccupied(newPos))
            return new SimpleMove(newPos, oldPos, piece, player, board);
        if (board.isPositionOccupied(newPos))
            if (piece instanceof Pawn && (Math.abs(newPos - oldPos) == 9) || (Math.abs(newPos - oldPos)) == 11)
                return new AttackMove(newPos, oldPos, piece, player, board);
            else if (piece instanceof Pawn && (Math.abs(newPos - oldPos) == 10))
                throw new IllegalArgumentException("can't capture pawn +10");
            else
                return new AttackMove(newPos, oldPos, piece, player, board);
        throw new IllegalArgumentException("can't create move");
    }

}
