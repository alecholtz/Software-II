package DATACLASSES;

/**
 * A class for the customer data type
 * @author Alec Holtzapfel
 */
public class Customer {
    private int customerId;
    private String customerName;
    private String address;
    private String postalCode;
    private String phone;
    private String division;
    private String country;

    /**
     * instantiates the class
     * @param customerId
     * @param customerName
     * @param address
     * @param postalCode
     * @param phone
     * @param division
     * @param country
     */
    public Customer(int customerId, String customerName, String address, String postalCode, String phone, String division, String country) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.division = division;
        this.country = country;
    }

    /**
     * instantiates the class; for creating a new customer only
     * @param customerName
     * @param address
     * @param postalCode
     * @param phone
     * @param division
     * @param country
     */
    public Customer(String customerName, String address, String postalCode, String phone, String division, String country) {
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.division = division;
        this.country = country;
    }

    /**
     * gets the customer id
     * @return int
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * sets the customer id
     * @param customerId
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * gets the customer name
     * @return string
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * sets the customer name
     * @param customerName
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * gets the street address
     * @return string
     */
    public String getAddress() {
        return address;
    }

    /**
     * sets the street address
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * gets the postal codes
     * @return
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * sets the postal code
     * @param postalCode
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * gets the phone number
     * @return string
     */
    public String getPhone() {
        return phone;
    }

    /**
     * sets the phone numbers
     * @param phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * gets the first level division
     * @return string
     */
    public String getDivision() {
        return division;
    }

    /**
     * sets the first level division
     * @param division
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     * gets the country
     * @return string
     */
    public String getCountry() {
        return country;
    }

    /**
     * sets the country
     * @param country
     */
    public void setCountry(String country) {
        country = country;
    }
}
