package CONTROLLERS;

import DATABASEOBJECT.CustomerAppointmentCountsDaoImpl;
import DATABASEOBJECT.UserReportDaoImpl;
import DATACLASSES.Appointment;
import DATABASEOBJECT.ApptmtDaoImpl;
import DATACLASSES.CustomerAppointmentCounts;
import DATACLASSES.UserReport;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;
import java.time.LocalDate;

/**
 * A class for controlling the main menu.
 * @author Alec Holtzapfel
 */
public class TableViewController extends ScheduleController implements Initializable{
    @FXML
    private TableView<Appointment> scheduleTableView;

    @FXML
    private TableColumn<Appointment, Integer> appointmentId;

    @FXML
    private TableColumn<Appointment, String> appointmentTitle;

    @FXML
    private TableColumn<Appointment, String> appointmentDescription;

    @FXML
    private TableColumn<Appointment, String> location;

    @FXML
    private TableColumn<Appointment, String> contact;

    @FXML
    private TableColumn<Appointment, String> type;

    @FXML
    private TableColumn<Appointment, Date> startDate;

    @FXML
    private TableColumn<Appointment, Date> endDate;

    @FXML
    private TableColumn<Appointment, Integer> customerId;

    @FXML
    private TableColumn<Appointment, Integer> userId;

    @FXML
    private RadioButton monthToggle;

    @FXML
    private RadioButton weekToggle;

    @FXML
    private TextField weekField;

    @FXML
    private TextField monthField;

    @FXML
    private TableView<CustomerAppointmentCounts> customerPivotTable;

    @FXML
    private TableColumn<CustomerAppointmentCounts, String> appointmentTypePivot;

    @FXML
    private TableColumn<CustomerAppointmentCounts, String> month;

    @FXML
    private TableColumn<CustomerAppointmentCounts, String> count;

    @FXML
    private ComboBox<String> contactCombo;

    @FXML
    private TableView<Appointment> contactAppointmentTable;

    @FXML
    private TableColumn<Appointment, Integer> contactAppmntId;

    @FXML
    private TableColumn<Appointment, String> contactAppmntTitle;

    @FXML
    private TableColumn<Appointment, String> contactAppmntType;

    @FXML
    private TableColumn<Appointment, String> contactAppmntDesc;

    @FXML
    private TableColumn<Appointment, String> contactAppmntStart;

    @FXML
    private TableColumn<Appointment, String> contactAppmntEnd;

    @FXML
    private TableColumn<Appointment, Integer> contactCustId;


    @FXML
    private RadioButton userReportToggle;

    @FXML
    private ToggleGroup reportVersion;

    @FXML
    private RadioButton typeReportToggle;

    @FXML
    private TableView<UserReport> userReportTable;

    @FXML
    private TableColumn<UserReport, String> userNameColumn;

    @FXML
    private TableColumn<UserReport, String> appointmentCount;

    /**
     * Changes the visible report when radio buttons are toggled.
     * @param event toggling the radio buttons
     */
    @FXML
    void onActionSwapReports(ActionEvent event) {
        if (userReportToggle.isSelected()){
            userReportTable.setVisible(true);
            customerPivotTable.setVisible(false);
        }
        else if (typeReportToggle.isSelected()){
            userReportTable.setVisible(false);
            customerPivotTable.setVisible(true);
        }
    }


    /**
     * adds information to a log file when a records is deleted from the main table
     * @param appmnt the appointment deleted by the user
     * @throws IOException
     */
    private void appendDeletionActivity(Appointment appmnt) throws IOException {
        FileWriter txtWriter = new FileWriter("src/DeletionLog/deletion_activity_report.txt", true);
        PrintWriter print_line = new PrintWriter(txtWriter);

        String output = "Appointment "+ appmnt.getAppointmentId() + "," +
                appmnt.getCustomerId() + "," +
                appmnt.getStartDateTime() + "," +
                appmnt.getEndDateTime() + "," +
                getUser().getUserName() + "," +
                String.valueOf(LocalDateTime.now());

        print_line.printf("%s" + "%n", output);
        print_line.close();
    }

