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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class HomeController extends BaseController {

    private BankLogic bankLogic;

    private ObservableList<String> customerList;

    @FXML
    private TextField ssnSearchField;

    @FXML
    private Label wrongSearch;

    @FXML
    private ListView customerListView;

    @FXML
    private TextField firstNameInsert;

    @FXML
    private TextField lastNameInsert;

    @FXML
    private TextField ssnInsert;

    @FXML
    private void searchCustomer() {
        wrongSearch.setText("");
        String searchStr = null;
        boolean match = false;

        try {
            if (!ssnSearchField.getText().isEmpty()) {
                long testInput = Long.parseLong(ssnSearchField.getText());
                for (int i = 0; i < customerList.size(); i++) {
                    searchStr = customerList.get(i).substring(customerList.get(i).length() - 10);
                    if (searchStr.equals(ssnSearchField.getText())) {
                        customerListView.getSelectionModel().select(i);
                        match = true;
                    }
                    
                }
                if (!match) {
                    wrongSearch.setText("No match");
                }
            } else {
                wrongSearch.setText("Nothing in search field");
            }
        } catch (NumberFormatException e) {
            wrongSearch.setText("Only numbers allowed");
        }

    }

    @FXML
    private void printCustomersToFile() {
        bankLogic.customerToFile();
    }

    @FXML
    private void removeCustomer() {
        setPopupMessage("Are you sure?");
        showPopup();
    }

    @FXML
    private void selectCustomer(ActionEvent event) throws IOException {
        selectedCustomerSSN = bankLogic.getCustomerSsnViaIndex(customerListView
                .getSelectionModel().getSelectedIndex());

        loadScene("Customer.fxml");
    }

    @FXML
    private void createCustomer() {
        //Fix with first/last name

        String fullName = firstNameInsert.getText() + " "
                + lastNameInsert.getText();

        bankLogic.addCustomer(fullName, Long.parseLong(ssnInsert
                .getText()));

        updateInfo();
    }

    @Override
    protected void popupYes() {
        System.out.println("Yes");
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
        bankLogic = BankLogic.getBankLogic();

        updateInfo();

        try {
            loadPopup();
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
