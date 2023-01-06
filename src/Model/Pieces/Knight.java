package Model.Pieces;

import Model.utils.Color2;

/**
 * Model.Pieces.Knight's class child of piece.
 */
public class Knight extends Piece {
    /*-------------------------------------------ATTRIBUTS------------------------------------------------------------*/



    /*-------------------------------------------CONSTRUCTORS---------------------------------------------------------*/

    /**
     * Model.Pieces.Knight's constructor set the movement strategy by default.
     *
     * @param color
     * @param position
     */
    public Knight(Color2 color, int position) {
        super(color, position, 2.7f, Color2.WHITE == color ? 2 : -2);
        setStrategy(new StrategyMovementKnight());
    }




    /*-------------------------------------------OVERRIDE METHOD------------------------------------------------------*/

    @Override
    public String toString() {
        return getColor() + "knight";
    }

    @Override
    public boolean isValidMove(int position) {
        //comment in Model.Pieces.Bishop.java
        if (position < 0)
            throw new IllegalArgumentException("Model.Pieces.Knight.java : isValidMove(int position) : position < 0");
        int[] offset = executeStrategy();
        for (int j : offset) {
            int positionCalcul = this.getPosition();
            positionCalcul += j;
            if (positionCalcul == position && board[positionCalcul] != -10) return true;
        }
        return false;
    }
}
