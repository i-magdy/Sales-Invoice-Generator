package ui;

import models.FileOperations;
import models.InvoiceHeader;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MainFrame extends JFrame implements ActionsListener {


    private ArrayList<InvoiceHeader> invoiceHeaders = new ArrayList<>();
    private InvoicesTable invoicesTable;
    private CreateInvoiceFrame createInvoiceFrame;
    private ShowInvoiceView showInvoiceView;
    public MainFrame(){
        super("Sales Invoice Generator");
        setSize(1024,560);
        setLayout(new GridLayout(1,2));
        MainMenuBar menuBar = new MainMenuBar(this);
        setJMenuBar(menuBar.create());
        invoicesTable = new InvoicesTable(this);
        createInvoiceFrame = new CreateInvoiceFrame(this);
        showInvoiceView = new ShowInvoiceView(this);
        add(invoicesTable);
        add(showInvoiceView);
        showInvoiceView.hideLayout();
        createInvoiceFrame.hideLayout();
        invoicesTable.setInvoices(invoiceHeaders);
        invoiceHeaders = FileOperations.readFile();
        invoicesTable.setInvoices(invoiceHeaders);
    }


    @Override
    public void createInvoiceAction(InvoiceHeader invoiceHeader) {
        boolean isFound = false;
        for (int i =0; i < invoiceHeaders.size();i++){
            if (invoiceHeader.getInvoiceNumber() ==  invoiceHeaders.get(i).getInvoiceNumber()){
                isFound = true;
                invoiceHeaders.set(i,invoiceHeader);
                break;
            }
        }
        if (!isFound) {
            invoiceHeaders.add(invoiceHeader);
        }
        invoicesTable.setInvoices(invoiceHeaders);
    }

    @Override
    public void menuSaveAction() {
        FileOperations.writFile(invoiceHeaders);
    }

    @Override
    public void menuLoadAction() {
        invoiceHeaders = FileOperations.readFile(this);
        invoicesTable.setInvoices(invoiceHeaders);
    }

    @Override
    public void cancelCreatingAction() {
        createInvoiceFrame.hideLayout();
    }

    @Override
    public void createNewInvoice() {
        if (!createInvoiceFrame.isVisible()) {
            remove(showInvoiceView);
            showInvoiceView.hideLayout();
            add(createInvoiceFrame);
            createInvoiceFrame.showLayout();
            createInvoiceFrame.setInvoiceNumber(invoiceHeaders.isEmpty() ? 1 : invoiceHeaders.get(invoiceHeaders.size() - 1).getInvoiceNumber() + 1);
        }
    }

    @Override
    public void deleteInvoice(int position) {
        System.out.println(position);
        if (position != -1 && invoiceHeaders.size() > 0 && invoiceHeaders.size() > position && !createInvoiceFrame.isVisible()){
            InvoiceHeader deletedInvoice  = invoiceHeaders.remove(position);
            if (deletedInvoice.getInvoiceNumber() == showInvoiceView.getInvoiceNumber()){
                showInvoiceView.hideLayout();
            }
            invoicesTable.setInvoices(invoiceHeaders);
        }
    }

    @Override
    public void editInvoice(InvoiceHeader invoiceHeader) {
        remove(showInvoiceView);
        showInvoiceView.hideLayout();
        add(createInvoiceFrame);
        createInvoiceFrame.showLayout();
        createInvoiceFrame.updateInvoiceUi(invoiceHeader);
    }

    @Override
    public void hideShowingInvoice() {
        showInvoiceView.hideLayout();
        invoicesTable.clearSelection();
    }

    @Override
    public void showInvoice(int position) {
        if (position != -1 && invoiceHeaders.size() > 0 && invoiceHeaders.size() > position && !createInvoiceFrame.isVisible()){
            remove(createInvoiceFrame);
            remove(showInvoiceView);
            add(showInvoiceView);
            showInvoiceView.showInvoice(invoiceHeaders.get(position));
        }
    }

}
