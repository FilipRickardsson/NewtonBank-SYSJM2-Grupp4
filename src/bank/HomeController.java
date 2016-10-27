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
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class HomeController extends BaseController {

    private BankLogic bankLogic;

    private ObservableList<String> customerList;

    @FXML
    private TextField ssnSearchField;

    @FXML
    private ListView customerListView;

    @FXML
    private TextField nameInsert;

    @FXML
    private TextField ssnInsert;

    @FXML
    private void searchCustomer(ActionEvent event) {
        for (int i = 0; i < customerList.size(); i++) {
            if (customerList.get(i).contains(ssnSearchField.getCharacters())) {
                customerListView.getSelectionModel().select(i);
            }
        }

    }

    @FXML
    private void printCustomersToFile() {
        bankLogic.customerToFile();
    }

    @FXML
    private void removeCustomer() {
        showPopup();
    }

    @FXML
    private void selectCustomer(ActionEvent event) throws IOException {
        selectedCustomerSSN = bankLogic.getCustomerSsnViaIndex(customerListView
                .getSelectionModel().getSelectedIndex());
        
        Parent root = FXMLLoader.load(getClass().getResource("Customer.fxml"));
        Scene s = new Scene(root);
        Stage stg = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stg.setScene(s);
    }

    @FXML
    private void createCustomer() {
        bankLogic.addCustomer(nameInsert.getText(), Long.parseLong(ssnInsert
                .getText()));
        
        updateInfo();
    }

    @Override
    protected void popupYes() {
        System.out.println("Yes");
        selectedCustomerSSN = bankLogic.getCustomerSsnViaIndex(customerListView
                .getSelectionModel().getSelectedIndex());
        bankLogic.removeCustomer(selectedCustomerSSN);
        updateInfo();
        popup.close();
    }

    @Override
    protected void popupNo() {
        System.out.println("No");
        popup.close();
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
