package bank;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

/**
 * Handles the logic for the Account scene
 *
 * @author Grupp 4
 */
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

    /**
     * Tries to make a desposit based on user-input and shows messages based on
     * the result
     */
    @FXML
    private void makeDeposit() {
        if (validateInput()) {
            // Math.Round used to round the deposit to two decimals
            double value = Math.round(Double.parseDouble(amount.getText().replaceAll(",", ".")) * 100.0) / 100.0;
            boolean success = bankLogic.deposit(selectedCustomerSSN, selectedCustomerAccountID, value);
            updateInfo();
            if (success) {
                showMessage("Transaction complete", error, false);
            } else {
                showMessage("Invalid amount", error, true);
            }
        } else {
            showMessage("Invalid input", error, true);
        }
        amount.clear();
    }

    /**
     * Tries to make a withdrawal based on user input and shows messages based
     * on the result
     */
    @FXML
    private void makeWithdrawal() {
        if (validateInput()) {
            // Math.Round used to round the deposit to two decimals
            double value = Math.round(Double.parseDouble(amount.getText().replaceAll(",", ".")) * 100.0) / 100.0;
            boolean success = bankLogic.withdraw(selectedCustomerSSN, selectedCustomerAccountID, value);
            updateInfo();
            if (success) {
                showMessage("Transaction complete", error, false);
            } else {
                showMessage("Invalid amount", error, true);
            }
        } else {
            showMessage("Invalid input", error, true);
        }
        amount.clear();
    }

    @FXML
    private void printAccountToFile() {
        if (bankLogic.transactionsToFile(selectedCustomerAccountID)) {
            showMessage("Transaction list printed to text file", error, false);
        } else {
            showMessage("Could not write to file. Check file permissions", error, true);
        }
    }

    /**
     * Validates the user input
     *
     * @return true if the amount is within range
     */
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

    /**
     * Updates the information in the scene
     */
    private void updateInfo() {
        accountInformation.setText(bankLogic.getAccount(selectedCustomerSSN,
                selectedCustomerAccountID));
        accountList = FXCollections.observableArrayList((ArrayList) bankLogic.getTransactions(selectedCustomerSSN, selectedCustomerAccountID));
        transactions.setItems(accountList);
    }

    /**
     * Initializes the scene
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        updateInfo();
    }

}
