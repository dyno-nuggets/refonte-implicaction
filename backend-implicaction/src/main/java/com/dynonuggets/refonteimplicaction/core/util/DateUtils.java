package com.dynonuggets.refonteimplicaction.core.util;

import org.ocpsoft.prettytime.PrettyTime;

import java.time.Instant;
import java.util.Locale;

public class DateUtils {

    private DateUtils() {
        // empêche l'instanciation d'un objet de type DateUtils
    }

    public static String getDurationAsString(final Instant time) {
        final PrettyTime p = new PrettyTime(new Locale("fr"));
        return p.format(time);
    }
}
