package CONTROLLERS;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * An abstract class containing the structure for Customer related controllers.
 * @author Alec Holtzapfel
 */
public abstract class CustomerController extends Controller{
    /**
     * returns all first level divisions
     * @return list of first level divisions
     */
    public ObservableList<String> getAllDivisions(){
        try{
            String sql = "SELECT Division " +
                    "FROM first_level_divisions";

            PreparedStatement ps = getUser().getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery(sql);

            ObservableList<String> divisions = FXCollections.observableArrayList();
            while(rs.next()){
                divisions.add(rs.getString("Division"));
            }

            return divisions;
        }
        catch (SQLException err){
            System.out.println(err);
        }
        return null;
    }

    /**
     * returns all countries
     * @return list of countries
     */
    public ObservableList<String> getAllCountries(){
        try{
            String sql = "SELECT Country " +
                    "FROM countries";

            PreparedStatement ps = getUser().getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery(sql);

            ObservableList<String> countries = FXCollections.observableArrayList();
            while(rs.next()){
                countries.add(rs.getString("Country"));
            }

            return countries;
        }
        catch (SQLException err){
            System.out.println(err);
        }
        return null;
    }

    /**
     * returns a list of first level divisions within a country
     * @param country
     * @return list of first level divisions
     */
    public ObservableList<String> getAllStates(String country){
        try{
            String sql = "SELECT fld.Division\n" +
                    "FROM first_level_divisions fld\n" +
                    "INNER JOIN countries c\n" +
                    "on c.Country_ID = fld.Country_ID\n" +
                    "WHERE c.country = '" + country + "'";

            PreparedStatement ps = getUser().getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery(sql);

            ObservableList<String> states = FXCollections.observableArrayList();
            while(rs.next()){
                states.add(rs.getString("Division"));
            }

            return states;
        }
        catch(SQLException err){
            System.out.println(err);
        }
        return null;
    }
}
