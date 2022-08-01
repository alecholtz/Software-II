package CONTROLLERS;

import DATACLASSES.LogIn;
import Interfaces.TranslateText;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.TimeZone;

/**
 * A class for controlling the login menu.
 * @author Alec Holtzapfel
 */
public final class LoginController extends Controller implements Initializable {
    private LogIn user;
    private int LoginAttempts = 0;

    @FXML
    private TitledPane loginTitlePane;

    @FXML
    private TextField UsernameTextBox;

    @FXML
    private Text usernameText;

    @FXML
    private Text passwordText;

    @FXML
    private PasswordField PasswordTextBox;

    @FXML
    private Button loginButton;

    @FXML
    private Button exitButton;

    @FXML
    private Text IncorrectEntryText;

    @FXML
    private Text locationText;

    /**
     * gets the number of login attempts
     * @return int
     */
    public int getLoginAttempts() {
        return LoginAttempts;
    }

    /**
     * sets the number of login attempts
     * @param loginAttempts the number of login attempts as an integer
     */
    private void setLoginAttempts(int loginAttempts) {
        LoginAttempts = loginAttempts;
    }

    /**
     * gets the user
     * @return LogIn
     */
    public LogIn getUser() {
        return user;
    }

    /**
     * sets the user
     * @param username string validated username of the user
     * @param password string validated password of the user
     */
    private void setUser(String username, String password) {
        this.user = new LogIn(username, password);
    }

    /**
     * appends login attempt information to the log in .txt file
     * @param attemptedUserName string attempted username
     * @param attemptSuccessful boolean valid credentials or not
     * @throws IOException
     */
    private void appendLogInActivity(String attemptedUserName, boolean attemptSuccessful) throws IOException {
        FileWriter txtWriter = new FileWriter("src/LoginAttempts/login_activity.txt", true);
        PrintWriter print_line = new PrintWriter(txtWriter);
        String output = null;

        if (attemptSuccessful){
            output = attemptedUserName + "," + String.valueOf(LocalDateTime.now()) + ",successful";
        }
        else {
            output = attemptedUserName + "," + String.valueOf(LocalDateTime.now()) + ",unsuccessful";
        }

        print_line.printf("%s" + "%n", output);
        print_line.close();
    }

    /**
     * exits the login menu and closes the application
     * @param event selecting the exit button
     */
    @FXML
    void OnActionExit(ActionEvent event) {
       System.exit(0);
    }

    /**
     * checks the users login and attempts; automatically ends the instance if the incorrect
     * input is give five times
     * @param event selecting the login button
     * @throws IOException
     */
    @FXML
    void OnActionLogin(ActionEvent event) throws IOException {
        if (UsernameTextBox.getText().isEmpty() || PasswordTextBox.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a username and password.\n");
            Optional<ButtonType> result =  alert.showAndWait();
        }
        else {
            setUser(UsernameTextBox.getText(), PasswordTextBox.getText());

            if (user.getValidLogin()) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/FXML DOCS/TableView.fxml "));
                loader.load();

                TableViewController ctrl = loader.getController();
                ctrl.sendUser(user);
                ctrl.sendInitialData();

                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                Parent scene = loader.getRoot();
                stage.setScene(new Scene(scene));
                stage.show();

                ctrl.upcomingAppointmentAlert();

                appendLogInActivity(UsernameTextBox.getText(), true);
            } else {
                appendLogInActivity(UsernameTextBox.getText(), false);
                setLoginAttempts(getLoginAttempts() + 1);

                if (getLoginAttempts() > 0) {
                    IncorrectEntryText.setVisible(true);
                }
                if (getLoginAttempts() > 4) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "An incorrect login has been provided too many times." +
                            "\nThis session will now end; please try again later.\n");
                    Optional<ButtonType> result = alert.showAndWait();

                    System.exit(0);
                }
            }
        }

    }

    /**
     * initializes the menu; automatically translates between french and english depending on
     * user computer settings
     * The lambda function is used to translate text from english to the users default language.
     * @param url
     * @param rb the resource bundle for the users default language setting
     */
    @Override
    public void initialize(URL url, ResourceBundle rb){
        locationText.setText(ZoneId.systemDefault().toString());

        if(Locale.getDefault().getLanguage() != "en") {
            TranslateText translate = (resource, input) -> {
                String ouput = new String();
                input = input.replaceAll("[^a-zA-Z ]", "").toLowerCase();
                for (String val : input.split(" ")) {
                    ouput = ouput + " " + resource.getString(val);
                }
                return ouput;
            };

            loginTitlePane.setText(translate.translateText(rb, loginTitlePane.getText()));
            usernameText.setText(translate.translateText(rb, usernameText.getText()));
            passwordText.setText(translate.translateText(rb, passwordText.getText()));
            exitButton.setText(translate.translateText(rb, exitButton.getText()));
            loginButton.setText(translate.translateText(rb, loginButton.getText()));
            UsernameTextBox.setPromptText(translate.translateText(rb, UsernameTextBox.getPromptText()));
            PasswordTextBox.setPromptText(translate.translateText(rb, PasswordTextBox.getPromptText()));

            IncorrectEntryText.setText(translate.translateText(rb, IncorrectEntryText.getText()));
        }
    }
}
