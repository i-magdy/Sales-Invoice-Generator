package view;

import model.InvoiceHeader;

public interface ActionsListener {
    void createInvoiceAction(InvoiceHeader invoiceHeader);
    void menuSaveAction();
    void menuLoadAction();
    void cancelCreatingAction();
    void createNewInvoice();
    void deleteInvoice(int position);
    void editInvoice(InvoiceHeader invoiceHeader);
    void hideShowingInvoice();

    void showInvoice(int position);
}
