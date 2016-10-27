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
    @FXML
    private TextField name;
    @FXML
    private TextField ssn;
    @FXML
    private Label account;
    @FXML
    private TextField balance;
    @FXML
    private ListView accountList;

    private ObservableList<String> accounts;

    @FXML
    private void nameSet() {
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
 @FXML
    protected void handleHome() {
        showPopup();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bankLogic = BankLogic.getBankLogic();
        //TODO fix
        accounts = FXCollections.observableArrayList(bankLogic.getCustomers());
        
        accountList.setItems(accounts);

        try {
            loadPopup();
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
