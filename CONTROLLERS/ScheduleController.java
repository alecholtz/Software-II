package CONTROLLERS;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * An abstract class for schedule related controllers.
 * @author Alec Holtzapfel
 */
public abstract class ScheduleController extends Controller{
    /**
     * gets all user contacts
     * @return list of contacts
     */
    public ObservableList<String> getAllContacts(){
        try{
            String sql = "SELECT Contact_Name " +
                    "FROM contacts";

            PreparedStatement ps = getUser().getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery(sql);

            ObservableList<String> contactNames = FXCollections.observableArrayList();
            while(rs.next()){
                contactNames.add(rs.getString("Contact_Name"));
            }
            return contactNames;
        }
        catch(SQLException err){
            System.out.println(err);
        }
        return null;
    }

    /**
     * gets all customers
     * @return list of customer ids
     */
    public ObservableList<Integer> getAllCustomers(){
        try{
            String sql = "SELECT Customer_ID " +
                    "FROM customers";

            PreparedStatement ps = getUser().getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery(sql);

            ObservableList<Integer> customerIds = FXCollections.observableArrayList();
            while(rs.next()){
                customerIds.add(rs.getInt("Customer_ID"));
            }
            return customerIds;
        }
        catch(SQLException err){
            System.out.println(err);
        }
        return null;
    }

    /**
     * gets all customers
     * @return list of customer ids
     */
    public ObservableList<Integer> getAllUsers(){
        try{
            String sql = "SELECT User_ID " +
                    "FROM users";

            PreparedStatement ps = getUser().getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery(sql);

            ObservableList<Integer> userIds = FXCollections.observableArrayList();
            while(rs.next()){
                userIds.add(rs.getInt("User_ID"));
            }
            return userIds;
        }
        catch(SQLException err){
            System.out.println(err);
        }
        return null;
    }
}
