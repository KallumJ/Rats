package envrionment;

/**
 * A class to provide helper methods pertaining to managing the Time of Day
 *
 * @author Kallum Jones (2005855) (17/02/22)
 */
public class TimeOfDayUtils {
    public static final String DAY_STR = "day";
    public static final String NIGHT_STR = "night";
    public static final String BOTH_STR = "both";
    private static final String UNKNOWN_ENUM_ERR = "%s has no string assigned";
    private static final String UNKNOWN_STR_ERR = "%s has no enum assigned";

    /**
     * Convert the provided enum into a string
     * @param timeOfDay the TimeOfDay enum to convert
     * @return the time of day as a string
     */
    public static String getStringFromEnum(TimeOfDay timeOfDay) {
        switch (timeOfDay) {
            case DAY:
                return DAY_STR;
            case NIGHT:
                return NIGHT_STR;
            case BOTH:
                return BOTH_STR;
        }
        throw new IllegalArgumentException(String.format(UNKNOWN_ENUM_ERR, timeOfDay));
    }

    /**
     * Convert the provided string to a TimeOfDay enum
     * @param timeString the string provided
     * @return The time of day as a TimeOfDay enum
     */
    public static TimeOfDay getEnumFromString(String timeString) {
        switch (timeString) {
            case DAY_STR:
                return TimeOfDay.DAY;
            case NIGHT_STR:
                return TimeOfDay.NIGHT;
            case BOTH_STR:
                return TimeOfDay.BOTH;
        }

        throw new IllegalArgumentException(String.format(UNKNOWN_STR_ERR, timeString));
    }
}
