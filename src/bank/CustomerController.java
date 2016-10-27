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
import javafx.scene.control.ToggleGroup;
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
    Label message;
    final ToggleGroup group=new ToggleGroup();
    @FXML
    private void buttonChange(ActionEvent event) {
        long newSsn = BaseController.selectedCustomerSSN;
        banklogic.changeCustomer(changeName.getText(), newSsn);
    }

    @FXML
    private void buttonRemove(ActionEvent event){
        setPopupMessage("Are you sure ?");
        showPopup();
    }

    @FXML
    private void buttonCreate(ActionEvent event){
        if(saving.isSelected()){
            banklogic.addSavingsAccount(selectedCustomerSSN);
        }
        else{
            banklogic.addCreditAccount(selectedCustomerSSN);
        }
        updateInfo();
    }

    @FXML
    private void buttonSelect(ActionEvent event) throws IOException {
        selectedCustomerAccountID = banklogic.getCustomerAccountIdViaIndex(listOfAccounts.getSelectionModel().getSelectedIndex());
        loadScene("Account.fxml");
    }
    @Override
    protected void popupYes() {
        selectedCustomerAccountID = banklogic.getCustomerAccountIdViaIndex(listOfAccounts.getSelectionModel().getSelectedIndex());
        System.out.println("Selected index: " + selectedCustomerAccountID);
        loadScene("Info.fxml");
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
        saving.setSelected(true);
        saving.setToggleGroup(group);
        credit.setToggleGroup(group);
        banklogic = BankLogic.getBankLogic();
        updateInfo();

        try {
            loadPopup();
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
