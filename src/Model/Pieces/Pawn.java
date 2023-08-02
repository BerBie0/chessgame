package Model.Pieces;

import Model.Utils.Color2;

import java.util.ArrayList;

public class Pawn extends Piece{
    private boolean isPromoted;
    private boolean isEmpassant;
    private boolean hasMovedOnce;

    public Pawn(Color2 color, int position) {
        super(color, position, 1f, Color2.WHITE == color ? 1 : -1);
        hasMovedOnce = false;
        isEmpassant = false;
        isPromoted = false;
        setStrategy(new StrategyMovementPawn());
    }

    public boolean getHasMovedOnce() {
        return hasMovedOnce;
    }
    public void setHasMovedOnce(boolean hasMovedOnce) {
        this.hasMovedOnce = hasMovedOnce;
    }

    @Override
    public String toString() {
        return getColor() + "pawn";
    }
    @Override
    public boolean isValideMove(int position) {
        if (position < 0 || position > 120)
            return false;
        return allLegalMove(this.getPosition()).contains(position);
    }

    @Override
    public ArrayList<Integer> allLegalMove(int position) {
        int[] offset = executeStrategy();
        ArrayList<Integer> allLegalMove = new ArrayList<>();
        int offsetIndex = isWhite() ? 1 : 0;

        int singleMovePosition = position + offset[offsetIndex];
        int doubleMovePosition = position + (offset[offsetIndex] * 2);

        if (board[singleMovePosition] != 10)
            allLegalMove.add(singleMovePosition);

        if (!hasMovedOnce && board[doubleMovePosition] != 10)
            allLegalMove.add(doubleMovePosition);

        return allLegalMove;
    }
}
