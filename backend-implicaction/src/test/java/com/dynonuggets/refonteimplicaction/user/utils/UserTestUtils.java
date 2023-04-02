package com.dynonuggets.refonteimplicaction.user.utils;

import com.dynonuggets.refonteimplicaction.core.domain.model.Role;
import com.dynonuggets.refonteimplicaction.user.domain.model.UserModel;
import com.dynonuggets.refonteimplicaction.user.dto.UserDto;
import com.dynonuggets.refonteimplicaction.user.dto.enums.RoleEnum;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.test.web.servlet.ResultActions;

import java.time.Instant;
import java.util.List;
import java.util.Set;

import static com.dynonuggets.refonteimplicaction.core.utils.AppUtils.callIfNotNull;
import static com.dynonuggets.refonteimplicaction.utils.TestUtils.*;
import static java.lang.String.format;
import static java.util.Collections.emptySet;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toSet;
import static lombok.AccessLevel.PRIVATE;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@NoArgsConstructor(access = PRIVATE)
public class UserTestUtils {

    public static UserDto generateRandomUserDto() {
        return generateRandomUserDto(null, false);
    }

    public static UserDto generateRandomUserDto(final Set<String> roles, final boolean isActive) {
        return UserDto.builder()
                .id((long) generateRandomNumber())
                .username(randomAlphabetic(10))
                .enabled(isActive)
                .emailVerified(isActive)
                .roles(roles)
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

    public static void resultActionsValidationForSingleUser(final UserDto userDto, final ResultActions resultActions, final Integer index) throws Exception {
        final String prefix = index != null ? format("$.content[%d]", index) : "$";
        final String registeredAt = callIfNotNull(userDto.getRegisteredAt(), Object::toString);
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath(format("%s.id", prefix), is(userDto.getId().intValue())))
                .andExpect(jsonPath(format("%s.username", prefix), is(userDto.getUsername())))
                .andExpect(jsonPath(format("%s.email", prefix), is(userDto.getEmail())))
                .andExpect(jsonPath(format("%s.username", prefix), is(userDto.getUsername())))
                .andExpect(jsonPath(format("%s.username", prefix), is(userDto.getUsername())))
                .andExpect(jsonPath(format("%s.registeredAt", prefix), is(registeredAt)));
    }

    public static UserModel generateRandomUser() {
        return generateRandomUser(null, generateRandomBoolean());
    }

    public static UserModel generateRandomUser(final Set<RoleEnum> roleEnums, final boolean isEnabled) {
        final String firstname = randomAlphabetic(10);
        final String lastname = randomAlphabetic(10);
        final Instant registeredAt = generateRandomInstant();
        final String email = format("%s.%s@mail.com", firstname, lastname);

        final Set<Role> roles = ofNullable(roleEnums).orElse(emptySet())
                .stream()
                .map(roleEnum -> Role.builder().name(roleEnum.name()).build())
                .collect(toSet());

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
