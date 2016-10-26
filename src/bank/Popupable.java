package bank;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public interface Popupable {

    public static Stage loadPopup(Object obj) throws IOException {
        Stage popup = new Stage();
        FXMLLoader loader = new FXMLLoader(obj.getClass().getResource("Popup.fxml"));
        loader.setController(obj);
        Parent popupRoot = (Parent) loader.load();
        popup.setScene(new Scene(popupRoot));
        popup.setTitle("Confirmation");
        popup.setResizable(false);
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setOnCloseRequest((WindowEvent we) -> {
        });
        
        return popup;
    }
    
    public void popupYes();
    
    public void popupNo();

    public void setPopupMessage();
    
}
