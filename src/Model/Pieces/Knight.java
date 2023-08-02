package Model.Pieces;

import Model.Utils.Color2;

import java.util.ArrayList;

public class Knight extends Piece{

    public Knight(Color2 color, int position) {
        super(color, position, 2.7f, Color2.WHITE == color ? 2 : -2);

        setStrategy(new StrategyMovementKnight());
    }

    @Override
    public String toString() {
        return getColor() + "Knight";
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
        ArrayList<Integer> allLegalMove = new ArrayList<Integer>();
        for (int i : offset) {
            int singleMovePosition = position + offset[i];
            if (board[singleMovePosition] != 10)
                allLegalMove.add(singleMovePosition);
        }
        return allLegalMove;
    }
}
