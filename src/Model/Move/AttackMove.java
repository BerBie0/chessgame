package Model.Move;

import Model.Board.Board;
import Model.Pieces.Piece;
import Model.Player.Player;

import java.util.List;

public class AttackMove extends Move
{
    /*-------------------------------------------ATTRIBUTS------------------------------------------------------------*/
    /*-------------------------------------------CONSTRUCTORS---------------------------------------------------------*/

    public AttackMove(int op, int np, Piece pi, Player pl, Board b)
    {
        super(op, np, pi, pl, b);
    }


    /*---------------------------------------------GET SET------------------------------------------------------------*/
    /*-------------------------------------------OVERRIDE METHOD------------------------------------------------------*/
    /*-------------------------------------------INTERFACE METHOD-----------------------------------------------------*/

    @Override
    public void execute()
    {
        /*
        if( piece.getColor() != player.getColor() )
            throw new IllegalArgumentException("AttackMove.java : The piece is not ur");
         */

        Piece capturePiece = board.getPieceFromPosition(newPos);
        if ( board.isPositionOccupied(newPos) && piece.canCapturePiece(capturePiece) )
            capture(capturePiece);
        else
            throw new IllegalArgumentException("AttackMove.java : newPos is empty || can't capture the piece");
    }

    @Override
    public void undo()
    {
        List<Piece> playerWhoCapturedPiece = player.getCapturedPieces();
        Piece comeBackPiece = playerWhoCapturedPiece.get(playerWhoCapturedPiece.size() - 1);
        comeBackPiece.setPosition(newPos);
        board.move(piece, newPos);
        player.move(piece, newPos);
        board.addPieceToBoard(comeBackPiece);
        player.getCapturedPieces().remove(comeBackPiece);
    }



    /*------------------------------------------------METHOD----------------------------------------------------------*/

    private void capture(Piece capturePiece)
    {
        board.validateAttackMove(piece, newPos);
        board.removePieceToBoard(capturePiece);
        board.move(piece, newPos);
        player.move(piece, newPos);
        player.addCapturedPiece(capturePiece);
    }
}
