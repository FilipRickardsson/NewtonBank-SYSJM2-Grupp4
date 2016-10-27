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
    private String testLong = "9202254545";
    private String testName = "paul";
    private String testBalance = "10000";
    @FXML
    private TextField name;
    @FXML
    private TextField ssn;
    @FXML
    private Label account;
    @FXML
    private TextField balance;
    @FXML
    private ListView accountList;

    private ObservableList<String> accounts;
    
    

    @FXML
    private void nameSet() {
        // sett name and ssn textfields based on wich customer/account is closing
    }
    // Få info från tidigare scen
    //Fix balance set
    //Hur får vi ut vilka accounts som tagits bort?
    //fixa popupYes ladda home.fxml

    @FXML
    private String balanceSet() {
        String saldo = "Fix";
       // ta hand info och få ut saldo
       
       return saldo;
    }

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
        accounts = FXCollections.observableArrayList(bankLogic.getAccount(9202254545L, 1006));       
        accountList.setItems(accounts);
        ssn.setText(testLong);
        name.setText(testName);
        balance.setText(testBalance);
       

        try {
            loadPopup();
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
