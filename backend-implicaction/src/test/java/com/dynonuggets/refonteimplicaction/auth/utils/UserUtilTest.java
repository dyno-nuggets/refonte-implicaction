package com.dynonuggets.refonteimplicaction.auth.utils;

import com.dynonuggets.refonteimplicaction.auth.domain.model.Role;
import com.dynonuggets.refonteimplicaction.auth.domain.model.RoleEnum;
import com.dynonuggets.refonteimplicaction.auth.domain.model.User;
import com.dynonuggets.refonteimplicaction.auth.rest.dto.UserDto;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.test.web.servlet.ResultActions;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

import static com.dynonuggets.refonteimplicaction.core.util.TestUtils.*;
import static com.dynonuggets.refonteimplicaction.core.util.Utils.callIfNotNull;
import static java.lang.String.format;
import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;
import static lombok.AccessLevel.PRIVATE;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@NoArgsConstructor(access = PRIVATE)
public class UserUtilTest {

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
        return generateRandomUserDto(null, false);
    }

    public static UserDto generateRandomUserDto(final List<String> roles, final boolean isActive) {
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
                .active(isActive)
                .imageUrl(randomAlphabetic(50))
                .roles(roles)
                .relationTypeOfCurrentUser(null)
                .build();
    }

    public static void resultActionsValidationForPageUser(final Page<UserDto> userMockPage, final ResultActions resultActions) throws Exception {
        final List<UserDto> pageElements = userMockPage.getContent();

        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.totalPages").value(userMockPage.getTotalPages()))
                .andExpect(jsonPath("$.totalElements").value(userMockPage.getTotalElements()));

        for (int i = 0; i < pageElements.size(); i++) {
            final UserDto userDto = pageElements.get(i);
            resultActionsValidationForSingleUser(userDto, resultActions, i);
        }
    }

    public static void resultActionsValidationForSingleUser(final UserDto userDto, final ResultActions resultActions) throws Exception {
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON));

        resultActionsValidationForSingleUser(userDto, resultActions, null);
    }

    public static void resultActionsValidationForSingleUser(final UserDto userDto, final ResultActions resultActions, final Integer index) throws Exception {
        final String prefix = index != null ? format("$.content[%d]", index) : "$";
        final String registeredAt = callIfNotNull(userDto.getRegisteredAt(), Object::toString);
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath(format("%s.id", prefix), is(userDto.getId().intValue())))
                .andExpect(jsonPath(format("%s.username", prefix), is(userDto.getUsername())))
                .andExpect(jsonPath(format("%s.email", prefix), is(userDto.getEmail())))
                .andExpect(jsonPath(format("%s.firstname", prefix), is(userDto.getFirstname())))
                .andExpect(jsonPath(format("%s.lastname", prefix), is(userDto.getLastname())))
                .andExpect(jsonPath(format("%s.username", prefix), is(userDto.getUsername())))
                .andExpect(jsonPath(format("%s.url", prefix), is(userDto.getUrl())))
                .andExpect(jsonPath(format("%s.hobbies", prefix), is(userDto.getHobbies())))
                .andExpect(jsonPath(format("%s.purpose", prefix), is(userDto.getPurpose())))
                .andExpect(jsonPath(format("%s.presentation", prefix), is(userDto.getPresentation())))
                .andExpect(jsonPath(format("%s.username", prefix), is(userDto.getUsername())))
                .andExpect(jsonPath(format("%s.expectation", prefix), is(userDto.getExpectation())))
                .andExpect(jsonPath(format("%s.contribution", prefix), is(userDto.getContribution())))
                .andExpect(jsonPath(format("%s.registeredAt", prefix), is(registeredAt)))
                .andExpect(jsonPath(format("%s.imageUrl", prefix), is(userDto.getImageUrl())));
    }
}
