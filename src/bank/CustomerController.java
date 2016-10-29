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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class CustomerController extends BaseController {

    private ObservableList<String> accounts;

    @FXML
    private Label lblSsn;

    @FXML
    private TextField changeName;

    @FXML
    private ListView listOfAccounts;

    @FXML
    private RadioButton saving;

    @FXML
    private RadioButton credit;

    @FXML
    private Label message;
    
    @FXML
    private Button btnAccount;

    private final ToggleGroup group = new ToggleGroup();

    @FXML
    private void buttonChange(ActionEvent event) {
        long newSsn = BaseController.selectedCustomerSSN;

        if (bankLogic.changeCustomer(changeName.getText(), newSsn)) {
            message.setText("Change success");
        } else {
            message.setText("Invalid symbols");
        }
    }

    @FXML
    private void buttonRemove(ActionEvent event) {
        setPopupMessage("Are you sure ?");
        showPopup();
    }

    @FXML
    private void buttonCreate(ActionEvent event) {
        if (saving.isSelected()) {
            bankLogic.addSavingsAccount(selectedCustomerSSN);
        } else {
            bankLogic.addCreditAccount(selectedCustomerSSN);
        }
        updateInfo();
    }

    @FXML
    private void buttonSelect(ActionEvent event) throws IOException {
        selectedCustomerAccountID = bankLogic.getCustomerAccountIdViaIndex(listOfAccounts.getSelectionModel().getSelectedIndex());
        loadScene("Account.fxml");
    }

    @Override
    protected void popupYes() {
        selectedCustomerAccountID = bankLogic.getCustomerAccountIdViaIndex(listOfAccounts.getSelectionModel().getSelectedIndex());
        loadScene("Info.fxml");
        popup.close();
    }

    private void updateInfo() {
        ArrayList<String> info = (ArrayList<String>) bankLogic.getCustomer(selectedCustomerSSN);
        changeName.setText(info.get(0));
        lblSsn.setText(info.get(1));
        info.remove(0);
        info.remove(0);
        accounts = FXCollections.observableArrayList(info);
        listOfAccounts.setItems(accounts);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnAccount.setVisible(false);
        saving.setSelected(true);
        saving.setToggleGroup(group);
        credit.setToggleGroup(group);
        
        updateInfo();

        try {
            loadPopup();
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
