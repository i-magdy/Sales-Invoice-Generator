package models;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class FileOperations {


    @Deprecated
    private static final FileFilter filter = new FileFilter() {
        @Override
        public boolean accept(File file) {
            if (file.isDirectory()) return true;
            String name = file.getName();
            return name.endsWith(".csv");
        }

        @Override
        public String getDescription() {
            return "*.csv";
        }
    };
    @Deprecated(since = "Load files from Directory FileChooser")
    public static ArrayList<InvoiceHeader> readFile(Component component){
        ArrayList<InvoiceHeader> headersList = new ArrayList<>();
        JFileChooser invoiceHeaderChooser = new JFileChooser();
        invoiceHeaderChooser.setDialogTitle("Open Invoice Header");
        JFileChooser invoiceLinesChooser = new JFileChooser();
        invoiceLinesChooser.setDialogTitle("Open Invoice Lines");
        invoiceHeaderChooser.setFileFilter(filter);
        invoiceLinesChooser.setFileFilter(filter);
        int resultHeaders = invoiceHeaderChooser.showOpenDialog(component);
        if (resultHeaders == JFileChooser.APPROVE_OPTION){
            String headersPath = invoiceHeaderChooser.getSelectedFile().getPath();
            if (!headersPath.endsWith(".csv")){
                JOptionPane.showMessageDialog(component,"Wrong InvoiceHeader.csv File!","Load InvoiceHeader",JOptionPane.PLAIN_MESSAGE);
                return new ArrayList<>();
            }
            int resultLines = invoiceLinesChooser.showOpenDialog(component);
            if (resultLines == JFileChooser.APPROVE_OPTION) {
                String linesPath = invoiceLinesChooser.getSelectedFile().getPath();
                if (!linesPath.endsWith(".csv")){
                    JOptionPane.showMessageDialog(component,"Wrong InvoiceLine.csv File!","Load InvoiceLine",JOptionPane.PLAIN_MESSAGE);
                    return new ArrayList<>();
                }
                FileReader headersFile = null;
                BufferedReader headersBuffer = null;
                FileReader linesFile = null;
                BufferedReader linesBuffer = null;
                try {
                    headersFile = new FileReader(headersPath);
                    headersBuffer = new BufferedReader(headersFile);
                    linesFile = new FileReader(linesPath);
                    linesBuffer = new BufferedReader(linesFile);
                    String headerRow;
                    String lineRow;
                    while ((headerRow = headersBuffer.readLine()) != null) {
                        StringTokenizer headerToken = new StringTokenizer(headerRow, ",");
                        if (headerToken.countTokens() == 3) {
                            String invoiceNum = headerToken.nextToken();
                            String invoiceDate = headerToken.nextToken();
                            String customerName = headerToken.nextToken();
                            if (invoiceNum.equals("InvoiceNum")){ continue; }
                            InvoiceHeader header = new InvoiceHeader(Integer.parseInt(invoiceNum),invoiceDate,customerName);
                            headersList.add(header);
                        }
                    }
                    while ((lineRow = linesBuffer.readLine()) != null){
                        StringTokenizer lineToken = new StringTokenizer(lineRow,",");
                        if (lineToken.countTokens() == 4){
                            String invoiceNum = lineToken.nextToken();
                            String itemName = lineToken.nextToken();
                            String itemPrice = lineToken.nextToken();
                            String itemCount = lineToken.nextToken();
                            if (invoiceNum.equals("InvoiceNum")){ continue; }
                            InvoiceLine line = new InvoiceLine(Integer.parseInt(invoiceNum),itemName,Double.parseDouble(itemPrice),Integer.parseInt(itemCount));
                            line.setItemTotal(line.getItemPrice()*line.getCount());
                            headersList.forEach((it) ->{
                                if (it.getInvoiceNumber() == Integer.parseInt(invoiceNum)){
                                    it.addInvoiceLine(line);
                                    it.setTotal(it.getTotal() + line.getItemTotal());
                                }
                            });
                        }
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (headersBuffer != null) {
                        try {
                            headersBuffer.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return headersList;
    }

    @Deprecated(since = "Saving files in directory FileChooser")
    public void writFile(ArrayList<InvoiceHeader> invoiceHeaders,Component component){
        JFileChooser chooser =  new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setDialogTitle("Choose Folder to save");
        int result = chooser.showOpenDialog(component);
        if (result == JFileChooser.APPROVE_OPTION){
            String path = chooser.getSelectedFile().getPath();
            if (chooser.getSelectedFile().isDirectory()){
                FileWriter headersWriter = null;
                FileWriter linesWriter = null;
                try {
                    headersWriter = new FileWriter(path+"/InvoiceHeader.csv");
                    linesWriter = new FileWriter(path+"/InvoiceLine.csv");
                    headersWriter.append("InvoiceNum");
                    headersWriter.append(",");
                    headersWriter.append("InvoiceDate");
                    headersWriter.append(",");
                    headersWriter.append("CustomerName");
                    headersWriter.append("\n");
                    linesWriter.append("InvoiceNum");
                    linesWriter.append(",");
                    linesWriter.append("ItemName");
                    linesWriter.append(",");
                    linesWriter.append("ItemPrice");
                    linesWriter.append(",");
                    linesWriter.append("Count");
                    linesWriter.append("\n");
                    for (InvoiceHeader header : invoiceHeaders){
                        String num = String.valueOf(header.getInvoiceNumber());
                        headersWriter.append(String.join(",",List.of(num,header.getDate(),header.getCustomerName())));
                        headersWriter.append("\n");
                        for (InvoiceLine line: header.getInvoiceLines()){
                            linesWriter.append(String.join(",",List.of(num,line.getItemName(),String.valueOf(line.getItemPrice()),String.valueOf(line.getCount()))));
                            linesWriter.append("\n");
                        }
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }finally {
                    if (linesWriter != null) {
                        try {
                            headersWriter.flush();
                            headersWriter.close();
                            linesWriter.flush();
                            linesWriter.close();
                            JOptionPane.showMessageDialog(component,"SAVED! at: "+path,"Save",JOptionPane.PLAIN_MESSAGE);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        }

    }

    public static ArrayList<InvoiceHeader> readFile(){
        ArrayList<InvoiceHeader> headersList = new ArrayList<>();
        FileReader headersFile = null;
        BufferedReader headersBuffer = null;
        FileReader linesFile = null;
        BufferedReader linesBuffer = null;
        try {
            headersFile = new FileReader("InvoiceHeader.csv");
            headersBuffer = new BufferedReader(headersFile);
            linesFile = new FileReader("InvoiceLine.csv");
            linesBuffer = new BufferedReader(linesFile);
            String headerRow;
            String lineRow;
            while ((headerRow = headersBuffer.readLine()) != null) {
                StringTokenizer headerToken = new StringTokenizer(headerRow, ",");
                if (headerToken.countTokens() == 3) {
                    String invoiceNum = headerToken.nextToken();
                    String invoiceDate = headerToken.nextToken();
                    String customerName = headerToken.nextToken();
                    if (invoiceNum.equals("InvoiceNum")){ continue; }
                    InvoiceHeader header = new InvoiceHeader(Integer.parseInt(invoiceNum),invoiceDate,customerName);
                    headersList.add(header);
                }
            }
            while ((lineRow = linesBuffer.readLine()) != null){
                StringTokenizer lineToken = new StringTokenizer(lineRow,",");
                if (lineToken.countTokens() == 4){
                    String invoiceNum = lineToken.nextToken();
                    String itemName = lineToken.nextToken();
                    String itemPrice = lineToken.nextToken();
                    String itemCount = lineToken.nextToken();
                    if (invoiceNum.equals("InvoiceNum")){ continue; }
                    InvoiceLine line = new InvoiceLine(Integer.parseInt(invoiceNum),itemName,Double.parseDouble(itemPrice),Integer.parseInt(itemCount));
                    line.setItemTotal(line.getItemPrice()*line.getCount());
                    headersList.forEach((it) ->{
                        if (it.getInvoiceNumber() == Integer.parseInt(invoiceNum)){
                            it.addInvoiceLine(line);
                            it.setTotal(it.getTotal() + line.getItemTotal());
                        }
                    });
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        } finally {
            if (headersBuffer != null) {
                try {
                    headersBuffer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return headersList;
    }

    public static void writFile(ArrayList<InvoiceHeader> invoiceHeaders){
        FileWriter headersWriter = null;
        FileWriter linesWriter = null;
        try {
            headersWriter = new FileWriter("InvoiceHeader.csv");
            linesWriter = new FileWriter("InvoiceLine.csv");
            headersWriter.append("InvoiceNum");
            headersWriter.append(",");
            headersWriter.append("InvoiceDate");
            headersWriter.append(",");
            headersWriter.append("CustomerName");
            headersWriter.append("\n");
            linesWriter.append("InvoiceNum");
            linesWriter.append(",");
            linesWriter.append("ItemName");
            linesWriter.append(",");
            linesWriter.append("ItemPrice");
            linesWriter.append(",");
            linesWriter.append("Count");
            linesWriter.append("\n");
            for (InvoiceHeader header : invoiceHeaders){
                String num = String.valueOf(header.getInvoiceNumber());
                headersWriter.append(String.join(",",List.of(num,header.getDate(),header.getCustomerName())));
                headersWriter.append("\n");
                for (InvoiceLine line: header.getInvoiceLines()){
                    linesWriter.append(String.join(",",List.of(num,line.getItemName(),String.valueOf(line.getItemPrice()),String.valueOf(line.getCount()))));
                    linesWriter.append("\n");
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if (linesWriter != null) {
                try {
                    headersWriter.flush();
                    headersWriter.close();
                    linesWriter.flush();
                    linesWriter.close();
                    JOptionPane.showMessageDialog(null,"SAVED!","Save File",JOptionPane.PLAIN_MESSAGE);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }


    public static void main(String[] args){
        ArrayList<InvoiceHeader> invoiceHeaders = readFile();
        invoiceHeaders.forEach((it)-> {
            System.out.println(it.getInvoiceNumber());
            System.out.println("{");
            System.out.println(it.getDate()+","+it.getCustomerName());
            it.getInvoiceLines().forEach(invoiceLine -> {
                System.out.println(invoiceLine.getItemName()+","+invoiceLine.getItemPrice()+","+invoiceLine.getCount());
            });
            System.out.println("}");
        });
    }


}
