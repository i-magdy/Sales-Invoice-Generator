package view;

import util.ActionCommands;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuBar  implements ActionListener {


    private final JMenu mFileMenu;
    private final ActionsListener listener;

    public MainMenuBar(ActionsListener listener){
        this.listener = listener;
        mFileMenu = new JMenu("File");
        JMenuItem mLoadItem = new JMenuItem("Load");
        JMenuItem mSaveItem = new JMenuItem("Save");

        mFileMenu.add(mLoadItem);
        mFileMenu.add(mSaveItem);
        mSaveItem.setActionCommand(ActionCommands.MENU_SAVE);
        mLoadItem.setActionCommand(ActionCommands.MENU_LOAD);
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
            case ActionCommands.MENU_SAVE:  {
                listener.menuSaveAction();
                break;
            }
            case ActionCommands.MENU_LOAD:{
                listener.menuLoadAction();
                break;
            }
            default:
                System.out.println("Action command not found");
                break;
        }
    }
}
