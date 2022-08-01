package DATACLASSES;

/**
 * A class for the appointment type by month data
 * @author Alec Holtzapfel
 */
public class CustomerAppointmentCounts {
    private String appointmentType;
    private String appointmentMonth;
    private int count;

    /**
     * instantiates the class
     * @param appointmentType
     * @param appointmentMonth
     * @param count
     */
    public CustomerAppointmentCounts(String appointmentType, String appointmentMonth, int count) {
        this.appointmentType = appointmentType;
        this.appointmentMonth = appointmentMonth;
        this.count = count;
    }

    /**
     * gets the appointment type
     * @return string
     */
    public String getAppointmentType() {
        return appointmentType;
    }

    /**
     * sets the appointment type
     * @param appointmentType
     */
    public void setAppointmentType(String appointmentType) {
        this.appointmentType = appointmentType;
    }

    /**
     * gets the appointment month
     * @return string
     */
    public String getAppointmentMonth() {
        return appointmentMonth;
    }

    /**
     * sets the appointment month
     * @param appointmentMonth
     */
    public void setAppointmentMonth(String appointmentMonth) {
        this.appointmentMonth = appointmentMonth;
    }

    /**
     * gets the appointment count
     * @return int
     */
    public int getCount() {
        return count;
    }

    /**
     * sets the appointment counts
     * @param count
     */
    public void setCount(int count) {
        this.count = count;
    }
}
