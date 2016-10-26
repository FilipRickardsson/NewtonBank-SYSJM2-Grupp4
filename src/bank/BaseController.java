package bank;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public abstract class BaseController implements Initializable {

    protected Stage popup;

    @FXML
    Label lblMessage;

    public void loadPopup() throws IOException {
        popup = new Stage();
        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("Popup.fxml"));
        myLoader.setController(this);
        Parent popupRoot = (Parent) myLoader.load();
        popup.setScene(new Scene(popupRoot));
        popup.setTitle("Message");
        popup.setResizable(false);
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setOnCloseRequest((WindowEvent we) -> {
            
        });
    }

    protected void showPopup() {
        if (popup != null) {
            popup.show();
        } else {
            System.out.println("Error");
        }
    }

    @FXML
    protected abstract void popupYes();

    @FXML
    protected abstract void popupNo();

    protected void setPopupMessage(String msg) {
        lblMessage.setText(msg);
    }

    protected abstract void init();
    
    @FXML
    protected void handleHome() {
        showPopup();
    } 

}
