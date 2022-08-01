package DATACLASSES;

/**
 * A class for the user report data
 * @author Alec Holtzapfel
 */
public class UserReport {
    private String username;
    private int count;

    /**
     *Creates a user report
     * @param username string name of the user
     * @param count in count of appointments for the user
     */
    public UserReport(String username, int count) {
        this.username = username;
        this.count = count;
    }

    /**
     * gets the username
     * @return string
     */
    public String getUsername() {
        return username;
    }

    /**
     * sets the username
     * @param username string name of the user
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * gets the numb er of appointments for a user
     * @return integer
     */
    public int getCount() {
        return count;
    }

    /**
     * sets the number of appointments for a user
     * @param count integer number of appointments associated with a user
     */
    public void setCount(int count) {
        this.count = count;
    }
}
