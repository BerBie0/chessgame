package Model.Pieces;

import Model.utils.Color2;

/**
 * class Model.Pieces.Queen child of piece.
 */
public class Queen extends Piece {

    /*-------------------------------------------ATTRIBUTS------------------------------------------------------------*/




    /*-------------------------------------------CONSTRUCTORS---------------------------------------------------------*/

    /**
     * Model.Pieces.Queen's constructor set the strategy movement by default.
     *
     * @param color    queen's color.
     * @param position queen's position.
     */
    public Queen(Color2 color, int position) {
        super(color, position, 8.9f, Color2.WHITE == color ? 5 : -5);
        setStrategy(new StrategyMovementQueen());
    }




    /*-------------------------------------------OVERRIDE METHOD------------------------------------------------------*/

    @Override
    public String toString() {
        return getColor() + "queen";
    }

    @Override
    public boolean isValidMove(int position) {
        //comments in Model.Pieces.Bishop.java
        if (position < 0)
            throw new IllegalArgumentException("Model.Pieces.Bishop.java : isValidMove(int position) : position < 0");
        int[] offset = executeStrategy();
        //System.out.print("{ ");
        //System.out.println(Arrays.toString(offset));
        for (int j : offset) {
            int positionCalcul = this.getPosition();
            while (board[positionCalcul + j] != -10) {
                positionCalcul += j;
                //System.out.print(positionCalcul + ", ");
                if (positionCalcul == position) return true;
            }
        }
        //System.out.print(" }");
        return false;
    }

}
