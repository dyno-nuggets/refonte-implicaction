package com.dynonuggets.refonteimplicaction.core.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TestUtils {

    private static final Random random = new Random();

    public static int generateRandomNumber() {
        return random.nextInt(20);
    }

    public static boolean generateRandomBoolean() {
        return random.nextBoolean();
    }

    public static Instant generateRandomInstant() {
        return generateRandomInstant(Instant.parse("2020-12-03T10:15:30.00Z"));
    }

    public static Instant generateRandomInstant(final Instant minDate) {
        final long startSeconds = minDate.getEpochSecond();
        final long endSeconds = Instant.now().getEpochSecond();
        final long random = ThreadLocalRandom
                .current()
                .nextLong(startSeconds, endSeconds);

        return Instant.ofEpochSecond(random);
    }

    public static LocalDate toLocalDate(final Instant date) {
        return LocalDate.ofInstant(date, ZoneId.of(ZoneId.SHORT_IDS.get("ECT")));
    }
}
