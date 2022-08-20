package ui;

import util.ActionCommands;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame implements ActionListener {

    public MainFrame(){
        super("Sales Invoice Generator");
        setSize(720,720);
        setLayout(new FlowLayout());
        MainMenuBar menuBar = new MainMenuBar();
        setJMenuBar(menuBar.create());
    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {



    }
}
