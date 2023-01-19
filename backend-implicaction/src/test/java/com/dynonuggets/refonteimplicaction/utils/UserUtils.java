package com.dynonuggets.refonteimplicaction.utils;

import com.dynonuggets.refonteimplicaction.dto.UserDto;
import com.dynonuggets.refonteimplicaction.model.FileModel;
import com.dynonuggets.refonteimplicaction.model.Role;
import com.dynonuggets.refonteimplicaction.model.RoleEnum;
import com.dynonuggets.refonteimplicaction.model.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

import static com.dynonuggets.refonteimplicaction.utils.TestUtils.*;
import static java.lang.String.format;
import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserUtils {

    public static User generateRandomUser() {
        return generateRandomUser(null, generateRandomBoolean());
    }

    public static User generateRandomUser(final List<RoleEnum> roleEnums, boolean isActive) {
        final String firstname = randomAlphabetic(10);
        final String lastname = randomAlphabetic(10);
        final Instant registeredAt = generateRandomDate();
        final Instant activatedAt = isActive ? generateRandomDate(registeredAt) : null;
        String email = format("%s.%s@mail.com", firstname, lastname);
        String url = format("%s.%s.com", firstname, lastname);

        final LocalDate birthday = toLocalDate(
                generateRandomDate(Instant.parse("1950-12-03T10:15:30.00Z"))
        );

        final List<Role> roles = ofNullable(roleEnums).orElse(emptyList())
                .stream()
                .map(roleEnum -> Role.builder().id(roleEnum.getId()).name(roleEnum.name()).build())
                .collect(toList());

        FileModel image = FileModel.builder()
                .id((long) generateRandomNumber())
                .filename(randomAlphabetic(10))
                .url("http://random/url.com/image.png")
                .contentType(IMAGE_JPEG_VALUE)
                .objectKey(randomAlphabetic(25))
                .build();

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
                .activatedAt(activatedAt)
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
        final Instant activatedAt = generateRandomDate(registeredAt);
        String email = format("%s.%s@mail.com", firstname, lastname);
        String url = format("%s.%s.com", firstname, lastname);
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
                .activatedAt(activatedAt)
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
