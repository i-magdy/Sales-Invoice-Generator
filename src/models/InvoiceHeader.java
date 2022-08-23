package models;

import java.util.ArrayList;

public class InvoiceHeader {

    private int invoiceNumber;
    private String date;
    private String customerName;
    private Double total = 0.0;
    private ArrayList<InvoiceLine> invoiceLines = new ArrayList<>();


    public InvoiceHeader() {
    }

    public InvoiceHeader(int invoiceNumber, String date, String customerName) {
        this.invoiceNumber = invoiceNumber;
        this.date = date;
        this.customerName = customerName;
    }


    public int getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(int invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setInvoiceLines(ArrayList<InvoiceLine> invoiceLines) {
        this.invoiceLines = invoiceLines;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public ArrayList<InvoiceLine> getInvoiceLines() {
        return invoiceLines;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
