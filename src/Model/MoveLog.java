package Model;

import Model.Board.Board;
import Model.Move.IMove;
import Model.Move.Move;
import Model.utils.IObserverMoveLog;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MoveLog {
    /*-------------------------------------------ATTRIBUTS------------------------------------------------------------*/

    private List<IMove> positions;
    private final Board board;
    private List<IObserverMoveLog> observers;
    /*-------------------------------------------CONSTRUCTORS---------------------------------------------------------*/

    public MoveLog(Board board) {
        this.positions = new ArrayList<>();
        this.board = board;
        this.observers = new LinkedList<>();
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
        notifyObserver();
    }

    public int size() {
        return this.positions.size();
    }

    public void clear() {
        this.positions.clear();
    }

    public boolean removeMove(final Move move) {
        boolean res = this.positions.remove(move);
        notifyObserver();
        return res;

    }

    public void addObserver(IObserverMoveLog obs) {
        observers.add(obs);
    }
    public void notifyObserver() {
        for(IObserverMoveLog obs : observers) {
            obs.updateGameHistoryPanel();
        }
    }
}