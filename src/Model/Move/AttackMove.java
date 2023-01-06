package Model.Move;

import Model.Board.Board;
import Model.utils.MoveLog;
import Model.Pieces.Piece;
import Model.Player.Player;

import java.util.List;

public class AttackMove extends Move {
    /*-------------------------------------------ATTRIBUTS------------------------------------------------------------*/
    /*-------------------------------------------CONSTRUCTORS---------------------------------------------------------*/

    public AttackMove(int op, int np, Piece pi, Player pl, Board b) {
        super(op, np, pi, pl, b);
    }


    /*---------------------------------------------GET SET------------------------------------------------------------*/
    /*-------------------------------------------OVERRIDE METHOD------------------------------------------------------*/
    /*-------------------------------------------INTERFACE METHOD-----------------------------------------------------*/

    @Override
    public void execute() {
        Piece capturePiece = board.getPieceFromPosition(newPos);
        if (board.isPositionOccupied(newPos) && piece.canCapturePiece(capturePiece))
            capture(capturePiece);
        else
            throw new IllegalArgumentException("AttackMove.java : newPos is empty || can't capture the piece");
    }

    //todo supprmier le dernier moveLog des autres move
    @Override
    public void undo(MoveLog moveLog) {
        List<Piece> playerWhoCapturedPiece = player.getCapturedPieces();
        Piece comeBackPiece = playerWhoCapturedPiece.get(playerWhoCapturedPiece.size() - 1);
        board.move(piece, oldPos);
        player.move(piece, oldPos);
        //comeBackPiece.setPosition(newPos);
        board.addPieceToBoard(comeBackPiece);
        player.move(comeBackPiece, newPos);
        board.move(comeBackPiece, newPos);
        player.getCapturedPieces().remove(comeBackPiece);
        moveLog.removeMove(this);
    }



    /*------------------------------------------------METHOD----------------------------------------------------------*/

    private void capture(Piece capturePiece) {

        board.validateAttackMove(piece, newPos);

        board.removePieceToBoard(capturePiece);
        board.move(piece, newPos);
        player.move(piece, newPos);
        player.addCapturedPiece(capturePiece);
    }
}
