package Model.Pieces;

import Model.utils.Color2;

/**
 * king's class child of piece.
 */
public class King extends Piece {
    // TODO retirer fonction inutile
    /*-------------------------------------------ATTRIBUTS------------------------------------------------------------*/

    /**
     * true if the king has moved once.
     */
    private boolean hasMovedOnce;
    /**
     * true if the king is checked.
     */
    private boolean isChecked;
    /**
     * number of king instance.
     */
    private static int nbrInstance = 0;



    /*-------------------------------------------CONSTRUCTORS---------------------------------------------------------*/

    //TODO remettre en prive junit test

    /**
     * king's private contructor set the strategy movement by default, hasMovedOnce, isChecked to false.
     *
     * @param color    Model.Pieces.King's color.
     * @param position Model.Pieces.King's position in the board.
     */
    public King(Color2 color, int position) {
        super(color, position, 128f, Color2.WHITE == color ? 6 : -6);
        setStrategy(new StrategyMovementKing());
        hasMovedOnce = false;
        isChecked = false;
        nbrInstance++;
    }

    /**
     * Inialise an instance of king, limit of 2 king.
     *
     * @param color    king's color.
     * @param position kings' position.
     * @return instance of Model.Pieces.King.
     */
    public static King createInstanceKing(Color2 color, int position) {
        if (nbrInstance < 2) {
            return new King(color, position);
        }
        throw new IllegalArgumentException("Model.Pieces.King.java : createInstanceKing : too much king on the board");
    }



    /*---------------------------------------------GET SET------------------------------------------------------------*/

    public boolean getIsChecked() {
        return isChecked;
    }

    public boolean getHasMovedOnce() {
        return hasMovedOnce;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    public void setHasMovedOnce(boolean hasMovedOnce) {
        this.hasMovedOnce = hasMovedOnce;
    }



    /*-------------------------------------------OVERRIDE METHOD------------------------------------------------------*/

    @Override
    public String toString() {
        return getColor() + "king";
    }

    @Override
    public boolean isValidMove(int position) {
        if (position < 0)
            throw new IllegalArgumentException("Model.Pieces.Bishop.java : isValidMove(int position) : position < 0");

        //get the offset movement of the piece.
        int[] offset = executeStrategy();
        //for all value in the array test if positionCalcul is a legalPosition and if it is the position we search
        for (int j : offset) {
            int positionCalcul = this.getPosition();
            positionCalcul += j;
            if (positionCalcul == position && board[positionCalcul] != -10) return true;
        }
        return false;
    }
}
