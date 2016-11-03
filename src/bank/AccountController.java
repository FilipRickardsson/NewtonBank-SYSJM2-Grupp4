package bank;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class AccountController extends BaseController {

    private ObservableList<String> accountList;

    @FXML
    private Label accountInformation;

    @FXML
    private TextField amount;

    @FXML
    private ListView transactions;

    @FXML
    private Label error;

    @FXML
    private void makeDeposit() {
        if (validateInput()) {
            double value = Math.round(Double.parseDouble(amount.getText().replaceAll(",", ".")) * 100.0) / 100.0;
            boolean success = bankLogic.deposit(selectedCustomerSSN, selectedCustomerAccountID, value);
            updateInfo();
            if (success) {
                error.setText("Transaction complete");
            } else {
                error.setText("Invalid amount");
            }
        } else {
            error.setText("Invalid input");
        }
        amount.clear();
    }

    @FXML
    private void makeWithdrawal() {
        if (validateInput()) {
            double value = Math.round(Double.parseDouble(amount.getText().replaceAll(",", ".")) * 100.0) / 100.0;
            boolean success = bankLogic.withdraw(selectedCustomerSSN, selectedCustomerAccountID, value);
            updateInfo();
            if (success) {
                error.setText("Transaction complete");
            } else {
                error.setText("Invalid amount");
            }
        } else {
            error.setText("Invalid input");
        }
        amount.clear();
    }

    //test
    private boolean validateInput() {
        try {
            double input = Double.parseDouble(amount.getText().replaceAll(",", "."));
            if (input > 1000000000 || input < 0) {
                throw new NumberFormatException();
            }
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    @Override
    protected void popupYes() {
        popup.close();
    }

    public void updateInfo() {
        accountInformation.setText(bankLogic.getAccount(selectedCustomerSSN,
                selectedCustomerAccountID));
        accountList = FXCollections.observableArrayList((ArrayList) bankLogic.getTransactions(selectedCustomerSSN, selectedCustomerAccountID));
        transactions.setItems(accountList);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        updateInfo();

        try {
            loadPopup();
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
