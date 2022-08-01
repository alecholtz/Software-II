package DATABASEOBJECT;

import DATABASEACCESS.DatabaseAccessObject;
import DATACLASSES.Appointment;
import DATACLASSES.LogIn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;

/**
 * A class for requesting appointment related information for the database.
 * @author Alec Holtzapfel
 */
public class ApptmtDaoImpl extends DatabaseAccessObject {
    public ApptmtDaoImpl(LogIn user) {
        super(user);
    }

    /**
     * checks for an appointment in the next fifteen minutes
     * @return a list of appointments in the next fifteen minutes
     */
    public static ObservableList<Appointment> checkForAppmt(){
        try{
            String sql = "SELECT a.Appointment_ID,\n" +
                    "\ta.Title,\n" +
                    "    a.Description,\n" +
                    "    a.location,\n" +
                    "    a.Type,\n" +
                    "    DATE(a.Start) as 'Start Date',\n" +
                    "    DATE(a.end) as 'End Date',\n" +
                    "    TIME(a.Start) as 'Start Time',\n" +
                    "    Time(a.end) as 'End Time',\n" +
                    "    a.Customer_ID,\n" +
                    "    a.User_ID,\n" +
                    "    co.Contact_Name\n" +
                    "FROM appointments a\n" +
                    "LEFT JOIN customers c\n" +
                    "on c.Customer_ID = a.Customer_ID\n" +
                    "LEFT JOIN contacts co\n" +
                    "on co.Contact_ID = a.Contact_ID\n" +
                    "WHERE localtime() BETWEEN Start - INTERVAL 15 MINUTE AND Start\n" +
                    "AND User_ID = "+getUser().getUserId();

            PreparedStatement ps = getUser().getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            ObservableList<Appointment> schedule = FXCollections.observableArrayList();
            while(rs.next()){
                schedule.add(
                        new Appointment(
                                rs.getInt("Appointment_ID"),
                                rs.getInt("Customer_ID"),
                                rs.getInt("User_ID"),
                                rs.getString("Contact_Name"),
                                rs.getString("Title"),
                                rs.getString("Type"),
                                rs.getString("Description"),
                                rs.getString("Location"),
                                rs.getString("Start Date"),
                                String.valueOf(rs.getTime("Start Time")),
                                rs.getString("End Date"),
                                String.valueOf(rs.getTime("End Time")),
                                "Etc/UTC",
                                String.valueOf(ZoneId.systemDefault())
                        )
                );
            }

            return schedule;
        }
        catch (SQLException err){
            System.out.println(err);
        }
        return null;
    }

    /**
     * checks the table for an appointment which overlaps with the given appointments
     * @param appt the appointment being check for conflicts
     * @return a list of appointments
     */
    public static ObservableList<Appointment> checkForConflictingAppmt(Appointment appt){
        try{
            String sql = "SELECT a.Appointment_ID,\n" +
                    "\ta.Title,\n" +
                    "    a.Description,\n" +
                    "    a.location,\n" +
                    "    a.Type,\n" +
                    "    DATE(a.Start) as 'Start Date',\n" +
                    "    DATE(a.end) as 'End Date',\n" +
                    "    TIME(a.Start) as 'Start Time',\n" +
                    "    Time(a.End) as 'End Time',\n" +
                    "    a.Customer_ID,\n" +
                    "    a.User_ID,\n" +
                    "    co.Contact_Name\n" +
                    "FROM appointments a\n" +
                    "LEFT JOIN customers c\n" +
                    "on c.Customer_ID = a.Customer_ID\n" +
                    "LEFT JOIN contacts co\n" +
                    "on co.Contact_ID = a.Contact_ID\n" +
                    "WHERE a.Customer_ID = " + appt.getCustomerId() + " AND a.Appointment_ID <> " + appt.getAppointmentId() + "\n" +
                    " AND (Start BETWEEN '" + appt.getStartDateTime() + "' AND '" + appt.getEndDateTime() +"'\n"+
                    "OR End BETWEEN '" + appt.getStartDateTime() + "' AND '" + appt.getEndDateTime() +"'\n"+
                    "OR '" + appt.getStartDateTime() + "' BETWEEN Start AND End " +
                    "OR '" + appt.getEndDateTime() + "' BETWEEN Start AND End) ";
            PreparedStatement ps = getUser().getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            ObservableList<Appointment> schedule = FXCollections.observableArrayList();
            while(rs.next()){
                schedule.add(
                        new Appointment(
                                rs.getInt("Appointment_ID"),
                                rs.getInt("Customer_ID"),
                                rs.getInt("User_ID"),
                                rs.getString("Contact_Name"),
                                rs.getString("Title"),
                                rs.getString("Type"),
                                rs.getString("Description"),
                                rs.getString("Location"),
                                rs.getString("Start Date"),
                                String.valueOf(rs.getTime("Start Time")),
                                rs.getString("End Date"),
                                String.valueOf(rs.getTime("End Time")),
                                "Etc/UTC",
                                String.valueOf(ZoneId.systemDefault())

                        )
                );
            }
            return schedule;
        }
        catch (SQLException err){
            System.out.println(err);
        }
        return null;
    }

