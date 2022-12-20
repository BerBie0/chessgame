package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import Controller.Controller;
import Model.Model;

public class View implements IObserver{
    JFrame frame;
    JTextField field;
    JButton button;
    JLabel label, label2, label3;
    Model model;
    Controller controller;

    public View(Model model, Controller controller)
    {
        this.model = model;
        this.controller = controller;
        this.init();
    }
    public JButton getButton() {
        return button;
    }

    public JLabel getLabel() {
        return label;
    }

    public JLabel getLabel2() {
        return label2;
    }

    public JLabel getLabel3() {
        return label3;
    }

    public void init()
    {
        frame = new JFrame("exemple poo");
        field = new JTextField();
        field.setColumns(10);
        button = new JButton("ok");
        label = new JLabel("label1");
        label2 = new JLabel("label2");
        label3 = new JLabel("label3");

        frame.setLayout(new FlowLayout());
        frame.getContentPane().add(field);
        frame.getContentPane().add(button);
        button.addActionListener(actionEvent -> editBookTitle(field.getText()));
        frame.getContentPane().add(label);
        frame.getContentPane().add(label2);
        frame.getContentPane().add(label3);

        frame.setVisible(true);
        frame.pack();

        frame.setLocationRelativeTo(null);
    }

    public void editBookTitle(String change)
    {
        controller.modifyBookTitle(change);
    }

    @Override
    public void updateBookTitle(String change)
    {
        getLabel().setText(change);
        getLabel2().setText(model.getTitle());
        getLabel3().setText(model.getTitle());
    }
}
