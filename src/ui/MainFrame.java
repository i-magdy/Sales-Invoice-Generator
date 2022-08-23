package ui;

import models.InvoiceHeader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class MainFrame extends JFrame implements ActionListener,ActionsListener {


    private ArrayList<InvoiceHeader> invoiceHeaders = new ArrayList<>();
    public MainFrame(){
        super("Sales Invoice Generator");
        setSize(1024,560);
        setLayout(new GridLayout(1,2));
        MainMenuBar menuBar = new MainMenuBar();
        setJMenuBar(menuBar.create());
        InvoicesTable invoicesTable = new InvoicesTable(this);
        CreateInvoiceFrame createInvoiceFrame = new CreateInvoiceFrame(this);
        add(invoicesTable);
        add(createInvoiceFrame);
        createInvoiceFrame.showLayout();
        invoiceHeaders.add(new InvoiceHeader(5,"5/4/2022","Ibrahim"));
        invoicesTable.setInvoices(invoiceHeaders);
        createInvoiceFrame.updateInvoiceNumber(50);

    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {



    }

    @Override
    public void createInvoiceAction(InvoiceHeader invoiceHeader) {

    }

    @Override
    public void menuSaveAction() {

    }

    @Override
    public void menuLoadAction() {

    }

    @Override
    public void cancelCreatingAction() {

    }

    @Override
    public void CreateNewInvoice() {

    }
}
