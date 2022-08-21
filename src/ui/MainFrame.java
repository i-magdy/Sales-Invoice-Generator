package ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame implements ActionListener {


    public MainFrame(){
        super("Sales Invoice Generator");
        setSize(720,720);
        setLayout(new BoxLayout(getContentPane(),BoxLayout.X_AXIS));
        MainMenuBar menuBar = new MainMenuBar();
        setJMenuBar(menuBar.create());
        InvoicesTable invoicesTable = new InvoicesTable();
        add(invoicesTable);

    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {



    }
}
