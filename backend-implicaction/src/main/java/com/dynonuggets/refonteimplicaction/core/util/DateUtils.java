package com.dynonuggets.refonteimplicaction.core.util;

import lombok.NoArgsConstructor;
import org.ocpsoft.prettytime.PrettyTime;

import java.time.Instant;
import java.util.Locale;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class DateUtils {
    public static String getDurationAsString(final Instant time) {
        final PrettyTime p = new PrettyTime(new Locale("fr"));
        return p.format(time);
    }
}
