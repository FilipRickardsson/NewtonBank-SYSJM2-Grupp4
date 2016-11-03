package bank;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

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
     * Searching for customer by SSN. limited input. Error handling if symbols
     * or length not right
     */
    @FXML
    private void searchCustomer() {
        showMessage("", wrongSearch, false);
        String searchStr;
        boolean match = false;

        try {
            if (!ssnSearchField.getText().isEmpty()) {
                long testInput = Long.parseLong(ssnSearchField.getText());
                if (ssnSearchField.getText().length() == 10) {
                    for (int i = 0; i < customerList.size(); i++) {
                        searchStr = customerList.get(i).substring(customerList.get(i).length() - 10);
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
     * File with customerslist saves in
     * C:\Users\"MyCoputer"\Documents\GitProject\Projekt2Bank
     *
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
     * removes customer from list. PopUp appers for confirmation.
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
     * Selected customer, go to next scene
     *
     * @param event
     * @throws IOException
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
     * Create customr with firstname and lastname that combine to one String
     * name. Error handling: if existing SSn, if SSN lenght too long, Symbols is
     * allowed, no input.
     */
    @FXML
    private void createCustomer() {
        if (!firstNameInsert.getText().isEmpty() && !lastNameInsert.getText().isEmpty() && !ssnInsert.getText().isEmpty()) {

            try {
                if (bankLogic.validateName(firstNameInsert.getText(), lastNameInsert.getText())) {
                    String fullName = firstNameInsert.getText() + " "
                            + lastNameInsert.getText();

                    if (ssnInsert.getText().length() == 10) {
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

    private void updateInfo() {
        customerList = FXCollections.observableArrayList(bankLogic.getCustomers());

        customerListView.setItems(customerList);

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnAccount.setVisible(false);
        btnCustomer.setVisible(false);
        ssnSearchField.setPromptText("YYMMDDXXXX");

        updateInfo();

        try {
            loadPopup();
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
