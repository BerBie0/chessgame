package Model.Move;

import Model.Board.Board;
import Model.Pieces.Piece;
import Model.Pieces.Rook;
import Model.Player.Player;

public class CastleMove extends Move
{
    /*-------------------------------------------ATTRIBUTS------------------------------------------------------------*/
    /*-------------------------------------------CONSTRUCTORS---------------------------------------------------------*/

    public CastleMove(int op, int np, Piece pi, Player pl, Board b) {
        super(op, np, pi, pl, b);
    }
    /*---------------------------------------------GET SET------------------------------------------------------------*/
    /*-------------------------------------------OVERRIDE METHOD------------------------------------------------------*/
    /*-------------------------------------------INTERFACE METHOD-----------------------------------------------------*/

    @Override
    public void execute()
    {
        //TODO reecrire
        if( piece.getColor() != player.getColor() )
            throw new IllegalArgumentException("AttackMove.java : The piece is not ur");

        if ( player.isWhite() && newpos == 97 ) {
            Piece king = board.getWhiteKing();
            Piece rook = board.getPieceFromPosition(98);
            if (canCastle(king, rook)) {
                board.move(rook, 97);
                board.move(king, 96);
            } else
                throw new IllegalArgumentException("CastleMove.java : execute() : white can't castle on king side");
        } else if ( player.isWhite() && newpos == 93 ) {
            Piece king = board.getWhiteKing();
            Piece rook = board.getPieceFromPosition(91);
            if (canCastle(king, rook)) {
                board.move(rook, 94);
                board.move(king, 93);
            } else
                throw new IllegalArgumentException("CastleMove.java : execute() : white can't castle on queen side");
        } else if ( !player.isWhite() && newpos == 27 ) {
            Piece king = board.getBlackKing();
            Piece rook = board.getPieceFromPosition(28);
            if (canCastle(king, rook)) {
                board.move(rook, 26);
                board.move(king, 28);
            } else
                throw new IllegalArgumentException("CastleMove.java : execute() : black can't castle on king side");
        } else if ( !player.isWhite() && newpos == 23 ) {
            Piece king = board.getBlackKing();
            Piece rook = board.getPieceFromPosition(21);
            if (canCastle(king, rook)) {
                board.move(rook, 24);
                board.move(king, 23);
            } else
                throw new IllegalArgumentException("CastleMove.java : execute() : black can't castle on queen side");
        }
    }

    //TODO reecrire
    @Override
    public void undo()
    {
        int rookPosition, kingPosition;
        if ( player.isWhite() && board.getWhiteKing().getPosition() == 97 ) {
            kingPosition = 95;
            rookPosition = 98;
            Piece king = board.getWhiteKing();
            Piece rook = board.getPieceFromPosition(96);
            board.move(king, kingPosition);
            board.move(rook, rookPosition);
        } else if ( player.isWhite() && board.getWhiteKing().getPosition() == 92 ) {
            kingPosition = 95;
            rookPosition = 91;
            Piece king = board.getWhiteKing();
            Piece rook = board.getPieceFromPosition(94);
            board.move(king, kingPosition);
            board.move(rook, rookPosition);
        }
        else if ( !player.isWhite() && board.getBlackKing().getPosition() == 27 ) {
            kingPosition = 25;
            rookPosition = 28;
            Piece king = board.getBlackKing();
            Piece rook = board.getPieceFromPosition(26);
            board.move(king, kingPosition);
            board.move(rook, rookPosition);
        } else if ( !player.isWhite() && board.getBlackKing().getPosition() == 22 ) {
            kingPosition = 25;
            rookPosition = 21;
            Piece king = board.getBlackKing();
            Piece rook = board.getPieceFromPosition(24);
            board.move(king, kingPosition);
            board.move(rook, rookPosition);
        }
    }



    /*------------------------------------------------METHOD----------------------------------------------------------*/

    private boolean canCastle(Piece king, Piece rook)
    {
        if ( !(rook instanceof Rook) ) {
            return false;
        }
        //TODO regle de roque
        return true;
    }
}
