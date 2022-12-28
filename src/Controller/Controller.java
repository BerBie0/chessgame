package Controller;

import Model.Model;
import Model.Player.IPlayerObserver;

public class Controller implements IPlayerObserver {
    Model m;
    public Controller(Model m)
    {
        this.m = m;
    }

    public void modifyBookTitle(String change)
    {
        m.setTitle(change);
    }

    @Override
    public void updatePlayerName() {

    }
}
