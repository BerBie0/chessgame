package Controller;

import Model.Pieces.Piece;

public class GameController
{
    /*-------------------------------------------ATTRIBUTS------------------------------------------------------------*/

    private final GameManager gm;



    /*-------------------------------------------CONSTRUCTORS---------------------------------------------------------*/

    public GameController( GameManager gmm)
    {
        gm = gmm;
    }

    /*---------------------------------------------GET SET------------------------------------------------------------*/
    /*-------------------------------------------OVERRIDE METHOD------------------------------------------------------*/
    /*-------------------------------------------INTERFACE METHOD-----------------------------------------------------*/
    /*------------------------------------------------METHOD----------------------------------------------------------*/

    public void Game( int oldPos, int newPos, Piece piece )
    {
        while ( !gm.isCheckMate() && !gm.isPat() )
        {
            do
            {
                gm.execute( oldPos, newPos, piece, gm.getCurrentPlayer(), gm.getBoard() );
                if ( gm.isCurrentPlayerCheck() )
                    gm.undo();
            } while ( gm.isCurrentPlayerCheck() );

            gm.setWhiteTurn( !gm.isWhitePlayer() );
            gm.setBlackTurn( !gm.isBlackPlayer() );
        }
    }
}
