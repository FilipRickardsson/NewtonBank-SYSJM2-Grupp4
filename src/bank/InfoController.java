package bank;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

public class InfoController extends BaseController {

    private int typeOfOperation;

    @FXML
    private ListView accountList;

    @FXML
    private Button btnCustomer;

    @FXML
    private Button btnAccount;

    private ObservableList<String> customerInformation;

    public void dontPressMe(MouseEvent event) {
        accountList.addEventFilter(MouseEvent.MOUSE_PRESSED, (MouseEvent event1) -> {
            System.out.println(">> Mouse Clicked");
            event1.consume();
        });
    }

    @FXML
    @Override
    protected void handleHome() {
        typeOfOperation = 0;
        setPopupMessage("Are you sure you want to leave?");
        showPopup();
    }

    @FXML
    @Override
    protected void handleCustomer() {
        typeOfOperation = 1;
        setPopupMessage("Are you sure you want to leave?");
        showPopup();
    }

    @Override
    protected void popupYes() {
        if (typeOfOperation == 0) {
            loadScene("Home.fxml");
        } else {
            loadScene("Customer.fxml");
        }
        popup.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnAccount.setVisible(false);
        
        //TODO fix
        if (selectedCustomerAccountID == 0) {
            btnCustomer.setVisible(false);
            customerInformation = FXCollections.observableArrayList(bankLogic.removeCustomer(selectedCustomerSSN));
            accountList.setItems(customerInformation);
        } else {
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
