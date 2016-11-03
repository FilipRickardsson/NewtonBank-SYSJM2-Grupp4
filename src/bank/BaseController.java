package bank;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public abstract class BaseController implements Initializable {

    /**
     * This is the superClass that haves equal information to different scenes.
     * Buttons. Titels. PopUps.
     */
    protected static Stage main;
    protected Stage popup;
    protected PopupController popupCtrl;
    protected BankLogic bankLogic;

    public static long selectedCustomerSSN;
    public static int selectedCustomerAccountID;

    public BaseController() {
        bankLogic = BankLogic.getBankLogic();
    }

    protected void setStage(Stage stage) {
        BaseController.main = stage;
    }

    /**
     * Create PopUps
     *
     * @throws IOException
     */
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

    protected final void popupNo() {
        popup.close();
    }

    protected void setPopupMessage(String msg) {
        popupCtrl.setMessage(msg);
    }

    @FXML
    protected void handleHome() {
        loadScene("Home.fxml");
    }

    @FXML
    protected void handleCustomer() {
        loadScene("Customer.fxml");
    }

    @FXML
    protected void handleAccount() {
        loadScene("Account.fxml");
    }

    @FXML
    protected void handleQuit() {
        Platform.exit();
    }

    protected void loadScene(String sceneToLoad) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(sceneToLoad));
            Scene s = new Scene(root);
            main.setScene(s);
        } catch (IOException ex) {
            Logger.getLogger(BaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void showMessage(String msg, Label lbl, boolean isError) {
        if (isError) {
            lbl.setText(msg);
            lbl.setTextFill(Color.RED);
        } else {
            lbl.setText(msg);
            lbl.setTextFill(Color.BLACK);
        }
    }

}
