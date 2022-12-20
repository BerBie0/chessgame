package Model;

import java.util.ArrayList;
import java.util.List;

import View.IObserver;

public class Model{
    String valeur;
    List<IObserver> observers;

    public Model() {
        observers = new ArrayList<>();
    }
    public void addObserver( IObserver obs ) {
        observers.add(obs);
    }

    private void notifyTitleChange() {
        for ( IObserver obs : observers ) {
            obs.updateBookTitle(valeur);
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
