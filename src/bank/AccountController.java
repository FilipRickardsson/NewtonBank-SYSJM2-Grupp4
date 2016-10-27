package bank;

import java.awt.Button;
import java.awt.TextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class AccountController extends BaseController {

    private BankLogic bankLogic;

    private ObservableList<String> listan;

    @FXML
    private TextField amount;

    @FXML
    private ListView transactions;

    @FXML
    private Button deposit;

    @FXML
    private Button withdrawal;

    @FXML
    private void handleAmount(ActionEvent event) {

    }

    @FXML
    private void makeTransaction(ActionEvent event) {
        //transaction.toString();
        
    }

    @FXML
    private void makeDeposit(ActionEvent event) {
        //bankLogic.deposit(customer.getSsn(), transaction.getAccountID(), transaction.getAmount() );
    }

    @FXML
    private void makeWithdrawal(ActionEvent event) {

    }

    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("Account Controller");
    }

    public void init(long ssn, int accountID) {
        listan = FXCollections.observableArrayList(bankLogic.getTransactions(ssn, accountID));
        transactions.setItems(listan);
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
        bankLogic = BankLogic.getBankLogic();

        try {
            loadPopup();
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
}
