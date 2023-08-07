package Model.Pieces;

import Model.Utils.Color2;

import java.util.ArrayList;

public class King extends Piece{

    private boolean hasMovedOnce;
    private boolean isChecked;
    private static int nbrInstance = 0;
    private King(Color2 color, int position) {
        super(color, position, 128f, Color2.WHITE == color ? 6 : -6);
        setStrategy(new StrategyMovementKing());
        hasMovedOnce = false;
        isChecked = false;
        nbrInstance++;
    }
    public static King createInstanceKing(Color2 color, int position) {
        if (nbrInstance < 2) {
            return new King(color, position);
        }
        throw new IllegalArgumentException("cannot create more than 2 king");
    }

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


    @Override
    public String toString() {
        return getColor() + "king";
    }
    @Override
    public boolean isValidMove(int position) {
        if (position < 0 || position > 120)
            return false;
        return allLegalMove(this.getPosition()).contains(position);
    }
    @Override
    public ArrayList<Integer> allLegalMove(int position) {
        int[] offset = executeStrategy();
        ArrayList<Integer> allLegalMove = new ArrayList<Integer>();
        for (int i : offset) {
            int singleMovePosition = position + i;
            if (board[singleMovePosition] != 10)
                allLegalMove.add(singleMovePosition);
        }
        return allLegalMove;
    }
}
