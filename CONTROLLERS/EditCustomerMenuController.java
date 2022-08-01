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
 * A class for controlling the edit customer menu.
 * @author Alec Holtzapfel
 */
public class EditCustomerMenuController extends CustomerController{
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
     * switches the stage to the customer table view without updating the customer
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
     * updates the customer data in sql and switches to the customer table view
     * @param event selecting the update button
     * @throws IOException
     */
    @FXML
    void onActionUpdate(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/FXML DOCS/CustomerTable.fxml "));
        loader.load();

        Customer cust = new Customer(
                Integer.parseInt(customerIdText.getText()),
                customerNameText.getText(),
                streetText.getText(),
                postalCodeText.getText(),
                phoneNumberText.getText(),
                stateCombo.getValue(),
                countryCombo.getValue()
        );

        CustomersDaoImpl custCtrl = new CustomersDaoImpl(getUser());
        custCtrl.updateCustomer(cust);

        CustomerTableController ctrl = loader.getController();
        ctrl.sendUser(getUser());
        ctrl.sendInitialData();

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * filters the first level divisions when a country is selected
     * @param event selecting a country
     */
    @FXML
    void onActionUpdateFLD(ActionEvent event) {
        stateCombo.setItems(getAllStates(countryCombo.getValue()));
    }

    /**
     * populates the initial text and combo data
     * @param selectedCustomer
     */
    public void sendCustomer(Customer selectedCustomer){
        customerIdText.setText(String.valueOf(selectedCustomer.getCustomerId()));
        phoneNumberText.setText(selectedCustomer.getPhone());
        customerNameText.setText(selectedCustomer.getCustomerName());
        streetText.setText(selectedCustomer.getAddress());
        postalCodeText.setText(selectedCustomer.getPostalCode());
        phoneNumberText.setText(selectedCustomer.getPhone());
        stateCombo.setItems(getAllStates(selectedCustomer.getCountry()));
        stateCombo.setValue(selectedCustomer.getDivision());
        countryCombo.setValue(selectedCustomer.getCountry());
        countryCombo.setItems(getAllCountries());
    }
}
