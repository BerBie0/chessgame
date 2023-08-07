package Model.Pieces;

import Model.Utils.Color2;

import java.util.ArrayList;

public class Bishop extends Piece{

    public Bishop(Color2 color, int position) {
        super(color, position, 2.9f, Color2.WHITE == color ? 3 : -3);
        setStrategy(new StrategyMovementBishop());
    }

    @Override
    public String toString() {
        return getColor() + "bishop";
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
