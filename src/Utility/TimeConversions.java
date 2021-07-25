package Utility;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/** This class is converting local time to UTC due to issues with my Timestamp not converting correctly. */
public class TimeConversions {
    public static Timestamp UTCtoLocal(Timestamp UTC){
        LocalDateTime ldtUTC = UTC.toLocalDateTime();
        ZonedDateTime zdtUTC = ldtUTC.atZone(ZoneId.of("UTC"));
        ZonedDateTime zdtLocal = zdtUTC.withZoneSameInstant(ZoneId.systemDefault());
        LocalDateTime ldtLocal = zdtLocal.toLocalDateTime();
        Timestamp localTime = Timestamp.valueOf(ldtLocal);
        return localTime;
    }
    public static Timestamp localUtc(Timestamp UTC){
        LocalDateTime ldtUTC = UTC.toLocalDateTime();
        ZonedDateTime zdtUTC = ldtUTC.atZone(ZoneId.systemDefault());
        ZonedDateTime zdtLocal = zdtUTC.withZoneSameInstant(ZoneId.of("UTC"));
        LocalDateTime ldtLocal = zdtLocal.toLocalDateTime();
        Timestamp localTime = Timestamp.valueOf(ldtLocal);
        return localTime;
    }
}
