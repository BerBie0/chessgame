package Model.Move;

import Model.Board.Board;
import Model.Pieces.Piece;
import Model.Player.Player;

public abstract class Move implements IMove
{
    /*-------------------------------------------ATTRIBUTS------------------------------------------------------------*/

    protected final int oldPos;
    protected final int newPos;
    protected final Piece piece;
    protected final Player player;
    protected final Board board;



    /*-------------------------------------------CONSTRUCTORS---------------------------------------------------------*/

    public Move(int op, int np, Piece pi, Player pl, Board b)
    {
        oldPos = op;
        newPos = np;
        piece = pi;
        player = pl;
        board = b;
    }
    /*---------------------------------------------GET SET------------------------------------------------------------*/
    /*-------------------------------------------OVERRIDE METHOD------------------------------------------------------*/
    /*-------------------------------------------INTERFACE METHOD-----------------------------------------------------*/
    /*------------------------------------------------METHOD----------------------------------------------------------*/
}
