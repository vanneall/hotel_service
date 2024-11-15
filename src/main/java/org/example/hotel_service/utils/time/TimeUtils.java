package org.example.hotel_service.utils.time;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class TimeUtils {

    private TimeUtils() {}

    static public Long getCurrentTimeInLong() {
        return LocalDateTime.now(ZoneOffset.systemDefault()).toEpochSecond(ZoneOffset.UTC);
    }
}
