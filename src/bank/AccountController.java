package bank;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class AccountController implements Initializable {
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("Account Controller");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }    
    
}
