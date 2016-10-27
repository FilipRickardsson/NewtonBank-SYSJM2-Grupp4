package bank;

import java.awt.Label;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class InfoController extends BaseController {

    private BankLogic bankLogic;
    @FXML
    private ListView accountList;

    private ObservableList<String> customerInformation;
    
   

    @Override
    protected void popupYes() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Home.fxml"));
            Scene s = new Scene(root);
            main.setScene(s);
            // ladda home scen
            
            popup.close();
        } catch (IOException ex) {
            Logger.getLogger(InfoController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

 @FXML
    protected void handleHome() {
        showPopup();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bankLogic = BankLogic.getBankLogic();
        //TODO fix
        
        if (selectedCustomerAccountID == 0){
            
            customerInformation = FXCollections.observableArrayList(bankLogic.removeCustomer(selectedCustomerSSN));
            accountList.setItems(customerInformation);
        }
        else{
        customerInformation = FXCollections.observableArrayList(bankLogic.closeAccount(selectedCustomerSSN, selectedCustomerAccountID));       
        accountList.setItems(customerInformation);
        }

        try {
            loadPopup();
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
