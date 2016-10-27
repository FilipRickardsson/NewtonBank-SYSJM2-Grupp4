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

public class CustomerController extends BaseController {
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
    
    public void sendInformation(String text) {
        System.out.println(text);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @Override
    protected void popupYes() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void popupNo() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void init() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
