package DATACLASSES;

import java.sql.*;
import java.util.Locale;

/**
 * A class containing the login information, connection information, and the ability to validate the users login
 * @author Alec Holtzapfel
 */
public final class LogIn {
    private String UserName;
    private int userId;
    private boolean ValidLogin;
    private static Connection conn = null;

    private static final String protocol = "jdbc";
    private static final String vendorName = ":mysql:";
    private static final String ipAddress = "//localhost/client_schedule";
    private static final String jdbcURL = protocol + vendorName + ipAddress + "?connectionTimeZone = SERVER";

    private static final String MYSQLJDBCDriver = "com.mysql.cj.jdbc.Driver";

    private static final String username = "sqlUser";
    private static String password = "Passw0rd!";

    /**
     * checks the login information and instantiates the class
     * @param UserName the input username
     * @param Password the input password
     */
    public LogIn(String UserName, String Password) {
        this.UserName = UserName;
        ValidLogin = validateCredentials(UserName, Password);
        if (ValidLogin){
            conn = startConnection();
        }
    }

    /**
     * gets the user name
     * @return string
     */
    public String getUserName(){
        return UserName;
    }

    /**
     * gets the user Id
     * @return int
     */
    public int getUserId() {
        return userId;
    }

    /**
     * sets the user id
     * @param userId the integer id of the user
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     *gets the boolean indicating a valid login
     * @return boolean
     */
    public boolean getValidLogin(){
        return ValidLogin;
    }

    /**
     * attempts to start a connection to the database
     * @return Connections
     */
    public static Connection startConnection(){
        Connection tempConn = null;

        try {
            Class.forName(MYSQLJDBCDriver);
            tempConn = DriverManager.getConnection(jdbcURL, username, password);
        } catch (ClassNotFoundException err) {
            System.out.println(err);
        } catch (SQLException err) {
            System.out.println(err);
        }
        return tempConn;
    }

    /**
     * gets the connection to the database
     * @return Connection
     */
    public static Connection getConnection(){
        return conn;
    }

    /**
     * closes the connection to the database
     */
    public static void closeConnection() {
        try{conn.close();}
        catch(SQLException err){
            System.out.println(err);
        }
    }

    /**
     * validates the input credentials against the sql database
     * @param UserName the username being validated
     * @param Password the password being validated
     * @return boolean
     */
    private boolean validateCredentials(String UserName, String Password){
        conn = startConnection();
        try{
            String sql = "SELECT User_ID, User_Name," +
                    " Password from users where User_Name = '"+UserName+"'" +
                    " and Password = '"+Password+"';";

            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                if (rs.getString("User_Name").equals(UserName) & rs.getString("Password").equals(Password)){
                    setUserId(rs.getInt("User_Id"));
                    return true;
                }
            }
        }
        catch(SQLException err){
            System.out.println(err);
        }

        closeConnection();
        conn = null;
        return false;
    }

}
