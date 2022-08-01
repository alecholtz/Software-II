package CONTROLLERS;

import DATABASEOBJECT.CustomersDaoImpl;
import DATACLASSES.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * A class for controller the create customer menu.
 * @author Alec Holtzapfel
 */
public class CreateCustomerMenuController extends CustomerController {

    @FXML
    private TextField customerIdText;

    @FXML
    private TextField customerNameText;

    @FXML
    private TextField streetText;

    @FXML
    private TextField postalCodeText;

    @FXML
    private TextField phoneNumberText;

    @FXML
    private ComboBox<String> stateCombo;

    @FXML
    private ComboBox<String> countryCombo;

    /**
     * exits the create customer menu without update the SQL table
     * @param event selecting the cancel button
     * @throws IOException
     */
    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/FXML DOCS/CustomerTable.fxml "));
        loader.load();

        CustomerTableController ctrl = loader.getController();
        ctrl.sendUser(getUser());
        ctrl.sendInitialData();

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * creates a new customer in the customer table
     * @param event selecting the create button
     * @throws IOException
     */
    @FXML
    void onActionCreate(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/FXML DOCS/CustomerTable.fxml "));
        loader.load();

        Customer cust = new Customer(
                customerNameText.getText(),
                streetText.getText(),
                postalCodeText.getText(),
                phoneNumberText.getText(),
                stateCombo.getValue(),
                countryCombo.getValue()
        );

        CustomersDaoImpl custCtrl = new CustomersDaoImpl(getUser());
        custCtrl.createCustomer(cust);

        CustomerTableController ctrl = loader.getController();
        ctrl.sendUser(getUser());
        ctrl.sendInitialData();

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * filters the first level division when a country is selected
     * @param event selecting a country
     */
    @FXML
    void onActionFilterFLD(ActionEvent event) {
        stateCombo.setItems(getAllStates(countryCombo.getValue()));
    }

    /**
     * Sets the default values for text boxes and combos. The First Level Division combo
     * will be populated when a country is selected.
     */
    public void sendDefaultValues(){
        customerIdText.setText("AUTO_INCREMENT -- ID SET BY SYSTEM");
        countryCombo.setItems(getAllCountries());
    }

}
