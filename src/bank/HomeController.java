package bank;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

/**
 * Handles the logic for the Home scene
 *
 * @author Grupp 4
 */
public class HomeController extends BaseController {

    private ObservableList<String> customerList;

    @FXML
    private TextField ssnSearchField;

    @FXML
    private Label wrongSearch;

    @FXML
    private Label listViewInformation;

    @FXML
    private Label wrongCreateCustomer;

    @FXML
    private ListView customerListView;

    @FXML
    private TextField firstNameInsert;

    @FXML
    private TextField lastNameInsert;

    @FXML
    private TextField ssnInsert;

    @FXML
    private Button btnCustomer;

    @FXML
    private Button btnAccount;

    /**
     * Searches for customer by SSN. limited input. Error handling if symbols or
     * length not right
     */
    @FXML
    private void searchCustomer() {
        showMessage("", wrongSearch, false);
        String searchStr;
        boolean match = false;

        try {
            if (!ssnSearchField.getText().isEmpty()) {
                long testInput = Long.parseLong(ssnSearchField.getText());
                if (ssnSearchField.getText().length() == 12) {
                    for (int i = 0; i < customerList.size(); i++) {
                        searchStr = customerList.get(i).substring(customerList.get(i).length() - 12);
                        if (searchStr.equals(ssnSearchField.getText())) {
                            customerListView.getSelectionModel().select(i);
                            match = true;
                        }
                    }
                    if (!match) {
                        showMessage("No match", wrongSearch, true);
                    }
                } else {
                    showMessage("Wrong input length", wrongSearch, true);
                }
            } else {
                showMessage("Nothing in search field", wrongSearch, true);
            }
        } catch (NumberFormatException e) {
            showMessage("Only numbers allowed", wrongSearch, true);
        }

    }

    /**
     * Writes a textfile with a list of customer which is saved in the root
     * folder of the application
     */
    @FXML
    private void printCustomersToFile() {
        if (bankLogic.customerToFile()) {
            showMessage("Customer list printed to text file", listViewInformation, false);
        } else {
            showMessage("Could not write to file. Check file permissions", listViewInformation, true);
        }
    }

    /**
     * Shows a confirmation popup when the remove customer button is pressed
     */
    @FXML
    private void removeCustomer() {
        if (customerListView.getSelectionModel().getSelectedItem() != null) {
            setPopupMessage("Are you sure you want to remove\ncustomer?");
            showPopup();
        } else {
            showMessage("Nothing selected", listViewInformation, true);
        }
    }

    /**
     * If a customer is selected, the application goes to next scene
     *
     * @param event
     * @throws IOException If the scene could not be loaded
     */
    @FXML
    private void selectCustomer(ActionEvent event) throws IOException {
        if (customerListView.getSelectionModel().getSelectedItem() != null) {
            selectedCustomerSSN = bankLogic.getCustomerSsnViaIndex(customerListView
                    .getSelectionModel().getSelectedIndex());
            loadScene("Customer.fxml");
        } else {
            showMessage("Nothing selected", listViewInformation, true);
        }
    }

    /**
     * Checks that the different textfields has correct input and sends the
     * information for validation in BankLogic which then creates a new
     * customer. The error handling checks if the names and SSN is correct
     * length and shows error messages based on result
     */
    @FXML
    private void createCustomer() {
        if (!firstNameInsert.getText().isEmpty() && !lastNameInsert.getText().isEmpty() && !ssnInsert.getText().isEmpty()) {

            try {
                if (bankLogic.validateName(firstNameInsert.getText(), lastNameInsert.getText())) {
                    String fullName = firstNameInsert.getText() + " "
                            + lastNameInsert.getText();

                    if (ssnInsert.getText().length() == 12) {
                        if (bankLogic.validateSSN(Long.parseLong(ssnInsert.getText()))) {
                            if (bankLogic.addCustomer(fullName, Long.parseLong(ssnInsert
                                    .getText()))) {

                                updateInfo();
                                firstNameInsert.clear();
                                lastNameInsert.clear();
                                ssnInsert.clear();
                                showMessage("Customer added!", wrongCreateCustomer, false);
                            } else {
                                showMessage("Customer already exist", wrongCreateCustomer, true);
                            }
                        } else {
                            showMessage("Invalid ssn", wrongCreateCustomer, true);
                        }
                    } else {
                        showMessage("Wrong input length on ssn", wrongCreateCustomer, true);
                    }
                } else {
                    showMessage("Invalid symbols i names", wrongCreateCustomer, true);
                }
            } catch (NumberFormatException e) {
                showMessage("Only numbers allowed in ssn", wrongCreateCustomer, true);
            }
        } else {
            showMessage("Missing information", wrongCreateCustomer, true);
        }
    }

    @Override
    protected void popupYes() {
        selectedCustomerSSN = bankLogic.getCustomerSsnViaIndex(customerListView
                .getSelectionModel().getSelectedIndex());
        selectedCustomerAccountID = 0;
        popup.close();
        loadScene("Info.fxml");
    }

    /**
     * Updates the information displayed in the scene
     */
    private void updateInfo() {
        customerList = FXCollections.observableArrayList(bankLogic.getCustomers());
        customerListView.setItems(customerList);
    }

    /**
     * Initializes the scene
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnAccount.setVisible(false);
        btnCustomer.setVisible(false);
        ssnSearchField.setPromptText("YYYYMMDDXXXX");
        ssnInsert.setPromptText("YYYYMMDDXXXX");

        updateInfo();
    }

}
