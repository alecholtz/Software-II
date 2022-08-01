package DATACLASSES;

import Interfaces.AdjustTimeZone;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.sql.Time;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.util.Optional;
/**
 * A class for the appointment data type.
 * @author Alec Holtzapfel
 */
public class Appointment {
    private int appointmentId;
    private int customerId;
    private int userId;
    private String contactName;
    private String title;
    private String type;
    private String description;
    private String location;
    private String startDate;
    private String startTime;
    private String startDateTime;
    private String zonedStartDateTime;
    private String endDate;
    private String endTime;
    private String endDateTime;
    private String zonedEndDateTime;
    private boolean validAppmnt;

    /**
     * Instantiates the appointment.
     * Lambda expression used to convert from the source time zone
     * to a desired time zone when constructing an appointment.
     * The lambda function is primarily for switching between UTC
     * and the system default timezone of the user.
     * @param appointmentId integer id of the appointment
     * @param customerId integer id of the customer
     * @param userId integer id of the user
     * @param contactName string name of the contact
     * @param title string title of the appointment
     * @param type string type of the appointment
     * @param description string description of the appointment
     * @param location string location of the appointment
     * @param startDate the start date, stored as a string
     * @param startTime the start time, stored as a string
     * @param endDate the end date, stored as a string
     * @param endTime the end time stored as a string
     * @param sourceZoneId The zone id the datetimes are input
     * @param endZoneId The zone if the datetimes should be changes to
     */
    public Appointment(int appointmentId,
                       int customerId,
                       int userId,
                       String contactName,
                       String title,
                       String type,
                       String description,
                       String location,
                       String startDate,
                       String startTime,
                       String endDate,
                       String endTime,
                       String sourceZoneId,
                       String endZoneId) {
        AdjustTimeZone adjust = (t, s, e) -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime dt = LocalDateTime.parse(t, formatter);
            ZonedDateTime zdt = ZonedDateTime.of(dt,ZoneId.of(s));
            return String.valueOf(zdt.withZoneSameInstant(ZoneId.of(e)).toLocalDateTime()).replace("T", " ") + ":00";
        };

        this.appointmentId = appointmentId;
        this.customerId = customerId;
        this.userId = userId;
        this.contactName = contactName;
        this.title = title;
        this.type = type;
        this.description = description;
        this.location = location;
        try {
            setStartDateTime(adjust.adjustTime(startDate + " " + startTime, sourceZoneId, endZoneId));
        }
        catch (DateTimeParseException err) {
            if (Integer.valueOf(String.valueOf(err).split(" ")[10]) > 10){
                Alert alert = new Alert(Alert.AlertType.WARNING, "Please enter the start time in format HH:MM:SS");
                Optional<ButtonType> result = alert.showAndWait();
                this.validAppmnt = false;
                return;
            }
            else{
                Alert alert = new Alert(Alert.AlertType.WARNING, "Please enter the start date in format YYYY-MM-DD");
                Optional<ButtonType> result = alert.showAndWait();
                this.validAppmnt = false;
                return;
            }
        }

        try {
            setEndDateTime(adjust.adjustTime(endDate + " " + endTime, sourceZoneId, endZoneId));
        }
        catch (DateTimeParseException err) {
            if (Integer.valueOf(String.valueOf(err).split(" ")[10]) > 10){
                Alert alert = new Alert(Alert.AlertType.WARNING, "Please enter the end time in format HH:MM:SS");
                Optional<ButtonType> result = alert.showAndWait();
                this.validAppmnt = false;
                return;
            }
            else{
                Alert alert = new Alert(Alert.AlertType.WARNING, "Please enter the end date in format YYYY-MM-DD");
                Optional<ButtonType> result = alert.showAndWait();
                this.validAppmnt = false;
                return;
            }
        }
        this.startDate = getStartDateTime().split(" ")[0];
        this.startTime = getStartDateTime().split(" ")[1];
        this.endDate = getEndDateTime().split(" ")[0];
        this.endTime = getEndDateTime().split(" ")[1];

