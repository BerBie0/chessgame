package Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;

import Model.Model;
import View.IObserver;

public class Controller implements IObserver {
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
    public void updateBookTitle(String change) {
        m.setTitle(change);
    }
}
