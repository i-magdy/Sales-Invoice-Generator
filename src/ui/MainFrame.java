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
    private InvoicesTable invoicesTable;
    private CreateInvoiceFrame createInvoiceFrame;
    public MainFrame(){
        super("Sales Invoice Generator");
        setSize(1024,560);
        setLayout(new GridLayout(1,2));
        MainMenuBar menuBar = new MainMenuBar();
        setJMenuBar(menuBar.create());
        invoicesTable = new InvoicesTable(this);
        createInvoiceFrame = new CreateInvoiceFrame(this);
        add(invoicesTable);
        add(createInvoiceFrame);
        createInvoiceFrame.hideLayout();
        invoiceHeaders.add(new InvoiceHeader(5,"5/4/2022","Ibrahim"));
        invoiceHeaders.add(new InvoiceHeader(5,"5/4/2022","Ibrahim kkk"));
        invoicesTable.setInvoices(invoiceHeaders);
    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {



    }

    @Override
    public void createInvoiceAction(InvoiceHeader invoiceHeader) {
        invoiceHeaders.add(invoiceHeader);
        invoicesTable.setInvoices(invoiceHeaders);
    }

    @Override
    public void menuSaveAction() {

    }

    @Override
    public void menuLoadAction() {

    }

    @Override
    public void cancelCreatingAction() {
        createInvoiceFrame.hideLayout();
    }

    @Override
    public void createNewInvoice() {
        createInvoiceFrame.showLayout();
        createInvoiceFrame.setInvoiceNumber(invoiceHeaders.get(invoiceHeaders.size()-1).getInvoiceNumber() + 1);
    }

    @Override
    public void deleteInvoice(int position) {
        if (position != -1 && invoiceHeaders.size() > 0 && invoiceHeaders.size() > position){
            invoiceHeaders.remove(position);
            invoicesTable.setInvoices(invoiceHeaders);
        }
    }

}
