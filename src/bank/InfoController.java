package bank;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

public class InfoController extends BaseController {

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
        setPopupMessage("Are you sure?");
        showPopup();
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnCustomer.setVisible(false);
        btnAccount.setVisible(false);

        //TODO fix
        if (selectedCustomerAccountID == 0) {

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
