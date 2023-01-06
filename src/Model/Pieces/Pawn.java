package Model.Pieces;

import Model.utils.Color2;

/**
 * Model.Pieces.Pawn's class child of piece.
 */
public class Pawn extends Piece {
    // TODO retirer fonction inutile
    /*-------------------------------------------ATTRIBUTS------------------------------------------------------------*/
    /**
     * false if the pawn is promoted.
     */
    private boolean isPromoted = false;
    /**
     * false if the pawn can't take empassant.
     */
    private boolean isEmpassant = false;
    /**
     * false is the pawn had never move
     */
    private boolean hasNmovedOnce = false;




    /*-------------------------------------------CONSTRUCTORS---------------------------------------------------------*/

    /**
     * pawn's constructor set the strategy movement by default.
     *
     * @param color    pawn's color.
     * @param position pawn's position.
     */
    public Pawn(Color2 color, int position) {
        super(color, position, 1f, Color2.WHITE == color ? 1 : -1);
        setStrategy(new StrategyMovementPawn());
    }




    /*---------------------------------------------GET SET------------------------------------------------------------*/

    public boolean getHasMovedOnce() {
        return hasNmovedOnce;
    }

    /**
     * hasMovedOnce's getter.
     *
     * @param bool value of the var.
     */
    public void setHasMovedOnce(boolean bool) {
        hasNmovedOnce = bool;
    }



    /*-------------------------------------------OVERRIDE METHOD------------------------------------------------------*/

    @Override
    public String toString() {
        return getColor() + "pawn";
    }

    @Override
    public boolean isValidMove(int position) {
        //get the offset of movement
        int[] offset = executeStrategy();
        int positionCalcul = this.getPosition();
        if ((this.getPosition() - position == 9 || this.getPosition() - position == 11) && this.getColor() == Color2.WHITE)
            return true;
        if ((this.getPosition() - position == -9 || this.getPosition() - position == -11) && this.getColor() == Color2.BLACK)
            return true;
        if (this.getColor() == Color2.BLACK)
            if (hasNmovedOnce)
                return (position == positionCalcul + offset[1] && board[positionCalcul] != -10);
            else
                return (position == positionCalcul + offset[1] || position == positionCalcul + offset[1] * 2 && board[positionCalcul] != -10);
        else if (hasNmovedOnce)
            return (position == positionCalcul + offset[0] && board[positionCalcul] != -10);
        else
            return (position == positionCalcul + offset[0] || position == positionCalcul + offset[0] * 2 && board[positionCalcul] != -10);
    }
}
