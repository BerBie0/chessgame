package Model.Move;

import Model.Board.Board;
import Model.Pieces.King;
import Model.Pieces.Piece;
import Model.Pieces.Rook;
import Model.Player.Player;
import Model.Utils.Color2;

public class CastleMove extends Move{
    public CastleMove(int np, int op, Piece pi, Player p, Board b) {
        super(np, op, pi, p, b);
    }

    @Override
    public void execute() throws Exception {
        if (piece.getColor() != player.getColor())
            throw new IllegalArgumentException("CastleMove.java : The piece is not yours");

        if (player.isWhite()) {
            if (newPos == 97) {
                executeCastle(98, 96, 97);
            } else if (newPos == 93) {
                executeCastle(91, 93, 94);
            }
        } else {
            if (newPos == 27) {
                executeCastle(28, 27, 26);
            } else if (newPos == 23) {
                executeCastle(21, 23, 24);
            }
        }
    }
    private void executeCastle(int rookPos, int kingPos, int newRookPos) throws Exception {
        Piece king = getKing();
        Piece rook = board.getPieceFromPosition(rookPos);

        if (canCastle(king, rook)) {
            board.move(rook, newRookPos);
            board.move(king, kingPos);
            player.move(rook, newRookPos);
            player.move(king, kingPos);
        } else {
            throw new IllegalArgumentException("CastleMove.java : execute() : can't castle");
        }
    }

    @Override
    public void undo(MoveLog moveLog) {
        if (player.isWhite()) {
            if (board.getKing(Color2.WHITE).getPosition() == 97) {
                undoCastle(96, 95, 98);
            } else if (board.getKing(Color2.WHITE).getPosition() == 92) {
                undoCastle(94, 95, 91);
            }
        } else {
            if (board.getKing(Color2.BLACK).getPosition() == 27) {
                undoCastle(26, 25, 28);
            } else if (board.getKing(Color2.BLACK).getPosition() == 22) {
                undoCastle(24, 25, 21);
            }
        }
    }

    private void undoCastle(int rookPos, int kingPos, int originalRookPos) {
        Piece king = getKing();
        Piece rook = board.getPieceFromPosition(rookPos);

        board.move(king, kingPos);
        board.move(rook, originalRookPos);
        player.move(king, kingPos);
        player.move(rook, originalRookPos);
    }
    private Piece getKing() {
        return player.isWhite() ? board.getKing(Color2.WHITE) : board.getKing(Color2.BLACK);
    }
    private boolean canCastle(Piece king, Piece rook) throws Exception {
        if (!(rook instanceof Rook)) {
            return false;
        }
        if (!(king instanceof King)) {
            return false;
        }
        if (((King) king).getHasMovedOnce()) {
            return false;
        }
        board.validateAttackMove(piece, newPos);
        return true;
    }


}
