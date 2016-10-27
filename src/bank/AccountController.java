package bank;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class AccountController extends BaseController {

    private BankLogic bankLogic;

    private ObservableList<String> listan;

    @FXML
    private TextField amount;

    @FXML
    private ListView transactions;

    @FXML
    private Button deposit;

    @FXML
    private Button withdrawal;

    @FXML
    private void handleAmount(ActionEvent event) {

    }

 

    @FXML
    private void makeDeposit(ActionEvent event) {    
    
        bankLogic.deposit(selectedCustomerSSN, selectedCustomerAccountID, Double.parseDouble(amount.getText()));
    
        updateInfo();
    
    }

    @FXML
    private void makeWithdrawal(ActionEvent event) {
        
        bankLogic.withdraw(selectedCustomerSSN, selectedCustomerAccountID, Double.parseDouble(amount.getText()));
   
        updateInfo();
    
    }

    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("Account Controller");
    }



    @Override
    protected void popupYes() {
        System.out.println("Yes");
        popup.close();
    }

    @Override
    protected void popupNo() {
        System.out.println("No");
        popup.close();
    }

    public void updateInfo(){
        
        selectedCustomerSSN = 7912120101L;
        selectedCustomerAccountID= 1002;
        
        listan = FXCollections.observableArrayList((ArrayList)bankLogic.getTransactions(selectedCustomerSSN, selectedCustomerAccountID));
        transactions.setItems(listan);
        
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
