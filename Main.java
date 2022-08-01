package MAIN;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root;
        try {
            ResourceBundle rb = ResourceBundle.getBundle("MAIN/Nat", Locale.getDefault());
            root = FXMLLoader.load(getClass().getResource("../FXML DOCS/Login.fxml"), rb);
        }
        catch (MissingResourceException err) {
            root = FXMLLoader.load(getClass().getResource("../FXML DOCS/Login.fxml"));
        }
        primaryStage.setScene(new Scene(root, 450, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
