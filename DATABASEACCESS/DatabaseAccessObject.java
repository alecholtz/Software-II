package DATABASEACCESS;

import DATACLASSES.LogIn;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * An abstract class for accessing the database.
 * @author Alec Holtzapfel
 */
public abstract class DatabaseAccessObject {
    private static LogIn user;

    /**
     * Creates a Database Access Object Instance
     * @param user the current valid user
     */
    public DatabaseAccessObject(LogIn user) {
        this.user = user;
    }

    /**
     *Gets the user
     * @return LogIn
     */
    public static LogIn getUser() {
        return user;
    };

    /**
     * updates a table in SQL
     * @param sql a sql statement for updating a table as a string
     */
    public void adaptTable(String sql){
        try{
            PreparedStatement ps = getUser().getConnection().prepareStatement(sql);
            ps.executeUpdate();
        }
        catch(SQLException err){
            System.out.println(err);
        }
    }
}
