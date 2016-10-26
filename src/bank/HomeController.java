package bank;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class HomeController implements Initializable {
    
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
        //Make customer highlighted in list below
        
    }
    
    @FXML
    private void printCustomersToFile() {
        //Print customers to text file
    }
    
    @FXML
    private void removeCustomer() {
        //Removes a customer - Pop up?
    }
    
    @FXML
    private void selectCustomer() {
        //Selects a customer
    }
    
    @FXML
    private void createCustomer() {
        //Creates customer
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bankLogic = BankLogic.getBankLogic();
        customerList = FXCollections.observableArrayList(bankLogic.getCustomers());
        
        customerListView.setItems(customerList);
    }    
    
}
