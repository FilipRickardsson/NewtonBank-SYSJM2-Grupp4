package bank;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public abstract class BaseController implements Initializable {

    protected static Stage main;
    protected Stage popup;
    protected PopupController popupCtrl;
    
    public static long selectedCustomerSSN;
    public static int selectedCustomerAccountID;

    protected void setStage(Stage stage) {
        BaseController.main = stage;
    }
    
    protected void loadPopup() throws IOException {
        popup = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Popup.fxml"));
        Parent root = (Parent) loader.load();
        popupCtrl = (PopupController) loader.getController();
        popupCtrl.setController(this);
        popup.setResizable(false);
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setScene(new Scene(root));
        popup.setTitle("Newton Bank");
        popup.setOnCloseRequest((WindowEvent we) -> {
            we.consume();
        });

    }

    protected void showPopup() {
        if (popup != null) {
            popup.show();
        } else {
            System.out.println("Error loading popup");
        }
    }

    protected abstract void popupYes();

    protected void popupNo() {
        popup.close();
    }

    protected void setPopupMessage(String msg) {
        popupCtrl.setMessage(msg);
    }

    @FXML
    protected void handleHome() {
        showPopup();
    }

}
