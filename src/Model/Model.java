package Model;

import java.util.ArrayList;
import java.util.List;

import Model.Player.IPlayerObserver;

public class Model{
    String valeur;
    List<IPlayerObserver> observers;

    public Model() {
        observers = new ArrayList<>();
    }
    public void addObserver( IPlayerObserver obs ) {
        observers.add(obs);
    }

    private void notifyTitleChange() {
        for ( IPlayerObserver obs : observers ) {
            obs.updatePlayerName();
        }
    }
    public void setTitle( String title )
    {
        this.valeur = title;
        notifyTitleChange();
    }

    public String getTitle()
    {
        return this.valeur;
    }
}
