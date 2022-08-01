package CONTROLLERS;

import DATACLASSES.LogIn;
import javafx.scene.Parent;

import javafx.stage.Stage;


/**
 * An abstract class containing the basic structure of a controller.
 * @author Alec Holtzapfel
 */
public abstract class Controller {
    public Stage stage;
    public Parent scene;
    private LogIn user;

    /**
     * Gets the user
     * @return the users LogIn
     */
    public LogIn getUser() {
        return user;
    }

    /**
     * sets the user
     * @param user the current valid user
     */
    public void sendUser(LogIn user){
        this.user = user;
    }
}
