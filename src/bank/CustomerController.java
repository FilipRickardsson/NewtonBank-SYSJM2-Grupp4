package bank;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class CustomerController implements Initializable {
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("Customer Controller");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }    
    
}
