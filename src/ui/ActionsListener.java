package ui;

import models.InvoiceHeader;

public interface ActionsListener {
    public void createInvoiceAction(InvoiceHeader invoiceHeader);
    public void menuSaveAction();
    void menuLoadAction();
    void cancelCreatingAction();
    void createNewInvoice();
    void deleteInvoice(int position);
}
