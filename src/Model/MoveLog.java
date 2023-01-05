package Model;

import Model.Board.Board;
import Model.Move.IMove;
import Model.Move.Move;

import java.util.ArrayList;
import java.util.List;

public class MoveLog {
    /*-------------------------------------------ATTRIBUTS------------------------------------------------------------*/

    private final List<IMove> positions;
    private final Board board;
    /*-------------------------------------------CONSTRUCTORS---------------------------------------------------------*/

    public MoveLog(Board board) {
        this.positions = new ArrayList<>();
        this.board = board;
    }

    /*---------------------------------------------GET SET------------------------------------------------------------*/
    /*-------------------------------------------OVERRIDE METHOD------------------------------------------------------*/
    /*-------------------------------------------INTERFACE METHOD-----------------------------------------------------*/
    /*------------------------------------------------METHOD----------------------------------------------------------*/

    public List<IMove> getMoves() {
        return this.positions;
    }

    public void addMove(final IMove move) {
        this.positions.add(move);
    }

    public int size() {
        return this.positions.size();
    }

    public void clear() {
        this.positions.clear();
    }

    public boolean removeMove(final Move move) {
        return this.positions.remove(move);
    }
}