    /**
     *gets all appointments from the table
     * @return a list of appointments
     */
    public static ObservableList<Appointment> getAllAppointments() {
        try{
            String sql = "SELECT a.Appointment_ID,\n" +
                    "\ta.Title,\n" +
                    "    a.Description,\n" +
                    "    a.location,\n" +
                    "    a.Type,\n" +
                    "    DATE(a.Start) as 'Start Date',\n" +
                    "    DATE(a.end) as 'End Date',\n" +
                    "    TIME(a.Start) as 'Start Time',\n" +
                    "    Time(a.end) as 'End Time',\n" +
                    "    a.Customer_ID,\n" +
                    "    a.User_ID,\n" +
                    "    co.Contact_Name\n" +
                    "FROM appointments a\n" +
                    "LEFT JOIN customers c\n" +
                    "on c.Customer_ID = a.Customer_ID\n" +
                    "LEFT JOIN contacts co\n" +
                    "on co.Contact_ID = a.Contact_ID\n";

            PreparedStatement ps = getUser().getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            ObservableList<Appointment> schedule = FXCollections.observableArrayList();
            while(rs.next()){
                schedule.add(
                        new Appointment(
                                rs.getInt("Appointment_ID"),
                                rs.getInt("Customer_ID"),
                                rs.getInt("User_ID"),
                                rs.getString("Contact_Name"),
                                rs.getString("Title"),
                                rs.getString("Type"),
                                rs.getString("Description"),
                                rs.getString("Location"),
                                rs.getString("Start Date"),
                                String.valueOf(rs.getTime("Start Time")),
                                rs.getString("End Date"),
                                String.valueOf(rs.getTime("End Time")),
                                "Etc/UTC",
                                String.valueOf(ZoneId.systemDefault())
                        )
                );
            }

            return schedule;
        }
        catch(SQLException err){
            System.out.println(err);
        }
        return null;
    }

    /**
     * returns all appointments for a given month
     * @param month the month as a string
     * @return a list of appointments
     */
    public static ObservableList<Appointment> getAppointmentsbyMonth(String month) {
        try{
            String sql = "SELECT a.Appointment_ID,\n" +
                    "\ta.Title,\n" +
                    "    a.Description,\n" +
                    "    a.location,\n" +
                    "    a.Type,\n" +
                    "    DATE(a.Start) as 'Start Date',\n" +
                    "    DATE(a.end) as 'End Date',\n" +
                    "    TIME(a.Start) as 'Start Time',\n" +
                    "    Time(a.end) as 'End Time',\n" +
                    "    a.Customer_ID,\n" +
                    "    a.User_ID,\n" +
                    "    co.Contact_Name\n" +
                    "FROM appointments a\n" +
                    "LEFT JOIN customers c\n" +
                    "on c.Customer_ID = a.Customer_ID\n" +
                    "LEFT JOIN contacts co\n" +
                    "on co.Contact_ID = a.Contact_ID\n" +
                    "where a.User_ID = "+ getUser().getUserId() +
                    "\tAND monthname(a.Start) = '" + month + "'";

            PreparedStatement ps = getUser().getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            ObservableList<Appointment> schedule = FXCollections.observableArrayList();
            while(rs.next()){
                schedule.add(
                        new Appointment(
                                rs.getInt("Appointment_ID"),
                                rs.getInt("Customer_ID"),
                                rs.getInt("User_ID"),
                                rs.getString("Contact_Name"),
                                rs.getString("Title"),
                                rs.getString("Type"),
                                rs.getString("Description"),
                                rs.getString("Location"),
                                rs.getString("Start Date"),
                                String.valueOf(rs.getTime("Start Time")),
                                rs.getString("End Date"),
                                String.valueOf(rs.getTime("End Time")),
                                "Etc/UTC",
                                String.valueOf(ZoneId.systemDefault())
                        )
                );
            }

            return schedule;
        }
        catch(SQLException err){
            System.out.println(err);
        }
        return null;
    }

