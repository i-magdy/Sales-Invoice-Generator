import controller.InvoicesController;
import models.FileOperations;
import view.MainFrame;

public class Main {
    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        InvoicesController controller = new InvoicesController(frame);
        controller.setInvoiceHeaders(FileOperations.readFile());
        controller.updateView();
    }
}