        zonedStartDateTime = getStartDateTime() + " " + endZoneId;
        zonedEndDateTime = getEndDateTime() + " " + endZoneId;
        this.validAppmnt = true;
    }
    /**
     * instantiates the appointment; Used for creating appointments only
     * Lambda expression used to convert from the source time zone
     * to a desired time zone when constructing an appointment.
     * The lambda function is primarily for switching between UTC
     * and the system default timezone of the user.
     * @param customerId integer customer id
     * @param userId integer user id
     * @param contactName string customer name
     * @param title string title of the appointment
     * @param type string type opf the appointment
     * @param description string description of the appointment
     * @param location string location of the appointment
     * @param startDate start data as a string
     * @param startTime start time as a string
     * @param endDate end date as a string
     * @param endTime end time as a string
     * @param sourceZoneId the zone id the datetimes are input
     * @param endZoneId the zone if the datetimes should be changes to
     */
    public Appointment(int customerId,
                       int userId,
                       String contactName,
                       String title,
                       String type,
                       String description,
                       String location,
                       String startDate,
                       String startTime,
                       String endDate,
                       String endTime,
                       String sourceZoneId,
                       String endZoneId) {
        AdjustTimeZone adjust = (t, s, e) -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime dt = LocalDateTime.parse(t, formatter);
            ZonedDateTime zdt = ZonedDateTime.of(dt,ZoneId.of(s));
            return String.valueOf(zdt.withZoneSameInstant(ZoneId.of(e)).toLocalDateTime()).replace("T", " ") + ":00";
        };

        this.appointmentId = appointmentId;
        this.customerId = customerId;
        this.userId = userId;
        this.contactName = contactName;
        this.title = title;
        this.type = type;
        this.description = description;
        this.location = location;
        try {
            setStartDateTime(adjust.adjustTime(startDate + " " + startTime, sourceZoneId, endZoneId));
        }
        catch (DateTimeParseException err) {
            if (Integer.valueOf(String.valueOf(err).split(" ")[10]) > 10){
                Alert alert = new Alert(Alert.AlertType.WARNING, "Please enter the start time in format HH:MM:SS");
                Optional<ButtonType> result = alert.showAndWait();
                this.validAppmnt = false;
                return;
            }
            else{
                Alert alert = new Alert(Alert.AlertType.WARNING, "Please enter the start date in format YYYY-MM-DD");
                Optional<ButtonType> result = alert.showAndWait();
                this.validAppmnt = false;
                return;
            }
        }

        try {
            setEndDateTime(adjust.adjustTime(endDate + " " + endTime, sourceZoneId, endZoneId));
        }
        catch (DateTimeParseException err) {
            if (Integer.valueOf(String.valueOf(err).split(" ")[10]) > 10){
                Alert alert = new Alert(Alert.AlertType.WARNING, "Please enter the end time in format HH:MM:SS");
                Optional<ButtonType> result = alert.showAndWait();
                this.validAppmnt = false;
                return;
            }
            else{
                Alert alert = new Alert(Alert.AlertType.WARNING, "Please enter the end date in format YYYY-MM-DD");
                Optional<ButtonType> result = alert.showAndWait();
                this.validAppmnt = false;
                return;
            }
        }
        this.startDate = getStartDateTime().split(" ")[0];
        this.startTime = getStartDateTime().split(" ")[1];
        this.endDate = getEndDateTime().split(" ")[0];
        this.endTime = getEndDateTime().split(" ")[1];

        zonedStartDateTime = getStartDateTime() + " " + endZoneId;
        zonedEndDateTime = getEndDateTime() + " " + endZoneId;
        this.validAppmnt = true;
    }
    /**
     * instantiates the class
     * Lambda expression used to convert from the source time zone
     * to a desired time zone when constructing an appointment.
     * The lambda function is primarily for switching between UTC
     * and the system default timezone of the user.
     * @param appointmentId Integer appointment id
     * @param startDate start date as a string
     * @param startTime start time as a string
     * @param endDate end date as a string
     * @param endTime end time as a string
     * @param customerId integer customer id
     * @param sourceZoneId the zone id the datetimes are input
     * @param endZoneId the zone if the datetimes should be changes to
     */
    public Appointment(int appointmentId, String startDate, String startTime, String endDate, String endTime, int customerId, String sourceZoneId, String endZoneId) {
        AdjustTimeZone adjust = (t, s, e) -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime dt = LocalDateTime.parse(t, formatter);
            ZonedDateTime zdt = ZonedDateTime.of(dt,ZoneId.of(s));
            return String.valueOf(zdt.withZoneSameInstant(ZoneId.of(e)).toLocalDateTime()).replace("T", " ") + ":00";
        };

        this.appointmentId = appointmentId;
        this.customerId = customerId;
        try {
            setStartDateTime(adjust.adjustTime(startDate + " " + startTime, sourceZoneId, endZoneId));
        }
        catch (DateTimeParseException err) {
            if (Integer.valueOf(String.valueOf(err).split(" ")[10]) > 10){
                Alert alert = new Alert(Alert.AlertType.WARNING, "Please enter the start time in format HH:MM:SS");
                Optional<ButtonType> result = alert.showAndWait();
                this.validAppmnt = false;
                return;
            }
            else{
                Alert alert = new Alert(Alert.AlertType.WARNING, "Please enter the start date in format YYYY-MM-DD");
                Optional<ButtonType> result = alert.showAndWait();
                this.validAppmnt = false;
                return;
            }
        }

        try {
            setEndDateTime(adjust.adjustTime(endDate + " " + endTime, sourceZoneId, endZoneId));
        }
        catch (DateTimeParseException err) {
            if (Integer.valueOf(String.valueOf(err).split(" ")[10]) > 10){
                Alert alert = new Alert(Alert.AlertType.WARNING, "Please enter the end time in format HH:MM:SS");
                Optional<ButtonType> result = alert.showAndWait();
                this.validAppmnt = false;
                return;
            }
            else{
                Alert alert = new Alert(Alert.AlertType.WARNING, "Please enter the end date in format YYYY-MM-DD");
                Optional<ButtonType> result = alert.showAndWait();
                this.validAppmnt = false;
                return;
            }
        }
        this.startDate = getStartDateTime().split(" ")[0];
        this.startTime = getStartDateTime().split(" ")[1];
        this.endDate = getEndDateTime().split(" ")[0];
        this.endTime = getEndDateTime().split(" ")[1];

        zonedStartDateTime = getStartDateTime() + " " + endZoneId;
        zonedEndDateTime = getEndDateTime() + " " + endZoneId;
        this.validAppmnt = true;
    }

    /**
     * gets the valid appmt indicator
     * @return
     */
    public boolean isValidAppmnt() {
        return validAppmnt;
    }

    /**
     * gets the appointment id
     * @return int
     */
    public int getAppointmentId() {
        return appointmentId;
    }

    /**
     * gets the customer id
     * @return int
     */
    public int getCustomerId() {
        return customerId;
    }

    /**gets the user id
     * @return int
     */
    public int getUserId() {
        return userId;
    }

    /**
     * get the contact name
     * @return string
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * get the appointment title
     * @return string
     */
    public String getTitle() {
        return title;
    }

    /**
     * returns the appointment type
     * @return string
     */
    public String getType() {
        return type;
    }

    /**
     * gets the description
     * @return string
     */
    public String getDescription() {
        return description;
    }

    /**
     * gets the user location as a string
     * @return string
     */
    public String getLocation() {
        return location;
    }

    /**
     * gets the start date as a string
     * @return string
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * gets the end date as a string
     * @return string
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * gets the start time as a string
     * @return string
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * gets the end time as a string
     * @return
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * gets the start date time as a string
     * @return string
     */
    public String getStartDateTime() {
        return startDateTime;
    }

    /**
     * sets the start date time as a string
     * @param startDateTime
     */
    public void setStartDateTime(String startDateTime) {
        this.startDateTime = startDateTime;
    }

    /**
     * gets the end datetime as a string
     * @return string
     */
    public String getEndDateTime() {
        return endDateTime;
    }

    /**
     * sets the end datetime
     * @param endDateTime string end datetime of the appointments
     */
    public void setEndDateTime(String endDateTime) {
        this.endDateTime = endDateTime;
    }

    /**
     * Gets the start date time with zone.
     * @return zonedStartDateTime
     */
    public String getZonedStartDateTime() {
        return zonedStartDateTime;
    }
    /**
     * Gets the end date time with zone.
     * @return zonedEndDateTime
     */
    public String getZonedEndDateTime() {
        return zonedEndDateTime;
    }

    /**
     * Checks if the start or end time of a appointment is within company hours.
     * The lambda function is used to convert the stored datetime back to UTC.
     * @return
     */
    public boolean validateWithinCompanyHours(){
        AdjustTimeZone adjust = (t, s, e) -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime dt = LocalDateTime.parse(t, formatter);
            ZonedDateTime zdt = ZonedDateTime.of(dt,ZoneId.of(s));
            return String.valueOf(zdt.withZoneSameInstant(ZoneId.of(e)).toLocalDateTime()).replace("T", " ") + ":00";
        };

        String start = adjust.adjustTime(startDateTime, "Etc/UTC", "America/New_York");
        String end = adjust.adjustTime(endDateTime, "Etc/UTC", "America/New_York");
        System.out.println(start);
        System.out.println(end);
        if (Time.valueOf(start.split(" ")[1]).after(Time.valueOf("22:00:00"))
                || Time.valueOf(end.split(" ")[1]).after(Time.valueOf("22:00:00"))){
                Alert alert = new Alert(Alert.AlertType.WARNING, "This appointment falls outside of regular business hours\nPlease schedule at a different time");
                Optional<ButtonType> result = alert.showAndWait();
                return false;}
        else if (Time.valueOf(start.split(" ")[1]).before(Time.valueOf("08:00:00"))
                || Time.valueOf(end.split(" ")[1]).before(Time.valueOf("08:00:00"))) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "This appointment falls outside of regular business hours\nPlease schedule at a different time");
            Optional<ButtonType> result = alert.showAndWait();
            return false;
        }
        return true;}
}
