package CONTROLLERS;

import DATABASEOBJECT.CustomersDaoImpl;
import DATACLASSES.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * A class for controlling the customer menu.
 * @author Alec Holtzapfel
 */
public class CustomerTableController extends Controller implements Initializable {
    @FXML
    private TableView<Customer> customerTableView;

    @FXML
    private TableColumn<Customer, Integer> idColumn;

    @FXML
    private TableColumn<Customer, String> nameColumn;

    @FXML
    private TableColumn<Customer, String> addressColumn;

    @FXML
    private TableColumn<Customer, String> stateColumn;

    @FXML
    private TableColumn<Customer, String> countryColumn;

    @FXML
    private TableColumn<Customer, String> postalCodeColumn;

    @FXML
    private TableColumn<Customer, String> phoneNumberColumn;

    /**
     * Switches the primary stage to the create customer menu.
     * @param event selecting the create customer button
     * @throws IOException
     */
    @FXML
    void onActionCreate(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/FXML DOCS/CreateNewCustomerMenu.fxml "));
        loader.load();

        CreateCustomerMenuController ctrl = loader.getController();
        ctrl.sendUser(getUser());
        ctrl.sendDefaultValues();

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * deletes the selected customer from the table
     * @param event selecting the delete button with a customer selected
     */
    @FXML
    void onActionDelete(ActionEvent event) {
        int selectedId = customerTableView.getSelectionModel().getSelectedIndex();

        if (selectedId == -1){
            Alert alert = new Alert(Alert.AlertType.ERROR, "NO CUSTOMER SELECTED;\nPLEASE SELECT AN CUSTOMER");
            Optional<ButtonType> result = alert.showAndWait();

        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you would like to delete this customer?\n" +
                    "All related appointments will be deleted.");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK){
                CustomersDaoImpl custCtrl = new CustomersDaoImpl(getUser());
                int id = idColumn.getCellData(selectedId);
                custCtrl.deleteCustomer(id);

                customerTableView.setItems(custCtrl.getAllCustomers());

                idColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
                nameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
                addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
                postalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
                phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
                stateColumn.setCellValueFactory(new PropertyValueFactory<>("division"));
                countryColumn.setCellValueFactory(new PropertyValueFactory<>("Country"));


                Alert informatrionAlert = new Alert(Alert.AlertType.INFORMATION, "Customer "+String.valueOf(id)+" successfully deleted");
                Optional<ButtonType> inforesult = informatrionAlert.showAndWait();
            }
        }
    }

    /**
     * switches the primary stage to the edit customer menu
     * @param event selecting the edit customer button
     * @throws IOException
     */
    @FXML
    void onActionEdit(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/FXML DOCS/EditCustomerMenu.fxml "));
        loader.load();

        EditCustomerMenuController ctrl = loader.getController();
        if (customerTableView.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR, "No customer selected;\n" +
                    "Please select an customer from the table");
            Optional<ButtonType> result = alert.showAndWait();
        }
        else {
            ctrl.sendUser(getUser());
            ctrl.sendCustomer(customerTableView.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
     * switches the primary stage to the main table view
     * @param event selecting the exit button
     * @throws IOException
     */
    @FXML
    void onActionExit(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/FXML DOCS/TableView.fxml "));
        loader.load();

        TableViewController ctrl = loader.getController();
        ctrl.sendUser(getUser());
        ctrl.sendInitialData();

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * sends the initial table data
     */
    public void sendInitialData(){
        CustomersDaoImpl ctrl = new CustomersDaoImpl(getUser());

        customerTableView.setItems(ctrl.getAllCustomers());

        idColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        stateColumn.setCellValueFactory(new PropertyValueFactory<>("division"));
        countryColumn.setCellValueFactory(new PropertyValueFactory<>("Country"));
    }

    /**
     * initializes the table
     * @param url
     * @param rb the resource bundle for the users default language setting
     */
    @Override
    public void initialize(URL url, ResourceBundle rb){
        customerTableView.setItems(null);
    }
}
