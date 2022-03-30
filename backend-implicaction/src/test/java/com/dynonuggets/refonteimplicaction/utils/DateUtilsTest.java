package com.dynonuggets.refonteimplicaction.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

import static com.dynonuggets.refonteimplicaction.utils.DateUtils.getDurationAsString;
import static java.time.Instant.now;
import static org.assertj.core.api.Assertions.assertThat;

class DateUtilsTest {

    Map<Instant, String> expectedMaps;

    @BeforeEach
    void setUp() {
        ZoneId zoneId = ZoneId.of("Europe/Paris");
        expectedMaps = new HashMap<>();
        expectedMaps.put(now(), "à l'instant");
        expectedMaps.put(now().minusMillis(60_000), "il y a 1 minute");
        expectedMaps.put(now().minusMillis(3_540_000L), "il y a 59 minutes");
        expectedMaps.put(now().minusMillis(3_600_000L), "il y a 1 heure");
        expectedMaps.put(ZonedDateTime.ofInstant(now(), zoneId).minusDays(1).toInstant(), "il y a 1 jour");
        expectedMaps.put(ZonedDateTime.ofInstant(now(), zoneId).minusYears(2).toInstant(), "il y a 2 ans");
    }

    @Test
    void given_instant_should_return_corresponding_string() {
        // given
        expectedMaps.forEach((instant, expectedString) -> {
            // when
            String actualString = getDurationAsString(instant);
            // then
            assertThat(actualString).isEqualTo(expectedString);
        });
    }
}
