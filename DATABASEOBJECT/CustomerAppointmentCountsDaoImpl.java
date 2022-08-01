package DATABASEOBJECT;

import DATABASEACCESS.DatabaseAccessObject;
import DATACLASSES.Appointment;
import DATACLASSES.CustomerAppointmentCounts;
import DATACLASSES.LogIn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * A class for requesting the appointment counts by type data.
 * @author Alec Holtzapfel
 */
public class CustomerAppointmentCountsDaoImpl extends DatabaseAccessObject {
    /**
     * instantiates the class
     * @param user the current user of the application
     */
    public CustomerAppointmentCountsDaoImpl(LogIn user) {
        super(user);
    }

    /**
     * gets the appointments counts for the metrics table
     * @return a list of appointment counts
     */
    public ObservableList<CustomerAppointmentCounts> getAppointmentCounts(){
        try{
            String sql = "SELECT\n" +
                    " a.type,\n" +
                    " MONTHNAME(a.start) as 'Month',\n" +
                    " count(a.Appointment_ID) as 'Count'\n" +
                    "FROM appointments a\n" +
                    "GROUP BY\n" +
                    " a.type";

            PreparedStatement ps = getUser().getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            ObservableList<CustomerAppointmentCounts> counts = FXCollections.observableArrayList();
            while(rs.next()){
                counts.add(
                        new CustomerAppointmentCounts(
                                rs.getString("type"),
                                rs.getString("Month"),
                                rs.getInt("Count")
                        )
                );
            }

            return counts;
        }
        catch (SQLException err){
            System.out.println(err);
        }
        return null;
    }
}