    /**
     * gets all appointments for a week number
     * @param week the week as an integer
     * @return a list of appointments
     */
    public static ObservableList<Appointment> getAppointmentsbyWeek(String week){
        if (week == null){
            week = "WEEK(localtime())";
        }

        try{
            String sql = "SELECT a.Appointment_ID,\n" +
                    "\ta.Title,\n" +
                    "    a.Description,\n" +
                    "    a.location,\n" +
                    "    a.Type,\n" +
                    "    DATE(a.Start) as 'Start Date',\n" +
                    "    DATE(a.end) as 'End Date',\n" +
                    "    TIME(a.Start) as 'Start Time',\n" +
                    "    Time(a.end) as 'End Time',\n" +
                    "    a.Customer_ID,\n" +
                    "    a.User_ID,\n" +
                    "    co.Contact_Name\n" +
                    "FROM appointments a\n" +
                    "LEFT JOIN customers c\n" +
                    "on c.Customer_ID = a.Customer_ID\n" +
                    "LEFT JOIN contacts co\n" +
                    "on co.Contact_ID = a.Contact_ID\n" +
                    "where a.User_ID = "+ getUser().getUserId() +
                    "\tAND week(a.Start) = " + week;

            PreparedStatement ps = getUser().getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            ObservableList<Appointment> schedule = FXCollections.observableArrayList();
            while(rs.next()){
                schedule.add(
                        new Appointment(
                                rs.getInt("Appointment_ID"),
                                rs.getInt("Customer_ID"),
                                rs.getInt("User_ID"),
                                rs.getString("Contact_Name"),
                                rs.getString("Title"),
                                rs.getString("Type"),
                                rs.getString("Description"),
                                rs.getString("Location"),
                                rs.getString("Start Date"),
                                String.valueOf(rs.getTime("Start Time")),
                                rs.getString("End Date"),
                                String.valueOf(rs.getTime("End Time")),
                                "Etc/UTC",
                                String.valueOf(ZoneId.systemDefault())
                        )
                );
            return schedule;
            }
        }
        catch(SQLException err){
            System.out.println(err);
        }

        return null;
    }

    /**
     * creates a new appointment in the table
     * @param appointment the appointment being created, from the create schedule menu
     */
    public void createAppointment(Appointment appointment){
        String sql = "INSERT INTO appointments(Title,\n" +
                "\t\t\t\tDescription,\n" +
                "                Location,\n" +
                "                Type,\n" +
                "                Start,\n" +
                "                End,\n" +
                "                Create_Date,\n" +
                "                Created_By,\n" +
                "                Last_Update,\n" +
                "                Last_Updated_By,\n" +
                "                Customer_ID,\n" +
                "                User_ID,\n" +
                "                Contact_ID)\n" +
                "VALUES('"+appointment.getTitle()+"',\n'"+
                appointment.getDescription()+"',\n'"+
                appointment.getLocation()+"',\n'"+
                appointment.getType()+"',\n'"+
                appointment.getStartDateTime()+"',\n'"+
                appointment.getEndDateTime() +"',\n"+
                "localtime(),\n'"+
                getUser().getUserName() +"',\n"+
                "localtime(),\n'"+
                getUser().getUserName() +"',\n'"+
                appointment.getCustomerId()+"',\n"+
                getUser().getUserId()+",\n"+
                "(SELECT Contact_ID\n" +
                "FROM contacts\n" +
                "where Contact_Name = '"+ appointment.getContactName()+"')"+
                ")";
        super.adaptTable(sql);
    }

