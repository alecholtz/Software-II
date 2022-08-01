package CONTROLLERS;

import DATABASEOBJECT.ApptmtDaoImpl;
import DATACLASSES.Appointment;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.ZoneId;
import java.util.Optional;

/**
 *A class for controlling the adjust time menu.
 *@author Alec Holtzapfel
 */
public class AdjustAppmntTimeController extends ScheduleController{
    private int appointmentId;
    private int customerId;

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    @FXML
    private TextField startDateText;

    @FXML
    private TextField endDateText;

    @FXML
    private TextField startTimeText;

    @FXML
    private TextField endTimeText;
    /*
    exits the stage without updating the appointment time
    @param event selecting the cancel button
    @throws IOException
     */
    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/FXML DOCS/TableView.fxml "));
        loader.load();

        TableViewController ctrl = loader.getController();
        ctrl.sendUser(getUser());
        ctrl.sendInitialData();

        stage = (Stage) ((javafx.scene.control.Button) event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * updates the start and end of an appointment
     * @param event selecting the update button
     * @throws IOException
     */
    @FXML
    void onActionUpdate(ActionEvent event) throws IOException {
        Appointment appt = new Appointment(
                getAppointmentId(),
                startDateText.getText(),
                startTimeText.getText(),
                endDateText.getText(),
                endTimeText.getText(),
                getCustomerId(),
                String.valueOf(ZoneId.systemDefault()),
                "Etc/UTC"
        );
        ApptmtDaoImpl appmtCtrl = new ApptmtDaoImpl(getUser());

        if (appt.validateWithinCompanyHours()) {
            ObservableList<Appointment> appmts = appmtCtrl.checkForConflictingAppmt(appt);
            if (appmts == null) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/FXML DOCS/TableView.fxml "));
                loader.load();


                appmtCtrl.updateAppointmentTime(
                        appt.getAppointmentId(),
                        appt.getStartDateTime(),
                        appt.getEndDateTime()
                );

                TableViewController ctrl = loader.getController();
                ctrl.sendUser(getUser());
                ctrl.sendInitialData();

                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                Parent scene = loader.getRoot();
                stage.setScene(new Scene(scene));
                stage.show();
            }
            else{
                String info = new String();
                for (Appointment a: appmts){
                    info = info + "Appointent " + String.valueOf(a.getAppointmentId()) + " with Customer " + appt.getCustomerId() +
                            " from " + a.getStartDateTime() + " to " + a.getEndDateTime() +  " confilicts with this appointment\n";
                }//make this a lambda function
                Alert alert = new Alert(Alert.AlertType.WARNING, info);
                Optional<ButtonType> result = alert.showAndWait();
            }
        }
    }

    public void sendAppmntTimes(Appointment selectedAppointment){
        setAppointmentId(selectedAppointment.getAppointmentId());

        startDateText.setText(selectedAppointment.getStartDateTime().split(" ")[0]);
        startTimeText.setText(selectedAppointment.getStartDateTime().split(" ")[1]);
        endDateText.setText(selectedAppointment.getEndDateTime().split(" ")[0]);
        endTimeText.setText(selectedAppointment.getEndDateTime().split(" ")[1]);

        setCustomerId(selectedAppointment.getCustomerId());
    }
}
