package Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;

import Model.Model;

public class Controller {
    Model m;
    public Controller(Model m)
    {
        this.m = m;
    }

    public void modifyBookTitle(String change)
    {
        m.setTitle(change);
    }

    public String getData(){
        return m.getTitle();
    }
}
