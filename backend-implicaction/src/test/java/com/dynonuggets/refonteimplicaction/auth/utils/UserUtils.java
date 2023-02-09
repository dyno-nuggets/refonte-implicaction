package com.dynonuggets.refonteimplicaction.auth.utils;

import com.dynonuggets.refonteimplicaction.auth.domain.model.Role;
import com.dynonuggets.refonteimplicaction.auth.domain.model.RoleEnum;
import com.dynonuggets.refonteimplicaction.auth.domain.model.User;
import com.dynonuggets.refonteimplicaction.auth.rest.dto.UserDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

import static com.dynonuggets.refonteimplicaction.core.util.TestUtils.*;
import static java.lang.String.format;
import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserUtils {

    public static User generateRandomUser() {
        return generateRandomUser(null, generateRandomBoolean());
    }

    public static User generateRandomUser(final List<RoleEnum> roleEnums, final boolean isActive) {
        final String firstname = randomAlphabetic(10);
        final String lastname = randomAlphabetic(10);
        final Instant registeredAt = generateRandomDate();
        final String email = format("%s.%s@mail.com", firstname, lastname);
        final String url = format("%s.%s.com", firstname, lastname);

        final LocalDate birthday = toLocalDate(
                generateRandomDate(Instant.parse("1950-12-03T10:15:30.00Z"))
        );

        final List<Role> roles = ofNullable(roleEnums).orElse(emptyList())
                .stream()
                .map(roleEnum -> Role.builder().id(roleEnum.getId()).name(roleEnum.name()).build())
                .collect(toList());

        return User.builder()
                .id((long) generateRandomNumber())
                .username(randomAlphabetic(10))
                .password(randomAlphabetic(10))
                .firstname(firstname)
                .lastname(lastname)
                .email(email)
                .url(url)
                .birthday(birthday)
                .hobbies(randomAlphabetic(200))
                .purpose(randomAlphabetic(200))
                .presentation(randomAlphabetic(200))
                .expectation(randomAlphabetic(200))
                .contribution(randomAlphabetic(200))
                .phoneNumber(randomNumeric(10))
                .registeredAt(registeredAt)
                .activationKey(randomAlphabetic(25))
                .active(isActive)
                .image(null)
                .roles(roles)
                .groups(null)
                .notifications(null)
                // la charge de le définir est laissé à l'utilisateur
                .trainings(null)
                // la charge de le définir est laissé à l'utilisateur
                .experiences(null)
                .build();
    }

    public static UserDto generateRandomUserDto() {
        return generateRandomUserDto(null);
    }

    public static UserDto generateRandomUserDto(final List<String> roles) {
        final String firstname = randomAlphabetic(generateRandomNumber());
        final String lastname = randomAlphabetic(generateRandomNumber());
        final Instant registeredAt = generateRandomDate();
        final String email = format("%s.%s@mail.com", firstname, lastname);
        final String url = format("%s.%s.com", firstname, lastname);
        final LocalDate birthday = toLocalDate(
                generateRandomDate(Instant.parse("1950-12-03T10:15:30.00Z"))
        );

        return UserDto.builder()
                .id((long) generateRandomNumber())
                .username(randomAlphabetic(10))
                .firstname(firstname)
                .lastname(lastname)
                .email(email)
                .url(url)
                .hobbies(randomAlphabetic(200))
                // la charge de le définir est laissé à l'utilisateur
                .trainings(null)
                // la charge de le setter est laissé à l'utilisateur
                .experiences(null)
                .purpose(randomAlphabetic(200))
                .registeredAt(registeredAt)
                .presentation(randomAlphabetic(200))
                .contribution(randomAlphabetic(200))
                .birthday(birthday)
                .phoneNumber(randomNumeric(10))
                .phoneNumber(randomAlphabetic(25))
                .expectation(randomAlphabetic(200))
                .active(true)
                .imageUrl(randomAlphabetic(50))
                .roles(roles)
                .relationTypeOfCurrentUser(null)
                .build();
    }
}
