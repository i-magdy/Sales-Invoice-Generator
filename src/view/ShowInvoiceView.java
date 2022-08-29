package view;

import model.InvoiceHeader;
import util.ActionCommands;

import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ShowInvoiceView extends JPanel implements ActionListener {


    private InvoiceHeader invoiceHeader;
    private final ActionsListener listener;
    private JLabel customerNameField;
    private JLabel dateField;
    private JLabel totalLabel;
    private JLabel invoiceNumberLabel;
    private List<String> cols = new ArrayList<>(5);
    private JTable lines;


    public ShowInvoiceView(ActionsListener listener){
        this.listener = listener;
        setLocation(20,0);
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        cols.add("No.");
        cols.add("Item Name");
        cols.add("Item price");
        cols.add("Count");
        cols.add("Item Total");
        add(createInvoiceInfoPanel());
        lines = new JTable();
        add(new JScrollPane(lines));
        createPanelActions();
        hideLayout();
    }

    private void createPanelActions(){
        JButton saveButton = new JButton("Edit");
        JButton cancelButton = new JButton("Cancel");
        saveButton.setActionCommand(ActionCommands.EDIT);
        cancelButton.setActionCommand(ActionCommands.HIDE);
        saveButton.addActionListener(this);
        cancelButton.addActionListener(this);
        JPanel panel = new JPanel();
        panel.add(saveButton);
        panel.add(cancelButton);
        add(panel);
    }



    private JPanel createInvoiceInfoPanel(){
        JPanel infoPanel = new JPanel();
        infoPanel.setLocation(0,0);
        infoPanel.setLayout(new FlowLayout());
        infoPanel.add(createInvoiceInfoTitles());
        infoPanel.add(createInvoiceInfoFields());
        return infoPanel;
    }

    private JPanel createInvoiceInfoTitles(){
        JPanel panel = new JPanel();
        panel.setLocation(0,0);
        panel.setLayout(new GridLayout(4,1));
        panel.add(new JLabel("Invoice Number"));
        panel.add(new JLabel("Invoice Date"));
        panel.add(new JLabel("Customer Name"));
        panel.add(new JLabel("Invoice Total"));
        return panel;
    }


    private JPanel createInvoiceInfoFields(){
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4,1));
        panel.setLocation(0,0);
        invoiceNumberLabel = new JLabel();
        dateField = new JLabel();
        customerNameField = new JLabel();
        totalLabel = new JLabel("0.0");
        panel.setLayout(new GridLayout(4,1));
        panel.add(invoiceNumberLabel);
        panel.add(dateField);
        panel.add(customerNameField);
        panel.add(totalLabel);
        return panel;
    }

    private TableModel createTableModel() {
        return new TableModel() {
            @Override
            public int getRowCount() {
                return invoiceHeader != null ? invoiceHeader.getInvoiceLines().size() : 0 ;
            }

            @Override
            public int getColumnCount() {
                return cols.size();
            }

            @Override
            public String getColumnName(int i) {
                return cols.get(i);
            }

            @Override
            public Class<?> getColumnClass(int i) {
                switch (i){
                    case 0:
                    case 3:
                        return Integer.class;
                    case 2:
                    case 4:
                        return Double.class;
                    default: return String.class;
                }
            }

            @Override
            public boolean isCellEditable(int i, int i1) {
                return false;
            }

            @Override
            public Object getValueAt(int row, int col) {
                switch (col){
                    case 0: return invoiceHeader.getInvoiceLines().get(row).getInvoiceNumber();
                    case 1: return invoiceHeader.getInvoiceLines().get(row).getItemName();
                    case 2: return invoiceHeader.getInvoiceLines().get(row).getItemPrice();
                    case 3: return invoiceHeader.getInvoiceLines().get(row).getCount();
                    case 4: return  invoiceHeader.getInvoiceLines().get(row).getItemTotal();
                    default: return "";
                }
            }

            @Override
            public void setValueAt(Object o, int row, int col) {
            }

            @Override
            public void addTableModelListener(TableModelListener tableModelListener) {

            }

            @Override
            public void removeTableModelListener(TableModelListener tableModelListener) {

            }
        };
    }

    public void showLayout(){
        setVisible(true);
    }

    public void hideLayout(){
        setVisible(false);
        invoiceHeader = null;
    }


    public void showInvoice(InvoiceHeader invoiceHeader) {
        this.invoiceHeader = invoiceHeader;
        updateUi();
    }

    private void updateUi(){
        if (invoiceHeader == null) return;
        showLayout();
        invoiceNumberLabel.setText(String.valueOf(invoiceHeader.getInvoiceNumber()));
        dateField.setText(invoiceHeader.getDate());
        customerNameField.setText(invoiceHeader.getCustomerName());
        totalLabel.setText(String.valueOf(invoiceHeader.getTotal()));
        lines.setModel(createTableModel());
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        switch (actionEvent.getActionCommand()){
            case ActionCommands.EDIT:{
                listener.editInvoice(invoiceHeader);
                break;
            }
            case ActionCommands.HIDE:{
                listener.hideShowingInvoice();
                break;
            }
        }
    }

    public int getInvoiceNumber(){
        if (invoiceHeader == null) return -1;
        return invoiceHeader.getInvoiceNumber();
    }
}
