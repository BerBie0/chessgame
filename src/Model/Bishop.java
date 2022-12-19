package Model;

/**
 * bishop's class child of piece
 */
public class Bishop extends Piece
{
    /*-------------------------------------------ATTRIBUTS------------------------------------------------------------*/



    /*-------------------------------------------CONSTRUCTORS---------------------------------------------------------*/

    /**
     * bishop's Constructor, set the strategy by default
     * @param color bishop's color
     * @param position bishop's position in the board
     */
    public Bishop(Color2 color, int position )
    {
        super( color, position, 2.9f, Color2.WHITE == color ? 3 : -3 );
        setStrategy( new StrategyMovementBishop() );
    }



    /*-------------------------------------------OVERRIDE METHOD------------------------------------------------------*/

    @Override
    public String toString()
    {
        return getColor() + "bishop";
    }
    @Override
    public boolean isValidMove( int position )
    {
        if(position < 0)
            throw new IllegalArgumentException("Model.Bishop.java : isValidMove(int position) : position < 0");
        //get the offset movements of the piece
        int[] offset = executeStrategy();
        //for all value in the array
        for ( int j : offset )
        {
            int positionCalcul = this.getPosition();
            //test while the positionCalcul is a legal position
            while ( board[positionCalcul + j] != -10 ) {
                positionCalcul += j;
                if ( positionCalcul == position ) return true;
            }
        }
        return false;
    }
}
