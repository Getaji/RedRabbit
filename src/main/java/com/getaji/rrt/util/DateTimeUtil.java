package com.getaji.rrt.util;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

/**
 * javadoc here.
 *
 * @author Getaji
 */
public class DateTimeUtil {

    public static ThreeValues<TemporalUnit, Long, String> getLocalDateTimeDiff(LocalDateTime startDT, LocalDateTime endDT) {
        long years = startDT.until(endDT, ChronoUnit.YEARS);
        if (0 < years) {
            return ThreeValues.of(ChronoUnit.YEARS, years, years + "y");
        }
        long days = startDT.until(endDT, ChronoUnit.DAYS);
        if (0 < days) {
            return ThreeValues.of(ChronoUnit.DAYS, days, days + "d");
        }
        long hours = startDT.until(endDT, ChronoUnit.HOURS);
        if (0 < hours) {
            return ThreeValues.of(ChronoUnit.HOURS, hours, hours + "h");
        }
        long minutes = startDT.until(endDT, ChronoUnit.MINUTES);
        if (0 < minutes) {
            return ThreeValues.of(ChronoUnit.MINUTES, minutes, minutes + "m");
        }
        long seconds = startDT.until(endDT, ChronoUnit.SECONDS);
        if (0 < seconds) {
            return ThreeValues.of(ChronoUnit.SECONDS, minutes, seconds + "s");
        }

        return ThreeValues.of(ChronoUnit.FOREVER, 0L, "now");
    }
}
