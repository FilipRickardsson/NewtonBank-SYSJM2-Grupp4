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

public class AccountController implements Initializable{
    

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
    private void handleAmount(ActionEvent event){
        
        
        
    }
    
    @FXML
    private void makeTransaction(ActionEvent event){
        
        

            
            //transaction.toString();

        
    }
    
    @FXML
    private void makeDeposit(ActionEvent event){
        

         //bankLogic.deposit(customer.getSsn(), transaction.getAccountID(), transaction.getAmount() );

        
    }
    
    @FXML
    private void makeWithdrawal(ActionEvent event){
        
    }
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("Account Controller");
    }
    
    public void init(long ssn, int accountID){
         

         
        listan = FXCollections.observableArrayList(bankLogic.getTransactions(ssn, accountID));
                
    }
            
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        bankLogic = BankLogic.getBankLogic();
        
        
        
    }    


}





   
