package ui;

import models.InvoiceHeader;
import models.InvoiceLine;
import util.ActionCommands;

import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class CreateInvoiceFrame extends JPanel implements ActionListener {


    private InvoiceHeader invoiceHeader;
    private ActionsListener listener;
    private JTextField customerNameField;
    private JTextField dateField;
    private JLabel totalLabel;
    private JLabel invoiceNumberLabel;
    private List<String> cols = new ArrayList<>(5);
    private List<InvoiceLine> invoiceLines = new ArrayList<>();
    private JTable lines;


    public CreateInvoiceFrame(ActionsListener listener){
        this.listener = listener;
        setLocation(20,0);
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        cols.add("No.");
        cols.add("Item Name");
        cols.add("Item price");
        cols.add("Count");
        cols.add("Item Total");
        add(createInvoiceInfoPanel());
        lines = new JTable(createTableModel());
        add(new JScrollPane(lines));
        createPanelActions();
        hideLayout();
    }


    private void createPanelActions(){
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");
        saveButton.setActionCommand(ActionCommands.SAVE);
        cancelButton.setActionCommand(ActionCommands.CANCEL);
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
        dateField = new JTextField("",32);
        dateField.setToolTipText("DD/MM/YYYY");
        customerNameField = new JTextField();
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
                return invoiceLines.size() + 1;
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
                return i1 != 0  && i1 != 4;
            }

            @Override
            public Object getValueAt(int row, int col) {
                totalLabel.setText(totalPrice());
                switch (col){
                   case 0: return invoiceLines.size() > 0 ? row+ 1 : "";
                   case 1: return invoiceLines.size() > row ? invoiceLines.get(row).getItemName() : "";
                   case 2: return invoiceLines.size() > row ? invoiceLines.get(row).getItemPrice() : 0.0;
                   case 3: return invoiceLines.size() > row ? invoiceLines.get(row).getCount() : 0;
                   case 4: return  invoiceLines.size() > row ? invoiceLines.get(row).getItemTotal() : 0.0;
                   default: return "";
               }
            }

            @Override
            public void setValueAt(Object o, int row, int col) {
                if (invoiceLines.size() == 0 || row > invoiceLines.size()-1){
                    InvoiceLine line = new InvoiceLine();
                    switch (col){
                        case 1: {
                            line.setItemName(o.toString());
                            break;
                        }
                        case 2: {
                            if (o instanceof Double && (Double) o > 0.0) {
                                line.setItemPrice((Double) o);
                            }
                            break;
                        }
                        case 3:{
                            if (o instanceof  Integer && (Integer) o > 0){
                                line.setCount((Integer) o);
                            }
                            break;
                        }
                    }
                    line.setItemTotal(line.getItemPrice() * line.getCount());
                    invoiceLines.add(line);
                }else {
                    InvoiceLine line = invoiceLines.get(row);
                    switch (col){
                        case 1: {
                            line.setItemName(o.toString());
                            break;
                        }
                        case 2: {
                            if (o instanceof Double && (Double) o > 0.0) {
                                line.setItemPrice((Double) o);
                            }
                            break;
                        }
                        case 3:{
                            if (o instanceof  Integer && (Integer) o > 0){
                                line.setCount((Integer) o);
                            }
                            break;
                        }
                    }
                    line.setItemTotal(line.getItemPrice() * line.getCount());
                    invoiceLines.set(row,line);
                }
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
        invoiceHeader = new InvoiceHeader();
        setVisible(true);
    }

    public void hideLayout(){
        invoiceHeader = new InvoiceHeader();
        setVisible(false);
        clearData();
    }

    public void setInvoiceNumber(int i){
        invoiceHeader.setInvoiceNumber(i);
        invoiceNumberLabel.setText(String.valueOf(invoiceHeader.getInvoiceNumber()));
    }

    private String totalPrice(){
        if (invoiceLines.size() > 0){
            invoiceHeader.setTotal(0.0);
            for (int i =0; i < invoiceLines.size();i++){
                Double totalLine = invoiceLines.get(i).getItemTotal();
                invoiceHeader.setTotal(invoiceHeader.getTotal() + totalLine);
            }
        }
        return String.valueOf(invoiceHeader.getTotal());
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        switch (actionEvent.getActionCommand()){
            case ActionCommands.SAVE:{
                String name = customerNameField.getText();
                String date = dateField.getText();
                if (!name.isBlank() && name.length() > 2 && !date.isBlank() && isDataValid() && isDateFormatValid()){
                    invoiceHeader.setCustomerName(name);
                    invoiceHeader.setDate(date);
                    invoiceHeader.setInvoiceLines(invoiceLines);
                    listener.createInvoiceAction(invoiceHeader);
                    hideLayout();
                }else {
                    JOptionPane.showMessageDialog(this,"Check Your Data Input,Something went wrong","Wrong",JOptionPane.PLAIN_MESSAGE);
                }
                break;
            }
            case ActionCommands.CANCEL:{
                listener.cancelCreatingAction();
                break;
            }
            default: System.out.println(actionEvent.getActionCommand());
        }
    }

    private boolean isDataValid(){
        if (invoiceLines.isEmpty()) return false;
        boolean b = true;
        for (int i =0;i < invoiceLines.size();i++){
            InvoiceLine invoiceLine = invoiceLines.get(i);
            if (invoiceLine.getItemName().isBlank() || invoiceLine.getItemTotal() == 0.0 || invoiceLine.getCount() == 0 || invoiceLine.getItemPrice() == 0.0){
                b = false;
            }
        }
        return b;
    }

    private boolean isDateFormatValid(){
        String date = dateField.getText();
        if (date.isBlank()) return false;
        boolean b = true;
        if (date.length() == 10) {
            String day = date.substring(0, 2);
            String month = date.substring(3, 5);
            String year = date.substring(6, 10);
            try {
                int dd = Integer.parseInt(day);
                int mm = Integer.parseInt(month);
                int yyyy = Integer.parseInt(year);
                return  dd > 0 && dd <= 31 && mm > 0 && mm <= 12;
            }catch (NumberFormatException e){
                return false;
            }
        }else { b = false; }
        return b;
    }
    private void clearData(){
        invoiceLines = new ArrayList<>();
        invoiceNumberLabel.setText("");
        totalLabel.setText("");
        dateField.setText("");
        customerNameField.setText("");
        lines.setModel(createTableModel());
    }
}
