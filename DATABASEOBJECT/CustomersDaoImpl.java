package DATABASEOBJECT;

import DATABASEACCESS.DatabaseAccessObject;
import DATACLASSES.Customer;
import DATACLASSES.LogIn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * A class for collecting, updating, and deleting customer related data from the database.
 * @author Alec Holtzapfel
 */
public class CustomersDaoImpl extends DatabaseAccessObject {
    /**
     * instantiates the class
     * @param user the current user of the application
     */
    public CustomersDaoImpl(LogIn user) {
        super(user);
    }

    /**
     * gets a list of all customers
     * @return a list of customers
     */
    public static ObservableList<Customer> getAllCustomers(){
        try{
            String sql = "SELECT c.Customer_ID,\n" +
                    "\tc.Customer_Name,\n" +
                    "    c.Address,\n" +
                    "    c.Postal_Code,\n" +
                    "    c.Phone,\n" +
                    "    fld.Division,\n" +
                    "    ct.Country\n" +
                    "FROM Customers c\n" +
                    "LEFT JOIN first_level_divisions fld\n" +
                    "on fld.Division_ID = c.Division_ID\n" +
                    "LEFT JOIN countries ct\n" +
                    "on ct.Country_ID = fld.Country_ID\n";

            PreparedStatement ps = getUser().getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery(sql);

            ObservableList<Customer> customers = FXCollections.observableArrayList();
            while(rs.next()){
                customers.add(new Customer(
                        rs.getInt("Customer_ID"),
                        rs.getString("Customer_Name"),
                        rs.getString("Address"),
                        rs.getString("Postal_Code"),
                        rs.getString("Phone"),
                        rs.getString("Division"),
                        rs.getString("Country")
                    )
                );
            }

            return customers;
        }
        catch(SQLException err){
            System.out.println(err);
        }
        return null;
    }

    /**
     * creates a new customer in the customer table
     * @param customer the customer being created from the create customer menu
     */
    public void createCustomer(Customer customer){
        String sql = "INSERT INTO customers \n" +
                "\t(Customer_Name,\n" +
                "    Address,\n" +
                "    Postal_Code,\n" +
                "    Phone,\n" +
                "    Create_Date,\n" +
                "    Created_By,\n" +
                "    Last_Update,\n" +
                "    Last_Updated_By,\n" +
                "    Division_ID)\n" +
                "VALUES \n" +
                "\t('" + customer.getCustomerName() + "',\n" +
                "    '" + customer.getAddress() + "',\n" +
                "    '" + customer.getPostalCode() + "',\n" +
                "    '" + customer.getPhone() + "',\n" +
                "    localtime(),\n" +
                "    '" + getUser().getUserName() + "',\n" +
                "    localtime(),\n" +
                "    '" + getUser().getUserName() +"',\n"+
                "     (select Division_ID \n" +
                "        from first_level_divisions\n" +
                "        where Division = '" + customer.getDivision() + "')\n" +
                "    )";
        super.adaptTable(sql);
    }

    /**
     * updates a customer in the table
     * @param customer the customer being updated from the edit customer menu
     */
    public void updateCustomer(Customer customer){
        String sql = "UPDATE customers\n" +
                "SET Customer_Name = '" + customer.getCustomerName() + "',\n" +
                "\tAddress = '" + customer.getAddress() + "',\n" +
                "    Postal_Code = '" + customer.getPostalCode() + "',\n" +
                "    Phone = '" + customer.getPhone() + "',\n" +
                "    Last_Update = localtime(),\n" +
                "    Last_Updated_By = '" + getUser().getUserName() + "',\n" +
                "    Division_ID = (select Division_ID\n" +
                "\t\tfrom first_level_divisions\n" +
                "        where Division = '" + customer.getDivision() + "') " +
                "where Customer_ID = " + customer.getCustomerId();
        super.adaptTable(sql);
    }

    /**
     * deletes a customer from the table
     * @param customerId integer id of the customer being deleted
     */
    public void deleteCustomer(int customerId){
        String sql = "DELETE FROM customers\n" +
                "WHERE Customer_ID = " + customerId;
        super.adaptTable(sql);
    }
}
