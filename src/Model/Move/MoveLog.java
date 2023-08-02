package Model.Move;

import java.util.ArrayList;

public class MoveLog {
    private ArrayList<IMove> positions;
    private ArrayList<IObserverMoveLog> observers;

    public MoveLog() {
        this.positions = new ArrayList<>();
        this.observers = new ArrayList<>();
    }

    public ArrayList<IMove> getMoves() {
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

    public boolean removeMove(final IMove move) {
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
