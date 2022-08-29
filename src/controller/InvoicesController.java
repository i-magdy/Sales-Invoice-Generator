package controller;

import models.FileOperations;
import models.InvoiceHeader;
import view.ActionsListener;
import view.MainFrame;

import javax.swing.*;
import java.util.ArrayList;

public class InvoicesController implements ActionsListener {


    private final MainFrame mainFrame;
    private ArrayList<InvoiceHeader> invoiceHeaders;

    public InvoicesController(MainFrame frame) {
        mainFrame = frame;
        mainFrame.setActionsListener(this);
        mainFrame.setVisible(true);
    }


    public void setInvoiceHeaders(ArrayList<InvoiceHeader> invoiceHeaders) {
        this.invoiceHeaders = invoiceHeaders;
    }

    public ArrayList<InvoiceHeader> getInvoiceHeaders() {
        return invoiceHeaders;
    }

    public void updateView(){
        mainFrame.updateInvoicesTable(getInvoiceHeaders());
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

        updateView();
    }

    @Override
    public void menuSaveAction() {
        FileOperations.writFile(getInvoiceHeaders(),mainFrame);
    }

    @Override
    public void menuLoadAction() {
        setInvoiceHeaders(FileOperations.readFile(mainFrame));
        updateView();
    }

    @Override
    public void cancelCreatingAction() {
        mainFrame.hideCreateInvoiceView();
    }

    @Override
    public void createNewInvoice() {
        mainFrame.createNewInvoice(invoiceHeaders.isEmpty() ? 1 : invoiceHeaders.get(invoiceHeaders.size() - 1).getInvoiceNumber() + 1);
    }

    @Override
    public void deleteInvoice(int position) {
        if (position != -1 && invoiceHeaders.size() > 0 && invoiceHeaders.size() > position && !mainFrame.isCreateInvoiceViewVisible()){
            InvoiceHeader deletedInvoice  = invoiceHeaders.remove(position);
            if (deletedInvoice.getInvoiceNumber() == mainFrame.getCurrentInvoiceNumberIsShown()){
                mainFrame.hideInvoiceView();
            }
            updateView();
        }else {
            JOptionPane.showMessageDialog(mainFrame,invoiceHeaders.isEmpty() ?"There's no invoices to delete.":"Please Select invoice.","Error",JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void editInvoice(InvoiceHeader invoiceHeader) {
        mainFrame.showInvoiceForEdite(invoiceHeader);
    }

    @Override
    public void hideShowingInvoice() {
        mainFrame.hideInvoiceView();
    }

    @Override
    public void showInvoice(int position) {
        if (position != -1 && invoiceHeaders.size() > 0 && invoiceHeaders.size() > position && !mainFrame.isCreateInvoiceViewVisible()){
            mainFrame.showInvoiceView(invoiceHeaders.get(position));
        }
    }
}
