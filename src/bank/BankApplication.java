package bank;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class BankApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Home.fxml"));
        Parent root = (Parent) loader.load();
        BaseController controller = (BaseController) loader.getController();
        stage.setTitle("Newton Bank");
        stage.setWidth(1100);
        stage.setHeight(825);
        controller.setStage(stage);
        
        Image icon = new Image(getClass().getResourceAsStream("nb.png"));
        stage.getIcons().add(icon);

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
