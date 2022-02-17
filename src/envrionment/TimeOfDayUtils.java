package envrionment;

import java.sql.Time;

public class TimeOfDayUtils {
    public static final String DAY_STR = "day";
    public static final String NIGHT_STR = "night";
    public static final String BOTH_STR = "both";
    private static final String UNKNOWN_ENUM_ERR = "%s has no string assigned";
    private static final String UNKNOWN_STR_ERR = "%s has no enum assigned";

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
