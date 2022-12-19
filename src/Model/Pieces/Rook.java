package Model.Pieces;

import Model.utils.Color2;

/**
 * rook's class child of piece.
 */
public class Rook extends Piece
{
    /*-------------------------------------------ATTRIBUTS------------------------------------------------------------*/




    /*-------------------------------------------CONSTRUCTORS---------------------------------------------------------*/

    /**
     * rook's constructor set the movement strategy by default.
     * @param color rook's color.
     * @param position rook's position in the board.
     */
    public Rook(Color2 color, int position)
    {
        super( color, position, 4.3f, Color2.WHITE == color ? 4 : -4 );
        setStrategy(new StrategyMovementRook());
    }




    /*-------------------------------------------OVERRIDE METHOD------------------------------------------------------*/

    @Override
    public String toString()
    {
        return getColor() + "rook";
    }

    @Override
    public boolean isValidMove(int position)
    {
        //comment in Model.Pieces.Bishop.java
        if(position < 0)
            throw new IllegalArgumentException("Model.Pieces.Bishop.java : isValidMove(int position) : position < 0");
        int[] offset = executeStrategy();
        for (int j : offset)
        {
            int positionCalcul = this.getPosition();
            while (board[positionCalcul + j] != -10) {
                positionCalcul += j;
                if (positionCalcul == position) return true;
            }
        }
        return false;
    }

}
