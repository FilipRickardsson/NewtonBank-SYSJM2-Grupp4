package bank;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
        for(int i = 0; i < customerList.size(); i++) {
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
        //Removes a customer - Pop up?
    }
    
    @FXML
    private void selectCustomer(ActionEvent event) throws IOException {
        //Selects a customer
        
        Parent root = FXMLLoader.load(getClass().getResource("Customer.fxml"));
        Scene s = new Scene(root);
        Stage stg = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stg.setScene(s);
    }
    
    @FXML
    private void createCustomer() {
        //Creates customer - Pop up
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bankLogic = BankLogic.getBankLogic();
        
        customerList = FXCollections.observableArrayList(bankLogic.getCustomers());
        

        
        customerListView.setItems(customerList);
    }    
    
}
