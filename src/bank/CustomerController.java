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
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CustomerController extends BaseController {

    @FXML
    Label Prn;
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
    private BankLogic banklogic;
    @FXML
    RadioButton saving;
    @FXML
    RadioButton credit;
    @FXML
    Button create;
    @FXML
    private void buttonChange(ActionEvent event) {
        long newSsn = BaseController.selectedCustomerSSN;
        banklogic.changeCustomer(changeName.getText(), newSsn);
        updateInfo();
    }
    @FXML
    private void buttonRemove(ActionEvent event) throws IOException {
        loadPopup();
        setPopupMessage("Are you sure ?");
        showPopup();
        popupYes();
        int newAccountId=BaseController.selectedCustomerAccountID;
        banklogic.closeAccount(selectedCustomerSSN, newAccountId);
        updateInfo();
        setStage(main);
    }
    @FXML
    private void buttonCreate(ActionEvent event) throws IOException {
        
        banklogic.addSavingsAccount(selectedCustomerSSN);
        updateInfo();
    }
    @FXML
    private void buttonSelect(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("Account.fxml"));
        Scene s = new Scene(root);
        Stage stg = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stg.setScene(s);
    }

    public void sendInformation(String text) {
        System.out.println(text);
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

    private void updateInfo() {
        ArrayList<String> info = (ArrayList<String>) banklogic.getCustomer(selectedCustomerSSN);
        changeName.setText(info.get(0));
        Prn.setText(info.get(1));
        info.remove(0);
        info.remove(0);
        accounts = FXCollections.observableArrayList(info);
        listOfAccounts.setItems(accounts);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        banklogic = BankLogic.getBankLogic();
        selectedCustomerSSN = 7912120101L;
        updateInfo();

        try {
            loadPopup();
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
