package bank;

import java.awt.Label;
import java.awt.TextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

public class InfoController extends BaseController {
    
    private BankLogic bankLogic;
    private long Ssn;
    @FXML
    private TextField name;
    @FXML
    private Label ssn;
    @FXML
    private Label Deleted;
    @FXML
    private Label account;
    @FXML
    private Label balance;
    @FXML
    private ListView accountList;
            
    @FXML
            private void nameSsnSet(){
                // sett name and ssn textfields based on wich customer/account is closing
        }
            @FXML
            private void balanceSet(){
                //Set the balance textfield to total balance + interest for 
//                closed accounts
        
}
                
    
    ObservableList<String> accounts;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        BankLogic.getBankLogic();
        accounts =FXCollections.observableArrayList(bankLogic.getAccount(9702020101L, 1001));
        
        accountList.setItems(accounts);

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