    /**
     * updates an appointment in the table
     * @param appointment the appointment being updated
     */
    public void updateAppointment(Appointment appointment){
        String sql = "UPDATE appointments\n" +
                "SET Title = '" + appointment.getTitle() + "',\n" +
                "\tDescription = '" + appointment.getDescription() + "',\n" +
                "    Location = '" + appointment.getLocation() + "',\n" +
                "    type = '" + appointment.getType() + "',\n" +
                "    Start = '"+ appointment.getStartDateTime() + "',\n" +
                "    End = '" + appointment.getEndDateTime()+"',\n"+
                "    Last_Update = localtime(),\n" +
                "    Last_Updated_By = '" + getUser().getUserName() + "',\n" +
                "    Customer_ID = " + appointment.getCustomerId() + ",\n" +
                "    User_ID = " + getUser().getUserId() + ",\n" +
                "    Contact_ID = (SELECT Contact_ID\n" +
                "                FROM contacts\n" +
                "                where Contact_Name = '"+ appointment.getContactName()+"')\n" +
                "WHERE Appointment_ID = " + appointment.getAppointmentId() + ";";
        super.adaptTable(sql);
    }

    /**
     * updates the appointment times of an appointment
     * @param appointmentId integer id of an appointment
     * @param start start datetime as a string
     * @param end end datetime as a string
     */
    public void updateAppointmentTime(int appointmentId, String start, String end){
        String sql = "UPDATE appointments " +
                " SET Start = '" + start +
                "', End = '" + end +
                "' WHERE Appointment_ID = " + appointmentId;
        super.adaptTable(sql);
    }

    /**
     * deletes an appointment from the appointment table
     * @param appointmentId integer appointment id for deletion
     */
    public void deleteAppointment(int appointmentId){
        String sql = "DELETE FROM appointments\n" +
                "WHERE Appointment_ID = " + appointmentId + "";
        super.adaptTable(sql);
    }

    /**
     * gets a list of appointments by contact name
     * @param name string name of the contact
     * @return a list of appointments
     */
    public static ObservableList<Appointment> getAppointmentsbyContactName(String name) {
        try{
            String sql = "SELECT a.Appointment_ID,\n" +
                    "a.Title,\n" +
                    "a.Type,\n" +
                    "a.Description,\n" +
                    "DATE(a.Start) as 'Start Date',\n" +
                    "DATE(a.End) as 'End Date',\n" +
                    "TIME(a.Start) as 'Start Time',\n" +
                    "TIME(a.End) as 'End Time',\n" +
                    "a.Customer_ID,\n" +
                    "a.Contact_ID,\n" +
                    "a.User_ID,\n" +
                    "c.Contact_Name,\n" +
                    "a.Location\n" +
                    "FROM contacts c\n" +
                    "INNER JOIN appointments a\n" +
                    "on a.Contact_ID = c.Contact_ID\n" +
                    "WHERE c.Contact_Name = '" + name + "'";

            PreparedStatement ps = getUser().getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            ObservableList<Appointment> schedule = FXCollections.observableArrayList();
            while(rs.next()){
                schedule.add(
                        new Appointment(
                                rs.getInt("Appointment_ID"),
                                rs.getInt("Customer_ID"),
                                rs.getInt("User_ID"),
                                rs.getString("Contact_Name"),
                                rs.getString("Title"),
                                rs.getString("Type"),
                                rs.getString("Description"),
                                rs.getString("Location"),
                                rs.getString("Start Date"),
                                String.valueOf(rs.getTime("Start Time")),
                                rs.getString("End Date"),
                                String.valueOf(rs.getTime("End Time")),
                                "Etc/UTC",
                                String.valueOf(ZoneId.systemDefault())
                        )
                );
            }

            return schedule;
        }
        catch(SQLException err){
            System.out.println(err);
        }
        return null;
    }
}
