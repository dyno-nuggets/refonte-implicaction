package com.dynonuggets.refonteimplicaction.utils;

import com.dynonuggets.refonteimplicaction.core.domain.model.Role;
import com.dynonuggets.refonteimplicaction.user.domain.enums.RoleEnum;
import com.dynonuggets.refonteimplicaction.user.domain.model.UserModel;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.String.format;
import static java.time.LocalDate.now;
import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

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

    public static LocalDate generateRandomLocalDate() {
        return between(LocalDate.of(1950, Month.JANUARY, 1), now());
    }

    public static LocalDate between(final LocalDate startInclusive, final LocalDate endExclusive) {
        final long startEpochDay = startInclusive.toEpochDay();
        final long endEpochDay = endExclusive.toEpochDay();
        final long randomDay = ThreadLocalRandom
                .current()
                .nextLong(startEpochDay, endEpochDay);

        return LocalDate.ofEpochDay(randomDay);
    }

    public static UserModel generateRandomUser() {
        return generateRandomUser(null, generateRandomBoolean());
    }

    public static UserModel generateRandomUser(final List<RoleEnum> roleEnums, final boolean isEnabled) {
        final String firstname = randomAlphabetic(10);
        final String lastname = randomAlphabetic(10);
        final Instant registeredAt = generateRandomInstant();
        final String email = format("%s.%s@mail.com", firstname, lastname);

        final List<Role> roles = ofNullable(roleEnums).orElse(emptyList())
                .stream()
                .map(roleEnum -> Role.builder().id(roleEnum.getId()).name(roleEnum.name()).build())
                .collect(toList());

        return UserModel.builder()
                .id((long) generateRandomNumber())
                .username(randomAlphabetic(10))
                .firstname(randomAlphabetic(20))
                .lastname(randomAlphabetic(20))
                .password(randomAlphabetic(10))
                .birthday(generateRandomLocalDate())
                .email(email)
                .registeredAt(registeredAt)
                .activationKey(randomAlphabetic(25))
                .enabled(isEnabled)
                .roles(roles)
                .build();
    }
}
