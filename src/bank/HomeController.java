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
    
    @FXML
    private void searchCustomer() {
        wrongSearch.setText("");
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
                        wrongSearch.setText("No match");
                    }
                } else {
                    wrongSearch.setText("Wrong input length");
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
        String firstName = firstNameInsert.getText().replaceAll("\\s", "");
        
        String lastName = lastNameInsert.getText().replaceAll("\\s", "");
        
        try {
            if (true) {
                String fullName = firstName + " "
                        + lastName;
                if (ssnInsert.getText().length() == 10) {
                    bankLogic.addCustomer(fullName, Long.parseLong(ssnInsert
                            .getText()));
                    updateInfo();
                } else {
                    wrongCreateCustomer.setText("Wrong input length on ssn");
                }
            } else {
                wrongCreateCustomer.setText("A name can't contain numbers");
            }
        } catch (NumberFormatException e) {
            wrongCreateCustomer.setText("Only numbers allowed");
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
        
        updateInfo();
        
        try {
            loadPopup();
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
