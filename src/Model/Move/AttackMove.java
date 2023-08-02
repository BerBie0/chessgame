package Model.Move;

import Model.Board.Board;
import Model.Pieces.Piece;
import Model.Player.Player;

import java.util.List;

public class AttackMove extends Move{
    public AttackMove(int np, int op, Piece pi, Player p, Board b) {
        super(np, op, pi, p, b);
    }

    @Override
    public void execute() throws Exception {
        Piece capturePiece = board.getPieceFromPosition(newPos);
        capture(capturePiece);
    }

    @Override
    public void undo(MoveLog moveLog) {
        List<Piece> playerWhoCapturedPiece = player.getCapturedPieces();
        Piece comeBackPiece = playerWhoCapturedPiece.get(playerWhoCapturedPiece.size() - 1);
        board.move(piece, oldPos);
        player.move(piece, oldPos);
        board.addPieceToBoard(comeBackPiece);
        player.move(comeBackPiece, newPos);
        board.move(comeBackPiece, newPos);
        player.getCapturedPieces().remove(comeBackPiece);
        moveLog.removeMove(this);
    }


    private void capture(Piece capturePiece) throws Exception {
        board.validateAttackMove(piece, newPos);
        board.removePieceFromBoard(capturePiece);
        board.move(piece, newPos);
        player.move(piece, newPos);
        player.addCapturedPiece(capturePiece);
    }


}