    /**
     * filters the contact appointment table when a contact is selected
     * @param event selecting a contact
     */
    @FXML
    void filterContactAppmntTable(ActionEvent event) {
        ApptmtDaoImpl ctrl = new ApptmtDaoImpl(getUser());
        contactAppointmentTable.setItems(ctrl.getAppointmentsbyContactName(contactCombo.getValue()));

        contactAppmntId.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        contactAppmntTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        contactAppmntType.setCellValueFactory(new PropertyValueFactory<>("type"));
        contactAppmntDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        contactAppmntStart.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));
        contactAppmntEnd.setCellValueFactory(new PropertyValueFactory<>("endDateTime"));
        contactCustId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
    }

    /**
     * hides the filter options based on the selected toggle button
     * @param event selecting a different toggle
     */
    @FXML
    void onActionHide(ActionEvent event) {
        ApptmtDaoImpl ctrl = new ApptmtDaoImpl(getUser());

        if (monthToggle.isSelected()){
            weekField.setVisible(false);
            monthField.setVisible(true);

            String currentMonth = String.valueOf(LocalDate.now().getMonth());
            scheduleTableView.setItems(ctrl.getAppointmentsbyMonth(currentMonth));
        }
        else if (weekToggle.isSelected()){
            weekField.setVisible(true);
            monthField.setVisible(false);

            scheduleTableView.setItems(ctrl.getAppointmentsbyWeek(null));
        }

        appointmentId.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        appointmentTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        appointmentDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        location.setCellValueFactory(new PropertyValueFactory<>("location"));
        contact.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        startDate.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));

        endDate.setCellValueFactory(new PropertyValueFactory<>("endDateTime"));
        customerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        userId.setCellValueFactory(new PropertyValueFactory<>("userId"));
    }

    /**
     * submits the filter and updates the tables
     * @param event selecting the submit button
     */
    @FXML
    void onActionSubmitFilter(ActionEvent event) {
        ApptmtDaoImpl appmtCtrl = new ApptmtDaoImpl(getUser());
        if (monthToggle.isSelected()){
            scheduleTableView.setItems(appmtCtrl.getAppointmentsbyMonth(monthField.getText()));
        }
        else{
            scheduleTableView.setItems(appmtCtrl.getAppointmentsbyWeek(weekField.getText()));
        }
        appointmentId.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        appointmentTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        appointmentDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        location.setCellValueFactory(new PropertyValueFactory<>("location"));
        contact.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        startDate.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));
        endDate.setCellValueFactory(new PropertyValueFactory<>("endDateTime"));
        customerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        userId.setCellValueFactory(new PropertyValueFactory<>("userId"));
    }

    /**
     * updates the table to show all appointments
     * @param event selecting the show all appointments button
     */
    @FXML
    void onActionShowAllAppointments(ActionEvent event) {
        ApptmtDaoImpl ctrl = new ApptmtDaoImpl(getUser());
        scheduleTableView.setItems(ctrl.getAllAppointments());

        appointmentId.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        appointmentTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        appointmentDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        location.setCellValueFactory(new PropertyValueFactory<>("location"));
        contact.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        startDate.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));

        endDate.setCellValueFactory(new PropertyValueFactory<>("endDateTime"));
        customerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        userId.setCellValueFactory(new PropertyValueFactory<>("userId"));
    }

    /**
     * opens the edit schedule menu
     * @param event selecting the edit button
     * @throws IOException
     */
    @FXML
    void onActionEdit(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/FXML DOCS/EditScheduleMenu.fxml "));
        loader.load();

        EditScheduleMenuController ctrl = loader.getController();
        if (scheduleTableView.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR, "No appointment selected;\n" +
                    "Please select a appointment from the table");
            Optional<ButtonType> result = alert.showAndWait();
        }
        else {
            ctrl.sendUser(getUser());
            ctrl.sendAppointment(scheduleTableView.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
     * opens the adjust appointment time menu
     * @param event selecting the adjust appointment time button
     * @throws IOException
     */
    @FXML
    void onActionEditTime(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/FXML DOCS/AdjustAppmntMenu.fxml "));
        loader.load();

        AdjustAppmntTimeController ctrl = loader.getController();
        if (scheduleTableView.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR, "No appointment selected;\n" +
                    "Please select a appointment from the table");
            Optional<ButtonType> result = alert.showAndWait();
        }
        else {
            ctrl.sendUser(getUser());
            ctrl.sendAppmntTimes(scheduleTableView.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
     * deletes an appointment from the schedule table
     * @param event selecting the delete button
     * @throws IOException
     */
    @FXML
    void onActionDelete(ActionEvent event) throws IOException {
        int selectedId = scheduleTableView.getSelectionModel().getSelectedIndex();

        if (selectedId == -1){
            Alert alert = new Alert(Alert.AlertType.ERROR, "NO APPOINTMENT SELECTED;\nPLEASE SELECT AN APPOINTMENT");
            Optional<ButtonType> result = alert.showAndWait();

        }
        else {
            appendDeletionActivity(scheduleTableView.getSelectionModel().getSelectedItem());

            ApptmtDaoImpl appmtCtrl = new ApptmtDaoImpl(getUser());
            int id = appointmentId.getCellData(selectedId);
            String tempType = type.getCellData(selectedId);
            appmtCtrl.deleteAppointment(id);

            String currentMonth = String.valueOf(LocalDate.now().getMonth()); //fix to get filtered month or week
            scheduleTableView.setItems(appmtCtrl.getAppointmentsbyMonth(currentMonth));

            appointmentId.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
            appointmentTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
            appointmentDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
            location.setCellValueFactory(new PropertyValueFactory<>("location"));
            contact.setCellValueFactory(new PropertyValueFactory<>("contactName"));
            type.setCellValueFactory(new PropertyValueFactory<>("type"));
            startDate.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));
            endDate.setCellValueFactory(new PropertyValueFactory<>("endDateTime"));
            customerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            userId.setCellValueFactory(new PropertyValueFactory<>("userId"));

            CustomerAppointmentCountsDaoImpl customerCtrl = new CustomerAppointmentCountsDaoImpl(getUser());
            customerPivotTable.setItems(customerCtrl.getAppointmentCounts());
            appointmentTypePivot.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
            month.setCellValueFactory(new PropertyValueFactory<>("appointmentMonth"));
            count.setCellValueFactory(new PropertyValueFactory<>(String.valueOf("count")));

            UserReportDaoImpl userctrl = new UserReportDaoImpl(getUser());
            userReportTable.setItems(userctrl.getAppointmentCounts());
            userNameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
            appointmentCount.setCellValueFactory(new PropertyValueFactory<>("count"));


            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Appointment "+String.valueOf(id)+ " of type " + tempType + " successfully deleted");
            Optional<ButtonType> result = alert.showAndWait();
        }
    }

    /**
     * closes the application
     * @param event selecting the exit button
     */
    @FXML
    void onActionExit(ActionEvent event) {
        System.exit(0);
    }

    /**
     * opens the create appointment menu
     * @param event selecting the create appointment button
     * @throws IOException
     */
    @FXML
    void onActionNewAppointment(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/FXML DOCS/CreateScheduleMenu.fxml "));
        loader.load();

        CreateScheduleViewController ctrl = loader.getController();
        ctrl.sendUser(getUser());
        ctrl.sendDefaultValues();

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * switches to the customer table view
     * @param event selecting the view customers button
     * @throws IOException
     */
    @FXML
    void onActionViewCustomers(ActionEvent event) throws IOException {
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
     * generates an alert when there is an appointment in the next fifteen minutes
     */
    public void upcomingAppointmentAlert(){
        ApptmtDaoImpl ctrl = new ApptmtDaoImpl(getUser());

        String info = new String();
        ObservableList<Appointment> appmnt = ctrl.checkForAppmt();
        if(appmnt.isEmpty()){
            info = "There is no appointment in the next 15 minutes";
        }
        else{
            for (Appointment a: appmnt){
                info = info + "Appointent " + String.valueOf(a.getAppointmentId()) +
                    " at " + a.getZonedStartDateTime() + " starts in the next 15 minutes\n";
            }//make this a lambda function
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION, info);
        Optional<ButtonType> result = alert.showAndWait();
    }

    /**
     * populates the initial data
     */
    public void sendInitialData(){
        ApptmtDaoImpl ctrl = new ApptmtDaoImpl(getUser());
        String currentMonth = String.valueOf(LocalDate.now().getMonth());
        scheduleTableView.setItems(ctrl.getAppointmentsbyMonth(currentMonth));

        appointmentId.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        appointmentTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        appointmentDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        location.setCellValueFactory(new PropertyValueFactory<>("location"));
        contact.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        startDate.setCellValueFactory(new PropertyValueFactory<>("zonedStartDateTime"));

        endDate.setCellValueFactory(new PropertyValueFactory<>("zonedEndDateTime"));
        customerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        userId.setCellValueFactory(new PropertyValueFactory<>("userId"));

        CustomerAppointmentCountsDaoImpl customerCtrl = new CustomerAppointmentCountsDaoImpl(getUser());
        customerPivotTable.setItems(customerCtrl.getAppointmentCounts());

        appointmentTypePivot.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        month.setCellValueFactory(new PropertyValueFactory<>("appointmentMonth"));
        count.setCellValueFactory(new PropertyValueFactory<>(String.valueOf("count")));

        contactCombo.setItems(getAllContacts());

        UserReportDaoImpl userctrl = new UserReportDaoImpl(getUser());
        userReportTable.setItems(userctrl.getAppointmentCounts());

        userNameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        appointmentCount.setCellValueFactory(new PropertyValueFactory<>("count"));
    }

    /**
     * initializes the main table
     * @param url
     * @param rb the resource bundle for the users default language setting
     */
    @Override
    public void initialize(URL url, ResourceBundle rb){
        scheduleTableView.setItems(null);
    }
}
