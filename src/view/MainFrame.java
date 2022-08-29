package view;

import models.InvoiceHeader;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MainFrame extends JFrame  {

    private InvoicesTable invoicesTable;
    private CreateInvoiceFrame createInvoiceFrame;
    private ShowInvoiceView invoiceView;
    private ActionsListener listener;
    public MainFrame(){
        super("Sales Invoice Generator");
        setSize(1024,560);
        setLayout(new GridLayout(1,2));
    }


    public void updateInvoicesTable(ArrayList<InvoiceHeader> invoiceHeaders){
        invoicesTable.setInvoices(invoiceHeaders);
    }
    public void setActionsListener(ActionsListener listener){
        this.listener =  listener;
        MainMenuBar menuBar = new MainMenuBar(listener);
        setJMenuBar(menuBar.create());
        invoicesTable = new InvoicesTable(listener);
        createInvoiceFrame = new CreateInvoiceFrame(listener);
        invoiceView = new ShowInvoiceView(listener);
        add(invoicesTable);
        add(invoiceView);
        invoiceView.hideLayout();
    }

    public void showInvoiceForEdite(InvoiceHeader invoice){
        remove(invoiceView);
        invoiceView.hideLayout();
        add(createInvoiceFrame);
        createInvoiceFrame.showLayout();
        createInvoiceFrame.updateInvoiceUi(invoice);
    }


    public void hideCreateInvoiceView(){
        createInvoiceFrame.hideLayout();
        invoicesTable.clearSelection();
    }




    public void createNewInvoice(int newInvoiceNumber){
        if (!createInvoiceFrame.isVisible()) {
            remove(invoiceView);
            invoiceView.hideLayout();
            add(createInvoiceFrame);
            createInvoiceFrame.showLayout();
            createInvoiceFrame.setInvoiceNumber(newInvoiceNumber);
        }
    }

    public boolean isCreateInvoiceViewVisible(){
        return createInvoiceFrame.isVisible();
    }

    public int getCurrentInvoiceNumberIsShown(){
        return invoiceView.getInvoiceNumber();
    }

    public void hideInvoiceView(){
        invoiceView.hideLayout();
        invoicesTable.clearSelection();
    }

    public void showInvoiceView(InvoiceHeader invoiceHeader){
        remove(createInvoiceFrame);
        remove(invoiceView);
        add(invoiceView);
        invoiceView.showInvoice(invoiceHeader);
    }

}
