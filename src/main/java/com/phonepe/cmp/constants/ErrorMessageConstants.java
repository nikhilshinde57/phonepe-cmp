package com.phonepe.cmp.constants;

public class ErrorMessageConstants {


    /**
     * Error messages related to City entity
     */

    public static final String CITY_BY_ID_NOT_FOUND = "City with given id not found";
    public static final String CITY_BY_NAME_NOT_FOUND = "City with given name not found";
    public static final String CITY_NAME_NOT_NULL = "City name cannot be null";
    public static final String CITY_NAME_NOT_BLANK = "City name cannot be blank";
    public static final String CITY_DUPLICATE_NAME = "City with given name already exists";
    public static final String CITY_ID_NOT_NULL = "City id cannot be null";
    public static final String START_AND_DATE_REQUIRED = "Start and End date required";
    public static final String FROM_DATE_SHOULD_BE_LESS_THAN_TO_DATE = "From date should be less than to date";
    public static final String CITY_ID_NOT_BLANK = "City id cannot be blank";
    /**
     * Error messages related to Driver entity
     */

    public static final String DRIVER_BY_ID_NOT_FOUND = "Driver with given id not found";
    public static final String DRIVER_BY_NAME_NOT_FOUND = "Driver with given name not found";
    public static final String DRIVER_NAME_NOT_NULL = "City name cannot be null";
    public static final String DRIVER_ID_NOT_NULL = "Driver id cannot be null";
    public static final String DRIVER_ID_NOT_BLANK = "Driver id cannot be blank";
    public static final String DRIVER_NAME_NOT_BLANK = "Driver name cannot be blank";
    public static final String DRIVER_DUPLICATE_EMAIL_OR_CONTACT = "Driver with given email or contact already exists";
    public static final String FIRST_NAME_NOT_BLANK = "Driver first name cannot be blank";
    public static final String FIRST_NAME_NOT_NULL = "Driver first name cannot be null";
    public static final String LAST_NAME_NOT_BLANK = "Driver last name cannot be blank";
    public static final String LAST_NAME_NOT_NULL = "Driver last name cannot be null";
    public static final String EMAIL_NOT_NULL = "Driver email cannot be null";
    public static final String EMAIL_NOT_BLANK = "Driver email cannot be blank";
    public static final String EMAIL_ID_NOT_PROPER = "Please enter a valid Email ID";
    public static final String CONTACT_NOT_NULL = "Driver contact cannot be null";
    public static final String CONTACT_NOT_BLANK = "Driver contact cannot be blank";
    public static final String CONTACT_NOT_PROPER = "Please enter a valid contact with min length of 10 and max length 15";
    /**
     * Error messages related to Rider entity
     */

    public static final String RIDER_BY_ID_NOT_FOUND = "Rider with given id not found";
    public static final String RIDER_ID_NOT_NULL = "Rider id cannot be null";
    /**
     * Error messages related to Cab entity
     */

    public static final String CAB_NUMBER_NOT_BLANK = "Cab number name cannot be blank";
    public static final String CAB_NUMBER_NOT_NULL = "Cab number cannot be null";
    public static final String CAB_NUMBER_NOT_PROPER = "Please enter a valid cab number with 8";

    public static final String CAB_TYPE_NOT_NULL = "Cab type cannot be null";
    public static final String CAB_BY_ID_NOT_FOUND = "Cab with given id not found";
    public static final String CAB_ID_NOT_NULL = "Cab id cannot be null";
    public static final String CAB_DUPLICATE_CAB_NUMBER = "Cab with given cab number already exists";
    public static final String CAB_NOT_AVAILABLE = "Cab not available";
    /**
     * Error messages related to Trip entity
     */

    public static final String TRIP_DUPLICATE_WITH_CAB_NUMBER = "There is a ongoing trip exists with given cab id";
    public static final String TRIP_BY_ID_NOT_FOUND = "Trip with given id not found";
    public static final String TRIP_ID_NOT_NULL = "Trip id cannot be null";
    public static final String TRIP_STATUS_CANNOT_UPDATE = "Ongoing trip can not move to book status";
    public static final String TRIP_STATUS_CANNOT_UPDATE_FROM_FINISHED = "Can not update finished trip to other status";

    private ErrorMessageConstants() {
    }
}
