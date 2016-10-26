package bank;

import java.awt.Button;
import java.awt.TextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

public class CustomerController implements Initializable {
    @FXML
    Button change;
    @FXML
    Button remove;
    @FXML
    Button select;
    @FXML
    TextField changeName;
    @FXML
    ListView listOfAccounts;
    @FXML
    private ObservableList<String> accounts;
    @FXML
    private void buttonChange(ActionEvent event) {
        
    }
    @FXML
    private void buttonRemove(ActionEvent event) {
        
    }
    @FXML
    private void buttonSelect(ActionEvent event) {
        
    }
    public void init(String name, long ssn){
        accounts=FXCollections.observableArrayList();
        listOfAccounts.setItems(accounts);
        
        
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
}
