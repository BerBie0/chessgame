package Model.Move;

import Model.Board.Board;
import Model.Pieces.Piece;
import Model.Player.Player;

public class SimpleMove extends Move{
    public SimpleMove(int np, int op, Piece pi, Player p, Board b) {
        super(np, op, pi, p, b);
    }

    @Override
    public void execute() throws Exception {
        board.validateSimpleMove(piece, newPos);
        board.move(piece, newPos);
        player.move(piece, newPos);

    }

    @Override
    public void undo(MoveLog moveLog) {
        board.move(piece, oldPos);
        player.move(piece, oldPos);
    }

}
