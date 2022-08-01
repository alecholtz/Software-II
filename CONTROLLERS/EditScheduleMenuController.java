package CONTROLLERS;

import DATABASEOBJECT.ApptmtDaoImpl;
import DATACLASSES.Appointment;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.ZoneId;
import java.util.Optional;

/**
 * A class for controlling the edit schedule menu.
 * @author Alec Holtzapfel
 */
public class EditScheduleMenuController extends ScheduleController{
    @FXML
    private TextField appointmentIdText;

    @FXML
    private TextField titleText;

    @FXML
    private TextField descriptionText;

    @FXML
    private TextField typeText;

    @FXML
    private TextField startDateText;

    @FXML
    private TextField locationText;

    @FXML
    private ComboBox<String> contactCombo;

    @FXML
    private ComboBox<Integer> customerIdCombo;

    @FXML
    private TextField startTimeText;

    @FXML
    private TextField endTimeText;

    @FXML
    private TextField endDateText;

    @FXML
    private ComboBox<Integer> userCombo;

    /**
     * Switches the stage to the schedule table view without updating the appointment.
     * @param event selecting the cancel button
     * @throws IOException
     */
    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
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
     * updates an appointment and switches to the schedule table
     * @param event selecting the update button
     * @throws IOException
     */
    @FXML
    void onActionUpdate(ActionEvent event) throws IOException {
        Appointment appt = new Appointment(
                Integer.parseInt(appointmentIdText.getText()),
                customerIdCombo.getValue(),
                userCombo.getSelectionModel().getSelectedItem(),
                contactCombo.getValue(),
                titleText.getText(),
                typeText.getText(),
                descriptionText.getText(),
                locationText.getText(),
                startDateText.getText(),
                startTimeText.getText(),
                endDateText.getText(),
                endTimeText.getText(),
                String.valueOf(ZoneId.systemDefault()),
                "Etc/UTC"
        );
        ApptmtDaoImpl appmtCtrl = new ApptmtDaoImpl(getUser());
        if (appt.isValidAppmnt()) {
            if (appt.validateWithinCompanyHours()) {
                ObservableList<Appointment> appmts = appmtCtrl.checkForConflictingAppmt(appt);
                if (appmts.isEmpty()) {
                    appmtCtrl.updateAppointment(appt);

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
                } else {
                    String info = new String();
                    for (Appointment a : appmts) {
                        info = info + "Appointment " + String.valueOf(a.getAppointmentId()) + " with Customer " + appt.getCustomerId() +
                                " from " + a.getStartDateTime() + " to " + a.getEndDateTime() + " confilicts with this appointment\n";
                    }
                    Alert alert = new Alert(Alert.AlertType.WARNING, info);
                    Optional<ButtonType> result = alert.showAndWait();
                }
            }
        }
    }

    /**
     * populates the initial text and combo data
     * @param selectedAppointment the appointment selected in the Appointment table
     */
    public void sendAppointment(Appointment selectedAppointment){
        appointmentIdText.setText(String.valueOf(selectedAppointment.getAppointmentId()));
        titleText.setText(selectedAppointment.getTitle());
        descriptionText.setText(selectedAppointment.getDescription());
        typeText.setText(selectedAppointment.getType());
        startDateText.setText(selectedAppointment.getStartDate());
        startTimeText.setText(selectedAppointment.getStartTime());
        locationText.setText(selectedAppointment.getLocation());
        endDateText.setText(selectedAppointment.getEndDate());
        endTimeText.setText(selectedAppointment.getEndTime());

        userCombo.setItems(getAllUsers());
        userCombo.setValue(selectedAppointment.getUserId());
        contactCombo.setItems(getAllContacts());
        contactCombo.setValue(selectedAppointment.getContactName());
        customerIdCombo.setItems(getAllCustomers());
        customerIdCombo.setValue(selectedAppointment.getCustomerId());
    }
}
