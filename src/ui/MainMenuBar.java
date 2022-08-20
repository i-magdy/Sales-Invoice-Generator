package ui;

import util.ActionCommands;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuBar  implements ActionListener {


    private JMenu mFileMenu;

    public MainMenuBar(){
        mFileMenu = new JMenu("File");
        JMenuItem mLoadItem = new JMenuItem("Load");
        JMenuItem mSaveItem = new JMenuItem("Save");

        mFileMenu.add(mLoadItem);
        mFileMenu.add(mSaveItem);
        mSaveItem.setActionCommand(ActionCommands.SAVE);
        mLoadItem.setActionCommand(ActionCommands.LOAD);
        mSaveItem.addActionListener(this);
        mLoadItem.addActionListener(this);

    }

    public JMenuBar create(){
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(mFileMenu);
        return menuBar;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        switch (actionEvent.getActionCommand()){
            case ActionCommands.SAVE:  {
                System.out.println("Saving..");
                break;
            }
            case ActionCommands.LOAD:{
                System.out.println("Loading..");
                break;
            }
            default:
                System.out.println("Action command not found");
                break;
        }
    }
}
