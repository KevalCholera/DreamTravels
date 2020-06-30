package college_project.dreamtravels.Util;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class Constants {
    public static final String All_USER_DATA = "allUserData";
    public static final String CURRENT_USER_DATA = "currentUserData";
    public static final String CURRENT_USER_ID = "currentUserId";
    public static final String ACCESS_TOKEN = "accessToken";
    public static final String USER_SIGN_UP_DETAILS = "userSignUpDetails";
    public static final String USER_ID = "userId";
    public static final String USER_FULL_NAME = "userFullName";
    public static final String USER_FIRST = "userFirstName";
    public static final String USER_LAST = "userLastName";
    public static final String USER_MO = "userMo";
    public static final String USER_EMAIL = "userEmail";
    public static final String USER_ALTERNATE_EMAIL = "userAlternateEmail";
    public static final String USER_PASS = "userPass";
    public static final String SEQ_QUESTION1 = "secQ1";
    public static final String SEQ_QUESTION2 = "secQ2";
    public static final String SEQ_ANSWER1 = "ans1";
    public static final String SEQ_ANSWER2 = "ans2";
    public static final String USER_PROFILE = "userProfile";
    public static final String TRIP_BOOKING = "tripBooking";
    public static final String TRIP_EVENT_ID = "tripEventId";
    public static final String EVENT_AMOUNT = "eventAmount";
    public static final String EVENT_OR_HISTORY = "eventOrHistory";


    public static final String EVENT_INFO = "eventInfo";
    public static final String EVENT_ID = "eventId";
    public static final String EVENT_STATUS = "eventStatus";
    public static final String EVENT_PASSENGERS_count = "passengersCount";
    public static final String EVENT_OTHERS_REQ = "eventOthersReq";
    public static final String EVENT_VEHICLE_TYPE = "eventVehicleType";
    public static final String EVENT_LUGGAGE = "eventLuggage";
    public static final String EVENT_FROM_DATE = "eventFromDate";
    public static final String EVENT_TO_DATE = "eventToDate";
    public static final String EVENT_PICK_UP = "evetPickUp";
    public static final String EVENT_DROP_OFF = "eventDropOff";
    public static final String EVENT_DURATION_TYPE = "eventDurationType";
    public static final String EVENT_DURATION_TYPE_NAME = "eventDurationTypeName";


    public static final String EVENT_PASSENGER_FIRST_NAME = "eventPassengerFirstName";
    public static final String EVENT_PASSENGER_LAST_NAME = "eventPassengerLastName";
    public static final String EVENT_PASSENGER_FULL_NAME = "eventPassengerFullName";
    public static final String EVENT_PASSENGER_ID = "eventPassengerId";
    public static final String EVENT_PASSENGER_AGE = "eventPassengerAge";
    public static final String EVENT_PASSENGER_CONTACT_NUMBER = "eventPassengerContactNumber";
    public static final String EVENT_PASSENGER_EMAIL = "eventPassengerEmail";
    public static final String LIST_OF_PASSENGERS_DETAIL = "listOfPassengersDetails";


    public static final String TRAVELLING_TYPE_FROM_DATE = "travellingTypeFromDate";
    public static final String TRAVELLING_TYPE_TO_DATE = "travellingTypeToDate";
    public static final String TRAVELLING_TYPE_ID = "travellingTypeId";
    public static final String TRAVELLING_TYPE_NAME = "travellingTypeName";
    public static final String TRAVELLING_MODE_ID = "travellingModeId";
    public static final String TRAVELLING_MODE_NAME = "travellingModeName";
    public static final String TRAVELLING_MODE_ARRIVE_TIME = "travellingModeArriveTime";
    public static final String TRAVELLING_MODE_DEPARTURE_TIME = "travellingModeDepartureTime";
    public static final String TRAVELLING_MODE_REACHED_TIME = "travellingModeReachedTime";
    public static final String TRAVELLING_MODE_TRAVEL_MODE = "travellingModeTravelMode";
    public static final String TRAVELLING_MODE_SEAT = "travellingModeSeat";
    public static final String TRAVELLING_MODE_TIME_AQUIRE = "travellingModeTimeAquire";
    public static final String TRAVELLING_MODE_AMOUNT = "travellingModeAmount";
    public static final String TRAVEL_TYPE = "travelType";
    public static final String BUS = "bus";
    public static final String TRAIN = "train";
    public static final String FLIGHT = "flight";
    public static final String CAR = "car";


    public static final String HOTEL_BOOKING = "hotelBooking";
    public static final String HOTEL_ID = "hotelId";
    public static final String HOTEL_NAME = "hotelName";
    public static final String HOTEL_ROOM_TYPE = "hotelRoomType";
    public static final String HOTEL_ROOM_AVAILABILITY = "hotelRoomAvailability";
    public static final String HOTEL_AMOUNT = "hotelAmount";


    public static final SimpleDateFormat DATE_MONTH = new SimpleDateFormat("yyyy_MM", Locale.ENGLISH);
    public static final SimpleDateFormat DATE_FORMAT_SET = new SimpleDateFormat("dd-MM-yyyy hh:mm aa", Locale.ENGLISH);
    public static final SimpleDateFormat DATE_FORMAT_GET = new SimpleDateFormat("dd MM yyyy HH mm ss", Locale.ENGLISH);
    public static final SimpleDateFormat DATE_FORMAT_SEND = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.ENGLISH);
    public static final SimpleDateFormat DATE_FORMAT_ONLY_DATE = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
    public static final SimpleDateFormat DATE_FORMAT_ONLY_DATE1 = new SimpleDateFormat("dd-MM-yyyy hh:mm aa", Locale.ENGLISH);
    public static final SimpleDateFormat DATE_FORMAT_ONLY_TIME = new SimpleDateFormat("hh:mm aa", Locale.ENGLISH);
    public static final SimpleDateFormat DATE_FORMAT_DATE_TIME = new SimpleDateFormat("dd-MM-yyyy \n hh:mm aa", Locale.ENGLISH);
    public static final SimpleDateFormat DATE_FORMAT_FULL_DATE_TIME_DOWN = new SimpleDateFormat("dd-MMM-yyyy \n HH:mm", Locale.ENGLISH);
    public static final SimpleDateFormat DATE_FORMAT_FULL_DATE_TIME = new SimpleDateFormat("dd-MMMM-yyyy HH:mm", Locale.ENGLISH);
    public static final SimpleDateFormat DATE_FORMAT_TIME_MIN = new SimpleDateFormat("mm", Locale.ENGLISH);
    public static final SimpleDateFormat DATE_FORMAT_SMALL_TIME_HOUR = new SimpleDateFormat("hh", Locale.ENGLISH);
    public static final SimpleDateFormat DATE_FORMAT_BIG_TIME_HOUR = new SimpleDateFormat("HH", Locale.ENGLISH);

    public static final SimpleDateFormat DATE_FORMAT_YEAR = new SimpleDateFormat("yyyy", Locale.ENGLISH);
    public static final SimpleDateFormat DATE_FORMAT_MONTH = new SimpleDateFormat("MM", Locale.ENGLISH);
    public static final SimpleDateFormat DATE_FORMAT_DATE = new SimpleDateFormat("dd", Locale.ENGLISH);
    public static final SimpleDateFormat DATE_FORMAT_TIME_AM_PM = new SimpleDateFormat("aa", Locale.ENGLISH);
    public static final SimpleDateFormat DATE_FORMAT_EXTRA = new SimpleDateFormat("dd-MMM-yyyy HH:mm", Locale.ENGLISH);
    public static final SimpleDateFormat DATE_FORMAT_BIG_TIME_HOUR_MIN = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
    public static final SimpleDateFormat DATE_FORMAT_BIG_TIME_HOUR_MIN1 = new SimpleDateFormat("hh:mm aa", Locale.ENGLISH);

}