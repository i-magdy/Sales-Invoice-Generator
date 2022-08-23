package ui;

import models.InvoiceHeader;
import util.ActionCommands;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.ArrayList;
import java.util.List;

public class InvoicesTable extends JPanel implements ListSelectionListener {

    private ActionsListener listener;
    private JTable table;
    private List<String> col = new ArrayList<>();
    private ArrayList<InvoiceHeader> invoices = new ArrayList<>();
    private int selectedRow = -1;

    public InvoicesTable(ActionsListener listener){
        this.listener = listener;
        col.add("NO.");
        col.add("Customer");
        col.add("Count");
        col.add("Total");
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

        table = new JTable(createTableModel());
        table.getSelectionModel().addListSelectionListener(this);
        add(new JScrollPane(table));
        JButton createButton = new JButton("Create Invoice");
        JButton deleteButton = new JButton("Delete Invoice");
        createButton.setActionCommand(ActionCommands.CREATE);
        deleteButton.setActionCommand(ActionCommands.DELETE);
        JPanel panel = new JPanel();
        panel.add(createButton);
        panel.add(deleteButton);
        add(panel);
    }



    private TableModel createTableModel() {
        return new TableModel() {
            @Override
            public int getRowCount() {
                return invoices.size();
            }

            @Override
            public int getColumnCount() {
                return col.size();
            }

            @Override
            public String getColumnName(int i) {
                return col.get(i);
            }

            @Override
            public Class<?> getColumnClass(int i) {
                return String.class;
            }

            @Override
            public boolean isCellEditable(int i, int i1) {
                return false;
            }

            @Override
            public Object getValueAt(int i, int i1) {
                switch (i1){
                    case 0: return String.valueOf(invoices.get(i).getInvoiceNumber());
                    case 1: return invoices.get(i).getCustomerName();
                    case 3: return String.valueOf(invoices.get(i).getTotal());
                    case 2: return String.valueOf(invoices.get(i).getInvoiceLines().size());
                    default: return "";
                }
            }

            @Override
            public void setValueAt(Object o, int i, int i1) {
            }

            @Override
            public void addTableModelListener(TableModelListener tableModelListener) {

            }

            @Override
            public void removeTableModelListener(TableModelListener tableModelListener) {

            }
        };
    }
    @Override
    public void valueChanged(ListSelectionEvent listSelectionEvent) {
        selectedRow = table.getSelectedRow();
        System.out.println(selectedRow);
    }

    public int getSelectedRow() {
        return selectedRow;
    }

    public ArrayList<InvoiceHeader> getInvoices() {
        return invoices;
    }

    public void setInvoices(ArrayList<InvoiceHeader> invoices) {
        this.invoices = invoices;
        table.setModel(createTableModel());
    }
}
