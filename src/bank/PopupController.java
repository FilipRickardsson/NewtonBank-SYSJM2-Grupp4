package bank;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * Handles the logic for the popup
 *
 * @author Grupp 4
 */
public class PopupController implements Initializable {

    private BaseController controller;

    @FXML
    private Label lblMessage;

    /**
     * Constructor which gets a reference to BaseController which loaded the
     * popup
     *
     * @param controller Reference to BaseController
     */
    public void setController(BaseController controller) {
        this.controller = controller;
    }

    /**
     * Calls a method in the BaseController if the Yes button is pressed
     */
    @FXML
    private void popupYes() {
        controller.popupYes();
    }

    /**
     * Calls a method in the BaseController if the No button is pressed
     */
    @FXML
    private void popupNo(ActionEvent event) {
        controller.popupNo();
    }

    /**
     * Sets the message to display
     *
     * @param msg Message to display
     */
    public void setMessage(String msg) {
        lblMessage.setText(msg);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

}
