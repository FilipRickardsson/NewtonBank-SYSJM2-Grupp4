package bank;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BankApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("Home.fxml"));
        Parent root = (Parent) myLoader.load();
        BaseController controller = myLoader.getController();
        controller.init();
        stage.setScene(new Scene(root));
        stage.setTitle("Newton Bank");
        stage.show();

//        Parent root = FXMLLoader.load(getClass().getResource("Home.fxml"));
//
//        Scene scene = new Scene(root);
//
//        stage.setScene(scene);
//        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
