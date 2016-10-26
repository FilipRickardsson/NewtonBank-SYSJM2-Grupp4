package bank;

import java.awt.Label;
import java.awt.TextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class InfoController extends BaseController {

    private BankLogic bankLogic;
    private long Ssn;
    @FXML
    private TextField name;
    @FXML
    private Label ssn;
    @FXML
    private Label Deleted;
    @FXML
    private Label account;
    @FXML
    private Label balance;
    @FXML
    private ListView accountList;

    private ObservableList<String> accounts;

    @FXML
    private void nameSsnSet() {
        // sett name and ssn textfields based on wich customer/account is closing
    }

    @FXML
    private void balanceSet() {
        // Set the balance textfield to total balance + interest for 
        // closed accounts
    }

    @Override
    protected void popupYes() {
        System.out.println("Yes");
        popup.close();

    }

    @Override
    protected void popupNo() {
        System.out.println("No");
        popup.close();

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        BankLogic.getBankLogic();
        accounts = FXCollections.observableArrayList(bankLogic.getAccount(9702020101L, 1001));

        accountList.setItems(accounts);

        try {
            loadPopup();
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
