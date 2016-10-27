package bank;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class PopupController implements Initializable {

    private BaseController controller;

    @FXML
    private Label lblMessage;

    public void setController(BaseController controller) {
        this.controller = controller;
    }

    @FXML
    private void popupYes() {
        controller.popupYes();
    }

    @FXML
    private void popupNo(ActionEvent event) {
        controller.popupNo();
    }

    public void setMessage(String msg) {
        lblMessage.setText(msg);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}
