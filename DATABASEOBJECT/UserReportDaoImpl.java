package DATABASEOBJECT;

import DATABASEACCESS.DatabaseAccessObject;
import DATACLASSES.CustomerAppointmentCounts;
import DATACLASSES.LogIn;
import DATACLASSES.UserReport;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * A class for the user report table.
 * @author Alec Holtzapfel
 */
public class UserReportDaoImpl extends DatabaseAccessObject {
    public UserReportDaoImpl(LogIn user) {
        super(user);
    }

    /**
     * Gets the appointment counts by username.
     * @return ObservableList of UserReport class.
     */
    public ObservableList<UserReport> getAppointmentCounts(){
        try{
            String sql = "SELECT u.User_Name, COUNT(Appointment_ID) as 'count'\n" +
                    "FROM appointments a\n" +
                    "LEFT JOIN users u\n" +
                    "on u.User_ID = a.User_ID\n" +
                    "GROUP BY u.User_Name";

            PreparedStatement ps = getUser().getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            ObservableList<UserReport> counts = FXCollections.observableArrayList();
            while(rs.next()){
                counts.add(
                        new UserReport(
                                rs.getString("User_Name"),
                                rs.getInt("count")
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
