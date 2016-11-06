package bank;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

/**
 * Handles the logic for the Info scene
 *
 * @author Grupp 4
 */
public class InfoController extends BaseController {

    private int typeOfOperation;

    @FXML
    private ListView accountList;

    @FXML
    private Button btnCustomer;

    @FXML
    private Button btnAccount;

    private ObservableList<String> customerInformation;

    /**
     * Prevents selection in ListView.
     *
     * @param event
     */
    public void dontPressMe(MouseEvent event) {
        accountList.addEventFilter(MouseEvent.MOUSE_PRESSED, (MouseEvent event1) -> {
            event1.consume();
        });
    }

    /**
     * Shows a confirmation popup if the user wants to leave the scene
     */
    @FXML
    @Override
    protected void handleHome() {
        typeOfOperation = 0;
        setPopupMessage("Are you sure you want to leave?");
        showPopup();
    }

    /**
     * Shows a confirmation popup if the user wants to leave the scene
     */
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

    /**
     * Initializes the scene and sets the visibility of menu buttons
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnAccount.setVisible(false);

        if (selectedCustomerAccountID == 0) {
            btnCustomer.setVisible(false);
            customerInformation = FXCollections.observableArrayList(bankLogic.removeCustomer(selectedCustomerSSN));
            accountList.setItems(customerInformation);
        } else {
            customerInformation = FXCollections.observableArrayList(bankLogic.closeAccount(selectedCustomerSSN, selectedCustomerAccountID));
            accountList.setItems(customerInformation);
        }
    }

}
