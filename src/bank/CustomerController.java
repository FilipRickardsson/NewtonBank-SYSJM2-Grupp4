package bank;

import java.awt.Button;
import java.awt.TextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class CustomerController extends BaseController {

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

    @FXML
    private void buttonChange(ActionEvent event) {

    }

    @FXML
    private void buttonRemove(ActionEvent event) {

    }

    @FXML
    private void buttonSelect(ActionEvent event) {

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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        accounts = FXCollections.observableArrayList();
        listOfAccounts.setItems(accounts);

        try {
            loadPopup();
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
