package Model.Move;

import Model.Board.Board;
import Model.Pieces.Piece;
import Model.Player.Player;

public class SimpleMove extends Move
{

    /*-------------------------------------------ATTRIBUTS------------------------------------------------------------*/
    /*-------------------------------------------CONSTRUCTORS---------------------------------------------------------*/

    public SimpleMove(int op, int np, Piece pi, Player pl, Board b)
    {
        super(op, np, pi, pl, b);
    }
    /*---------------------------------------------GET SET------------------------------------------------------------*/
    /*-------------------------------------------OVERRIDE METHOD------------------------------------------------------*/
    /*-------------------------------------------INTERFACE METHOD-----------------------------------------------------*/
    @Override
    public void execute()
    {
        //System.out.println("coucou");
        if( piece.getColor() != player.getColor() )
            throw new IllegalArgumentException("SimpleMove.java : The piece is not ur");
        board.validateSimpleMove(piece, newPos);
        player.move(piece, newPos);
    }

    @Override
    public void undo()
    {
        player.move(piece, oldPos);
    }
    /*------------------------------------------------METHOD----------------------------------------------------------*/
}
