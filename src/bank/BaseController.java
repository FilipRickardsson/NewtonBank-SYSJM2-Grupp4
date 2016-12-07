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

/**
 * Superclass to the different scene controllers which handles menu-buttons,
 * scene switching, popups and error messages
 *
 * @author Grupp 4
 */
public abstract class BaseController implements Initializable {

    protected static Stage main;
    protected Stage popup;
    protected PopupController popupCtrl;
    protected BankLogic bankLogic;

    public static long selectedCustomerSSN;
    public static int selectedCustomerAccountID;

    /**
     * Constructor which gets a reference to the BankLogic object
     */
    public BaseController() {
        bankLogic = BankLogic.getBankLogic();
        try {
            loadPopup();
        } catch (IOException ex) {
            System.out.println("Could not load Popup");
        }
    }

    /**
     * Sets the main stage of the application which makes it available to the
     * the different controllers
     *
     * @param stage
     */
    protected void setStage(Stage stage) {
        BaseController.main = stage;
    }

    /**
     * Loads the popup
     *
     * @throws IOException if the popup could not be loaded
     */
    protected final void loadPopup() throws IOException {
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

    /**
     * Shows the popup
     */
    protected void showPopup() {
        if (popup != null) {
            popup.show();
        }
    }

    /**
     * Defines what will happen when the yes button is pressed in the popup
     */
    protected abstract void popupYes();

    /**
     * Closes the popup if the no button is pressed
     */
    protected final void popupNo() {
        popup.close();
    }

    /**
     * Sets the message to display in the popup
     *
     * @param msg message to display
     */
    protected void setPopupMessage(String msg) {
        popupCtrl.setMessage(msg);
    }

    /**
     * Switches to the Home scene when pressed in the menu
     */
    @FXML
    protected void handleHome() {
        loadScene("Home.fxml");
    }

    /**
     * Switches to the Customer scene when pressed in the menu
     */
    @FXML
    protected void handleCustomer() {
        loadScene("Customer.fxml");
    }

    /**
     * Switches to the Account scene when pressed in the menu
     */
    @FXML
    protected void handleAccount() {
        loadScene("Account.fxml");
    }

    /**
     * Terminates the application when the Quit button is pressed in the menu
     */
    @FXML
    protected void handleQuit() {
        Platform.exit();
    }

    /**
     * Loads a new scene
     *
     * @param sceneToLoad Name of the scene to load
     */
    protected void loadScene(String sceneToLoad) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(sceneToLoad));
            Scene s = new Scene(root);
            main.setScene(s);
        } catch (IOException ex) {
            Logger.getLogger(BaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Shows a message in the GUI. If its a error message, the message will be
     * shown in red
     *
     * @param msg Message to display
     * @param lbl Label to display the message
     * @param isError true if it is an error message
     */
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
