package Interfaces;

/**
 * An interface for changing a string datetime from one time zone to another.
 * @author Alec Holtzapfel
 */
public interface AdjustTimeZone {
    //String value returning function which adjusts the time zone from source to end
    String adjustTime(String dateTime, String sourceZoneId, String endZoneId);
}
