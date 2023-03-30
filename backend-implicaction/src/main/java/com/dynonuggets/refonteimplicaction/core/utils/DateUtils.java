package com.dynonuggets.refonteimplicaction.core.utils;

import lombok.NoArgsConstructor;
import org.ocpsoft.prettytime.PrettyTime;

import java.time.Instant;
import java.util.Locale;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class DateUtils {

    private static final PrettyTime prettyTime = new PrettyTime(new Locale("fr"));

    public static String getDurationAsString(final Instant time) {
        if (time == null) {
            return "";
        }

        return prettyTime.format(time);
    }
}
